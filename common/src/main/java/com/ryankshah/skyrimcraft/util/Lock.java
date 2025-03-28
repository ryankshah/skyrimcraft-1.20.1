package com.ryankshah.skyrimcraft.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ryankshah.skyrimcraft.item.LockItem;
import com.ryankshah.skyrimcraft.item.LockingItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;

import java.util.Observable;
import java.util.Random;

public class Lock extends Observable
{
    public static final Codec<Lock> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("id").forGetter(lock -> lock.id),
            Codec.BYTE.listOf().fieldOf("combo").xmap(
                    list -> {
                        byte[] array = new byte[list.size()];
                        for (int i = 0; i < list.size(); i++) {
                            array[i] = list.get(i);
                        }
                        return array;
                    },
                    array -> {
                        java.util.List<Byte> list = new java.util.ArrayList<>(array.length);
                        for (byte b : array) {
                            list.add(b);
                        }
                        return list;
                    }
            ).forGetter(lock -> lock.combo),
            Codec.BOOL.fieldOf("locked").forGetter(lock -> lock.locked)
    ).apply(instance, (id, combo, locked) -> {
        Lock lock = new Lock(id, combo.length, locked);
        // We need to handle this specially since the combo byte array is final and set during construction
        // This approach depends on having a constructor that allows setting the combo array directly
        // If that's not available, you may need to extend the Lock class or add a method to set it
        return lock;
    }));

    // Alternative codec that doesn't try to restore the exact combo, just the length
    public static final Codec<Lock> SIMPLE_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("id").forGetter(lock -> lock.id),
            Codec.INT.fieldOf("length").forGetter(lock -> lock.getLength()),
            Codec.BOOL.fieldOf("locked").forGetter(lock -> lock.isLocked())
    ).apply(instance, Lock::new));

    public final int id;
    // index is the order, value is the pin number
    protected final byte[] combo;
    protected boolean locked;

    //  TODO if lock is reshuffled any time other than during creation, then next time it is loaded it will have the initial combination and not the newly reshuffled one. Thankfully reshuffling like that does happen, but this should be changed if it does happen
    public final Random rng;

    public Lock(int id, int length, boolean locked)
    {
        this.id = id;
        this.rng = new Random(id);
        this.combo = this.shuffle(length);
        // this.lookup = this.inverse(this.combo);
        this.locked = locked;
    }

    public static Lock from(ItemStack stack)
    {
        return new Lock(LockingItem.getOrSetId(stack), LockItem.getOrSetLength(stack), !LockItem.isOpen(stack));
    }

    public static final String KEY_ID = "Id", KEY_LENGTH = "Length", KEY_LOCKED = "Locked";

    public static Lock fromNbt(CompoundTag nbt)
    {
        return new Lock(nbt.getInt(KEY_ID), nbt.getByte(KEY_LENGTH), nbt.getBoolean(KEY_LOCKED));
    }

    public static CompoundTag toNbt(Lock lock)
    {
        CompoundTag nbt = new CompoundTag();
        nbt.putInt(KEY_ID, lock.id);
        nbt.putByte(KEY_LENGTH, (byte) lock.combo.length);
        nbt.putBoolean(KEY_LOCKED, lock.locked);
        return nbt;
    }

    public static Lock fromBuf(FriendlyByteBuf buf)
    {
        return new Lock(buf.readInt(), (int) buf.readByte(), buf.readBoolean());
    }

    public static void toBuf(FriendlyByteBuf buf, Lock lock)
    {
        buf.writeInt(lock.id);
        buf.writeByte((int) lock.getLength());
        buf.writeBoolean(lock.isLocked());
    }

    public byte[] shuffle(int length)
    {
        byte[] combo = new byte[length];
        for(byte a = 0; a < length; ++a)
            combo[a] = a;
        CommonUtil.shuffle(combo, this.rng);
        return combo;
    }

	/*
	public byte[] inverse(byte[] combination)
	{
		byte[] lookup = new byte[combination.length];
		for(byte a = 0; a < combination.length; ++a)
			lookup[combination[a]] = a;
		return lookup;
	}
	*/

    public int getLength()
    {
        return this.combo.length;
    }

    public boolean isLocked()
    {
        return this.locked;
    }

    public void setLocked(boolean locked)
    {
        if(this.locked == locked)
            return;
        this.locked = locked;
        this.setChanged();
        this.notifyObservers();
    }

    public int getPin(int index)
    {
        return this.combo[index];
    }

    public boolean checkPin(int index, int pin)
    {
        return this.getPin(index) == pin;
    }
}