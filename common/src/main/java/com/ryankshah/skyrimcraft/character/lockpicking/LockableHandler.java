package com.ryankshah.skyrimcraft.character.lockpicking;


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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;

import java.util.List;
import java.util.Observable;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * Manages and handles logic for all LOADED lockables by accessing internal ILockableStorage objects.
 * This means that there is no way of getting a list of ALL lockables in a world like before
 */
public class LockableHandler implements ILockableHandler
{
    public static final ResourceLocation ID = new ResourceLocation(Constants.MODID, "lockable_handler");

    public final Level world;

    public AtomicInteger lastId = new AtomicInteger();

    public Int2ObjectMap<Lockable> lockables = new Int2ObjectLinkedOpenHashMap<>();

    public LockableHandler(Level world) {
        this.world = world;
    }

    public LockableHandler() {
        this(null);
    }

    public int nextId() {
        return this.lastId.incrementAndGet();
    }

    @Override
    public Int2ObjectMap<Lockable> getLoaded() {
        return this.lockables;
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

    public void readFromNbt(CompoundTag compoundTag) {
        this.lastId.set(compoundTag.getInt("last_id"));
        int size = compoundTag.getInt("LockablesSize");
        ListTag lockables = compoundTag.getList("Lockables",size);
        for(int a = 0; a < lockables.size(); ++a)
        {
            CompoundTag nbt1 = lockables.getCompound(a);
            Lockable lkb = Lockable.fromNbt(nbt1);
            this.lockables.put(lkb.id, lkb);
            lkb.addObserver(this);
        }
    }

    public void writeToNbt(CompoundTag compoundTag) {
        compoundTag.putInt("last_id", this.lastId.get());
        ListTag list = new ListTag();
        for(Lockable lkb : this.lockables.values())
            list.add(Lockable.toNbt(lkb));
        compoundTag.put("Lockables", list);
        compoundTag.putInt("LockablesSize", this.lockables.size());
    }
}