package com.ryankshah.skyrimcraft.block.base;

import com.ryankshah.skyrimcraft.entity.furniture.DwemerBench;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public interface DwemerBenchBase
{
    default boolean sitOn(Level level, BlockPos pos, Player player, Direction dir) {
        if (!level.isClientSide() && !DwemerBench.SITTING_POSITIONS.get(level.dimension()).contains(pos)) {
            DwemerBench entity = DwemerBench.of(level, pos, dir);
            if (level.addFreshEntity(entity)) {
                player.startRiding(entity);
                return true;
            } else {
                entity.removeSeat();
            }
        }
        return false;
    }

    AABB getSeatSize(BlockState state);
}