package com.ryankshah.skyrimcraft.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.state.properties.AttachFace;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;

public enum Transform
{
    NORTH_UP(Direction.NORTH, AttachFace.CEILING),
    SOUTH_UP(Direction.SOUTH, AttachFace.CEILING),
    WEST_UP(Direction.WEST, AttachFace.CEILING),
    EAST_UP(Direction.EAST, AttachFace.CEILING),
    NORTH_MID(Direction.NORTH, AttachFace.WALL),
    SOUTH_MID(Direction.SOUTH, AttachFace.WALL),
    WEST_MID(Direction.WEST, AttachFace.WALL),
    EAST_MID(Direction.EAST, AttachFace.WALL),
    NORTH_DOWN(Direction.NORTH, AttachFace.FLOOR),
    SOUTH_DOWN(Direction.SOUTH, AttachFace.FLOOR),
    WEST_DOWN(Direction.WEST, AttachFace.FLOOR),
    EAST_DOWN(Direction.EAST, AttachFace.FLOOR);

    // Create a codec for AttachFace using StringRepresentable
    public static final Codec<AttachFace> ATTACH_FACE_CODEC = StringRepresentable.fromEnum(AttachFace::values);

    // Direct codec using enum name
    public static final Codec<Transform> NAME_CODEC = Codec.STRING.xmap(
            name -> Transform.valueOf(name),
            transform -> transform.name()
    );

    // Helper method to safely get the Transform from direction and face
    private static Transform getTransform(Direction direction, AttachFace face) {
        return Transform.LOOKUP.get(Pair.of(direction, face));
    }

    // Codec that serializes the transform by its components
    public static final Codec<Transform> COMPONENT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Direction.CODEC.fieldOf("direction").forGetter(transform -> transform.dir),
            ATTACH_FACE_CODEC.fieldOf("face").forGetter(transform -> transform.face)
    ).apply(instance, Transform::getTransform));

    // Default codec to use
    public static final Codec<Transform> CODEC = NAME_CODEC;

    public static final HashMap<Pair<Direction, AttachFace>, Transform> LOOKUP = new HashMap<>(16); // 12 / 0.75

    static
    {
        for(Transform tr : Transform.values())
            LOOKUP.put(Pair.of(tr.dir, tr.face), tr);
    }

    public final Direction dir;
    public final AttachFace face;

    Transform(Direction dir, AttachFace face)
    {
        this.dir = dir;
        this.face = face;
    }

    public Direction getCuboidFace()
    {
        return this.face == AttachFace.CEILING ? Direction.UP : this.face == AttachFace.FLOOR ? Direction.DOWN : this.dir;
    }

    public static Transform fromDirectionAndFace(Direction dir, AttachFace face, Direction def)
    {
        return LOOKUP.get(Pair.of(dir.getAxis() == Direction.Axis.Y ? def : dir, face));
    }

    public static Transform fromDirection(Direction dir, Direction def)
    {
        return fromDirectionAndFace(dir, CommonUtil.faceFromDir(dir), def);
    }
}