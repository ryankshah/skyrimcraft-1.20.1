package com.ryankshah.skyrimcraft.block;

import com.ryankshah.skyrimcraft.block.base.ModularSeatBlock;
import com.ryankshah.skyrimcraft.block.util.ModularSeatProperty;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class DwemerBenchBlock extends ModularSeatBlock {

    public static final AABB SEAT = new AABB(0, 0, 0, 1, 0.5, 1);
    public static final VoxelShape VOXEL_SHAPE = Block.box(0, 0, 0, 16, 10, 16);

    public DwemerBenchBlock(BlockBehaviour.Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState()
                .setValue(SHAPE, ModularSeatProperty.SINGLE)
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false)
        );
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return VOXEL_SHAPE;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean moved) {
        if (!level.isClientSide() && state.getBlock() != newState.getBlock()) {
            Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(this));
        }
        super.onRemove(state, level, pos, newState, moved);
    }

    @Override
    public boolean sitOn(Level level, BlockPos pos, Player player, Direction dir) {
        return super.sitOn(level, pos, player, dir);
    }

    @Override
    public AABB getSeatSize(BlockState state) {
        return SEAT;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
    }
//    @Override
//    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
//        return super.useWithoutItem(state, level, pos, player, hitResult);
//    }

//    @Override
//    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
//        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
//    }
}