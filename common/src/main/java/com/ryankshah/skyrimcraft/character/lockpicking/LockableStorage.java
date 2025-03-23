package com.ryankshah.skyrimcraft.character.lockpicking;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.platform.Services;
import com.ryankshah.skyrimcraft.util.Lockable;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.chunk.LevelChunk;

import java.util.HashMap;
import java.util.Map;

/*
 * Internal storage for lockables with almost no handling logic
 * Also stores lockables which are shared by multiple chunks. Duplicate shared lockables are handled by checking if they have already been loaded before
 */
public class LockableStorage implements ILockableStorage {
    public static final ResourceLocation ID = new ResourceLocation(Constants.MODID, "lockable_storage");

    public static LevelChunk chunk;

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

    // Final codec for LockableStorage
    public static final Codec<LockableStorage> CODEC = RecordCodecBuilder.create(instance -> // Get the lockable map from the storage
            instance.group(
            INT_TO_LOCKABLE_MAP_CODEC.fieldOf("lockables").forGetter(LockableStorage::get)
    ).apply(instance, lockables -> {
        return new LockableStorage(null, lockables);
    }));

    public LockableStorage() {
    }

    public LockableStorage(LevelChunk c) {
        this(c, new Int2ObjectLinkedOpenHashMap<>());
    }

    public LockableStorage(LevelChunk c, Int2ObjectMap<Lockable> l) {
        chunk = c;
        lockables = l;
    }

    public LockableStorage(Int2ObjectMap<Lockable> l) {
        lockables = l;
    }

    @Override
    public Int2ObjectMap<Lockable> get() {
        return lockables;
    }

    @Override
    public void add(Lockable lkb) {
        lockables.put(lkb.id, lkb);
        chunk.setUnsaved(true);
    }

    @Override
    public void remove(int id) {
        lockables.remove(id);
        chunk.setUnsaved(true);
    }

    public static ListTag serializeNBT() {
        ListTag list = new ListTag();
        for (Lockable lkb : lockables.values())
            list.add(Lockable.toNbt(lkb));
        return list;
    }

    public static LockableStorage deserializeNBT(ListTag nbt) {
        ILockableHandler handler = Services.PLATFORM.getLockableHandler(chunk.getLevel());
        Int2ObjectMap<Lockable> lkbs = handler.getLoaded();
        for (int a = 0; a < nbt.size(); ++a) {
            CompoundTag nbt1 = nbt.getCompound(a);
            Lockable lkb = lkbs.get(Lockable.idFromNbt(nbt1));
            if (lkb == lkbs.defaultReturnValue()) {
                //if (lkb==null) return;
                lkb = Lockable.fromNbt(nbt1);
                lkb.addObserver(handler);
                lkbs.put(lkb.id, lkb);
            }
            lockables.put(lkb.id, lkb);
        }
        return new LockableStorage(chunk, lockables);
    }
}