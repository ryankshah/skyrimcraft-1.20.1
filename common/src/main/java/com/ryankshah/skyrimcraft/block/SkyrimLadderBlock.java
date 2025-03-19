package com.ryankshah.skyrimcraft.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class SkyrimLadderBlock extends LadderBlock
{
    public static final DirectionProperty FACING;
    public static final BooleanProperty WATERLOGGED;
    protected static final float AABB_OFFSET = 3.0F;
    protected static final VoxelShape EAST_AABB;
    protected static final VoxelShape WEST_AABB;
    protected static final VoxelShape SOUTH_AABB;
    protected static final VoxelShape NORTH_AABB;

    public SkyrimLadderBlock(BlockBehaviour.Properties $$0) {
        super($$0);
        this.registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH)).setValue(WATERLOGGED, false));
    }

    public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
        switch ((Direction)$$0.getValue(FACING)) {
            case NORTH:
                return NORTH_AABB;
            case SOUTH:
                return SOUTH_AABB;
            case WEST:
                return WEST_AABB;
            case EAST:
            default:
                return EAST_AABB;
        }
    }

    private boolean canAttachTo(BlockGetter $$0, BlockPos $$1, Direction $$2) {
        BlockState $$3 = $$0.getBlockState($$1);
        return $$3.isFaceSturdy($$0, $$1, $$2);
    }

    public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
        Direction $$3 = (Direction)$$0.getValue(FACING);
        return this.canAttachTo($$1, $$2.relative($$3.getOpposite()), $$3);
    }

    public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
        if ($$1.getOpposite() == $$0.getValue(FACING) && !$$0.canSurvive($$3, $$4)) {
            return Blocks.AIR.defaultBlockState();
        } else {
            if ((Boolean)$$0.getValue(WATERLOGGED)) {
                $$3.scheduleTick($$4, Fluids.WATER, Fluids.WATER.getTickDelay($$3));
            }

            return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
        }
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext $$0) {
        BlockState $$2;
        if (!$$0.replacingClickedOnBlock()) {
            $$2 = $$0.getLevel().getBlockState($$0.getClickedPos().relative($$0.getClickedFace().getOpposite()));
            if ($$2.is(this) && $$2.getValue(FACING) == $$0.getClickedFace()) {
                return null;
            }
        }

        $$2 = this.defaultBlockState();
        LevelReader $$3 = $$0.getLevel();
        BlockPos $$4 = $$0.getClickedPos();
        FluidState $$5 = $$0.getLevel().getFluidState($$0.getClickedPos());
        Direction[] var6 = $$0.getNearestLookingDirections();
        int var7 = var6.length;

        for(int var8 = 0; var8 < var7; ++var8) {
            Direction $$6 = var6[var8];
            if ($$6.getAxis().isHorizontal()) {
                $$2 = (BlockState)$$2.setValue(FACING, $$6.getOpposite());
                if ($$2.canSurvive($$3, $$4)) {
                    return (BlockState)$$2.setValue(WATERLOGGED, $$5.getType() == Fluids.WATER);
                }
            }
        }

        return null;
    }

    public BlockState rotate(BlockState $$0, Rotation $$1) {
        return (BlockState)$$0.setValue(FACING, $$1.rotate((Direction)$$0.getValue(FACING)));
    }

    public BlockState mirror(BlockState $$0, Mirror $$1) {
        return $$0.rotate($$1.getRotation((Direction)$$0.getValue(FACING)));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
        $$0.add(new Property[]{FACING, WATERLOGGED});
    }

    public FluidState getFluidState(BlockState $$0) {
        return (Boolean)$$0.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState($$0);
    }

    static {
        FACING = HorizontalDirectionalBlock.FACING;
        WATERLOGGED = BlockStateProperties.WATERLOGGED;
        EAST_AABB = Block.box(0.0, 0.0, 0.0, 3.0, 16.0, 16.0);
        WEST_AABB = Block.box(13.0, 0.0, 0.0, 16.0, 16.0, 16.0);
        SOUTH_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 3.0);
        NORTH_AABB = Block.box(0.0, 0.0, 13.0, 16.0, 16.0, 16.0);
    }
}
