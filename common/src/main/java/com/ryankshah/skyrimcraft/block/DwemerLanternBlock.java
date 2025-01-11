package com.ryankshah.skyrimcraft.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class DwemerLanternBlock extends Block implements SimpleWaterloggedBlock
{
    public static final BooleanProperty HANGING = BlockStateProperties.HANGING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final VoxelShape AABB = Shapes.or(Block.box(5.0D, 0.0D, 5.0D, 11.0D, 7.0D, 11.0D), Block.box(6.0D, 7.0D, 6.0D, 10.0D, 9.0D, 10.0D));
    protected static final VoxelShape HANGING_AABB = Shapes.or(Block.box(5.0D, 1.0D, 5.0D, 11.0D, 8.0D, 11.0D), Block.box(6.0D, 8.0D, 6.0D, 10.0D, 10.0D, 10.0D));

    public DwemerLanternBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(HANGING, false)).setValue(WATERLOGGED, false));
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        BlockPos pos = context.getClickedPos();
        BlockState aboveState = context.getLevel().getBlockState(pos.above());
        BlockState attachTo = context.getLevel().getBlockState(pos.relative(Direction.UP));

        // First, try to make it hang if there's a lantern or valid block above
        if (attachTo.getBlock() instanceof DwemerLanternBlock || Block.canSupportCenter(context.getLevel(), pos.above(), Direction.DOWN)) {
            return this.defaultBlockState()
                    .setValue(HANGING, true)
                    .setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
        }

        // If we can't hang it, try to place it normally
        if (Block.canSupportCenter(context.getLevel(), pos.below(), Direction.UP)) {
            return this.defaultBlockState()
                    .setValue(HANGING, false)
                    .setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
        }

        return null;
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return pState.getValue(HANGING) ? HANGING_AABB : AABB;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{HANGING, WATERLOGGED});
    }

    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        Direction direction = getConnectedDirection(pState).getOpposite();
        BlockPos attachmentPos = pPos.relative(direction);
        BlockState attachmentState = pLevel.getBlockState(attachmentPos);

        // Can attach to another lantern
        if (attachmentState.getBlock() instanceof DwemerLanternBlock) {
            return true;
        }

        // Can attach to any block that can support it
        return Block.canSupportCenter(pLevel, attachmentPos, direction.getOpposite());
    }

    protected static Direction getConnectedDirection(BlockState state) {
        return (Boolean)state.getValue(HANGING) ? Direction.DOWN : Direction.UP;
    }

    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pPos, BlockPos pNeighborPos) {
        if ((Boolean) pState.getValue(WATERLOGGED)) {
            pLevel.scheduleTick(pPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }

        return getConnectedDirection(pState).getOpposite() == pDirection && !pState.canSurvive(pLevel, pPos) ?
                Blocks.AIR.defaultBlockState() :
                pState;
    }

    public FluidState getFluidState(BlockState state) {
        return (Boolean)state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return false;
    }
}