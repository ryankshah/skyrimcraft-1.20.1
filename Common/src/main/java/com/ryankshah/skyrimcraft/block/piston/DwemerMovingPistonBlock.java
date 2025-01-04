package com.ryankshah.skyrimcraft.block.piston;

import com.mojang.serialization.MapCodec;
import com.ryankshah.skyrimcraft.registry.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.piston.MovingPistonBlock;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.piston.PistonHeadBlock;
import net.minecraft.world.level.block.piston.PistonMovingBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.PistonType;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class DwemerMovingPistonBlock extends BaseEntityBlock
{
    public static final DirectionProperty FACING = DwemerPistonHead.FACING;
    public static final EnumProperty<PistonType> TYPE = DwemerPistonHead.TYPE;

    public DwemerMovingPistonBlock(BlockBehaviour.Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(TYPE, PistonType.DEFAULT));
    }

    @Nullable
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return null;
    }

    public static BlockEntity newMovingBlockEntity(BlockPos pPos, BlockState pBlockState, BlockState pMovedState, Direction pDirection, boolean pExtending, boolean pIsSourcePiston) {
        return new DwemerPistonMovingBlockEntity(pPos, pBlockState, pMovedState, pDirection, pExtending, pIsSourcePiston);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, BlockEntityRegistry.DWEMER_PISTON.get(), DwemerPistonMovingBlockEntity::tick);
    }

    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (!pState.is(pNewState.getBlock())) {
            BlockEntity blockentity = pLevel.getBlockEntity(pPos);
            if (blockentity instanceof DwemerPistonMovingBlockEntity) {
                ((DwemerPistonMovingBlockEntity)blockentity).finalTick();
            }

        }
    }

    /**
     * Called after this block has been removed by a player.
     */
    public void destroy(LevelAccessor pLevel, BlockPos pPos, BlockState pState) {
        BlockPos blockpos = pPos.relative(pState.getValue(FACING).getOpposite());
        BlockState blockstate = pLevel.getBlockState(blockpos);
        if (blockstate.getBlock() instanceof DwemerPistonBase && blockstate.getValue(DwemerPistonBase.EXTENDED)) {
            pLevel.removeBlock(blockpos, false);
        }

    }

    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide && pLevel.getBlockEntity(pPos) == null) {
            pLevel.removeBlock(pPos, false);
            return InteractionResult.CONSUME;
        } else {
            return InteractionResult.PASS;
        }
    }

    public List<ItemStack> getDrops(BlockState pState, LootParams.Builder pParams) {
        DwemerPistonMovingBlockEntity pistonmovingblockentity = this.getBlockEntity(pParams.getLevel(), BlockPos.containing(pParams.getParameter(LootContextParams.ORIGIN)));
        return pistonmovingblockentity == null ? Collections.emptyList() : pistonmovingblockentity.getMovedState().getDrops(pParams);
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Shapes.empty();
    }

    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        DwemerPistonMovingBlockEntity pistonmovingblockentity = this.getBlockEntity(pLevel, pPos);
        return pistonmovingblockentity != null ? pistonmovingblockentity.getCollisionShape(pLevel, pPos) : Shapes.empty();
    }

    @Nullable
    private DwemerPistonMovingBlockEntity getBlockEntity(BlockGetter pBlockReader, BlockPos pPos) {
        BlockEntity blockentity = pBlockReader.getBlockEntity(pPos);
        return blockentity instanceof DwemerPistonMovingBlockEntity ? (DwemerPistonMovingBlockEntity)blockentity : null;
    }

    public ItemStack getCloneItemStack(BlockGetter pLevel, BlockPos pPos, BlockState pState) {
        return ItemStack.EMPTY;
    }

    /**
     * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     * @deprecated call via {@link net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase#rotate} whenever
     * possible. Implementing/overriding is fine.
     */
    public BlockState rotate(BlockState pState, Rotation pRot) {
        return pState.setValue(FACING, pRot.rotate(pState.getValue(FACING)));
    }

    /**
     * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     * @deprecated call via {@link net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase#mirror} whenever
     * possible. Implementing/overriding is fine.
     */
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, TYPE);
    }

    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return false;
    }
}
