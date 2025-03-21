package com.ryankshah.skyrimcraft.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BasePressurePlateBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;

public class SkyrimcraftPressurePlateBlock extends BasePressurePlateBlock
{
//    public static final MapCodec<SkyrimcraftPressurePlateBlock> CODEC = RecordCodecBuilder.mapCodec((p_308833_) -> {
//        return p_308833_.group(BlockSetType.CODEC.fieldOf("block_set_type").forGetter((p_304917_) -> {
//            return p_304917_.type;
//        }), propertiesCodec()).apply(p_308833_, SkyrimcraftPressurePlateBlock::new);
//    });
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    private final SkyrimcraftPressurePlateBlock.Sensitivity sensitivity;

//    public MapCodec<SkyrimcraftPressurePlateBlock> codec() {
//        return CODEC;
//    }

    public SkyrimcraftPressurePlateBlock(BlockSetType p_273284_, BlockBehaviour.Properties p_273571_, SkyrimcraftPressurePlateBlock.Sensitivity sensitivity) {
        super(p_273571_, p_273284_);
        this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(POWERED, false));
        this.sensitivity = sensitivity;
    }

    protected int getSignalForState(BlockState pState) {
        return (Boolean)pState.getValue(POWERED) ? 15 : 0;
    }

    protected BlockState setSignalForState(BlockState pState, int pStrength) {
        return (BlockState)pState.setValue(POWERED, pStrength > 0);
    }

    protected int getSignalStrength(Level pLevel, BlockPos pPos) {
        Class<? extends Entity> oclass1;
        switch (this.sensitivity) {
            case EVERYTHING:
                oclass1 = Entity.class;
                break;
            case MOBS:
                oclass1 = LivingEntity.class;
                break;
            default:
                throw new IncompatibleClassChangeError();
        }

        Class oclass = oclass1;
        return getEntityCount(pLevel, TOUCH_AABB.move(pPos), oclass) > 0 ? 15 : 0;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(new Property[]{POWERED});
    }

    public static enum Sensitivity {
        EVERYTHING,
        MOBS;
    }
}
