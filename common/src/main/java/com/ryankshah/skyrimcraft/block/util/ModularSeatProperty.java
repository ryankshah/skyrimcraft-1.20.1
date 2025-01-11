package com.ryankshah.skyrimcraft.block.util;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public enum ModularSeatProperty implements StringRepresentable {
    SINGLE,
    LEFT,
    MIDDLE,
    RIGHT,
    INNER_LEFT,
    INNER_RIGHT,
    OUTER_LEFT,
    OUTER_RIGHT,
    ;

    @Override
    public @NotNull String getSerializedName() {
        return name().toLowerCase(Locale.ROOT);
    }

    @Override
    public String toString() {
        return getSerializedName();
    }

    public String getLocation() {
        return switch (this) {
            case INNER_LEFT, INNER_RIGHT -> "corner";
            case OUTER_LEFT, OUTER_RIGHT -> "corner_inverted";
            default -> getSerializedName();
        };
    }
}