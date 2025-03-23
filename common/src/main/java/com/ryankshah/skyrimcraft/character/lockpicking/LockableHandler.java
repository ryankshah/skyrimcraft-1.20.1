package com.ryankshah.skyrimcraft.character.lockpicking;


import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.config.CommonConfig;
import com.ryankshah.skyrimcraft.network.skill.lockpicking.AddLockablePacket;
import com.ryankshah.skyrimcraft.network.skill.lockpicking.RemoveLockablePacket;
import com.ryankshah.skyrimcraft.network.skill.lockpicking.UpdateLockablePacket;
import com.ryankshah.skyrimcraft.platform.Services;
import com.ryankshah.skyrimcraft.util.Lockable;
import commonnetwork.api.Dispatcher;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * Manages and handles logic for all LOADED lockables by accessing internal ILockableStorage objects.
 * This means that there is no way of getting a list of ALL lockables in a world like before
 */
public class LockableHandler implements ILockableHandler
{
    public static final ResourceLocation ID = new ResourceLocation(Constants.MODID, "lockable_handler");

    public static Level world;

    public static AtomicInteger lastId = new AtomicInteger();

    public static Int2ObjectMap<Lockable> lockables = new Int2ObjectLinkedOpenHashMap<>();

    // Create a codec for Int2ObjectMap<Lockable>
    private static final Codec<Int2ObjectMap<Lockable>> INT_TO_LOCKABLE_MAP_CODEC = Codec.unboundedMap(
            Codec.INT,
            Lockable.CODEC
    ).xmap(
            map -> {
                Int2ObjectLinkedOpenHashMap<Lockable> result = new Int2ObjectLinkedOpenHashMap<>(map.size());
                result.putAll(map);
                return result;
            },
            map -> {
                Map<Integer, Lockable> result = new HashMap<>(map.size());
                map.int2ObjectEntrySet().forEach(entry ->
                        result.put(entry.getIntKey(), entry.getValue())
                );
                return result;
            }
    );

    // Create a codec for AtomicInteger
    private static final Codec<AtomicInteger> ATOMIC_INTEGER_CODEC = Codec.INT.xmap(
            AtomicInteger::new,
            AtomicInteger::get
    );

    // Final codec for LockableHandler
    public static final Codec<LockableHandler> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ATOMIC_INTEGER_CODEC.fieldOf("lastId").forGetter(handler -> {
                return new AtomicInteger(handler.getLastId());
            }),
            INT_TO_LOCKABLE_MAP_CODEC.fieldOf("lockables").forGetter(handler -> {
                return handler.getLoaded();
            })
    ).apply(instance, (lastId, lockables) -> {
        return new LockableHandler(lastId, lockables);
    }));

    public LockableHandler(Level w) {
        this(w, new AtomicInteger(), new Int2ObjectLinkedOpenHashMap<>());
    }

    public LockableHandler() {}

    public LockableHandler(Level w, AtomicInteger id, Int2ObjectMap<Lockable> l) {
        world = w;
        lastId = id;
        lockables = l;
    }

    public LockableHandler(AtomicInteger id, Int2ObjectMap<Lockable> l) {
        lastId = id;
        lockables = l;
    }

    public int nextId() {
        return lastId.incrementAndGet();
    }

    @Override
    public Int2ObjectMap<Lockable> getLoaded() {
        return lockables;
    }

    @Override
    public Int2ObjectMap<Lockable> getInChunk(BlockPos pos) {
        return this.world.hasChunkAt(pos) ? Services.PLATFORM.getLockableStorage(this.world.getChunkAt(pos)).get() : Int2ObjectMaps.emptyMap();
    }

    @Override
    public boolean add(Lockable lkb) {
        if(lkb.bb.volume() > CommonConfig.MAX_LOCKABLE_VOLUME)
            return false;
        List<ILockableStorage> sts = lkb.bb.containedChunksTo((x, z) ->
        {
            try {
                LevelChunk levelChunk = this.world.getChunk(x, z);
                ILockableStorage st = Services.PLATFORM.getLockableStorage(levelChunk);
                return st.get().values().stream().anyMatch(lkb1 -> lkb1.bb.intersects(lkb.bb)) ? null : st;
            } catch (Exception e){
                Constants.LOG.warn("Chunk not gen");
            }
            return null;
        }, true);
        if(sts == null)
            return false;

        // Add to chunk
        for(int a = 0; a < sts.size(); ++a)
            sts.get(a).add(lkb);
        // Add to world
        this.lockables.put(lkb.id, lkb);
        lkb.addObserver(this);
        // Do client/server extras
        if(this.world.isClientSide)
            lkb.swing(10);
        else {
            world.getServer().getPlayerList().getPlayers().forEach(player -> {
                Dispatcher.sendToClient(new AddLockablePacket(lkb), player);
            });
        }
        return true;
    }

    @Override
    public boolean remove(int id)
    {
        Lockable lkb = this.lockables.get(id);
        if(lkb == this.lockables.defaultReturnValue())
            return false;
        List<LevelChunk> chs = lkb.bb.containedChunksTo((x, z) -> this.world.hasChunk(x, z) ? this.world.getChunk(x, z) : null, true);

        // Remove from chunk
        for(int a = 0; a < chs.size(); ++a)
            Services.PLATFORM.getLockableStorage(chs.get(a)).remove(id);
//            chs.get(a).getCapability(LocksCapabilities.LOCKABLE_STORAGE).orElse(null).remove(id);
        // Remove from world
        this.lockables.remove(id);
        lkb.deleteObserver(this);
        // Do client/server extras
        if(this.world.isClientSide)
            return true;
        world.getServer().getPlayerList().getPlayers().forEach(player -> {
            Dispatcher.sendToClient(new RemoveLockablePacket(lkb.id), player);
        });
        return true;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (this.world.isClientSide || !(o instanceof Lockable))
            return;
        Lockable lockable = (Lockable) o;
        world.getServer().getPlayerList().getPlayers().forEach(player -> {
            Dispatcher.sendToClient(new UpdateLockablePacket(lockable.id, lockable.lock.isLocked()), player);
        });
    }

    public int getLastId() {
        return lastId.get();
    }

    public Int2ObjectMap<Lockable> getLockables() {
        return lockables;
    }

    public static void readFromNbt(CompoundTag compoundTag) {
        lastId.set(compoundTag.getInt("last_id"));
        int size = compoundTag.getInt("LockablesSize");
        ListTag lockablesTag = compoundTag.getList("Lockables",size);
        for(int a = 0; a < lockablesTag.size(); ++a)
        {
            CompoundTag nbt1 = lockablesTag.getCompound(a);
            Lockable lkb = Lockable.fromNbt(nbt1);
            lockables.put(lkb.id, lkb);
            lkb.addObserver(new LockableHandler(world, lastId, lockables));
        }
    }

    public static LockableHandler readNBT(Tag tag) {
        CompoundTag compoundTag = (CompoundTag)tag;
        lastId.set(compoundTag.getInt("last_id"));
        int size = compoundTag.getInt("LockablesSize");
        ListTag lockablesTag = compoundTag.getList("Lockables",size);
        for(int a = 0; a < lockablesTag.size(); ++a)
        {
            CompoundTag nbt1 = lockablesTag.getCompound(a);
            Lockable lkb = Lockable.fromNbt(nbt1);
            lockables.put(lkb.id, lkb);
            lkb.addObserver(new LockableHandler(world, lastId, lockables));
        }
        return new LockableHandler(world, lastId, lockables);
    }

    public static LockableHandler readNBTCompound(CompoundTag tag) {
        CompoundTag compoundTag = (CompoundTag)tag;
        lastId.set(compoundTag.getInt("last_id"));
        int size = compoundTag.getInt("LockablesSize");
        ListTag lockablesTag = compoundTag.getList("Lockables",size);
        for(int a = 0; a < lockablesTag.size(); ++a)
        {
            CompoundTag nbt1 = lockablesTag.getCompound(a);
            Lockable lkb = Lockable.fromNbt(nbt1);
            lockables.put(lkb.id, lkb);
            lkb.addObserver(new LockableHandler(world, lastId, lockables));
        }
        return new LockableHandler(world, lastId, lockables);
    }

    public static void writeToNbt(CompoundTag compoundTag) {
        compoundTag.putInt("last_id", lastId.get());
        ListTag list = new ListTag();
        for(Lockable lkb : lockables.values())
            list.add(Lockable.toNbt(lkb));
        compoundTag.put("Lockables", list);
        compoundTag.putInt("LockablesSize", lockables.size());
    }
}