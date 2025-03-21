package com.ryankshah.skyrimcraft.util;


import com.ryankshah.skyrimcraft.config.CommonConfig;
import com.ryankshah.skyrimcraft.mixin.accessor.LootPoolAccessor;
import com.ryankshah.skyrimcraft.mixin.accessor.LootTableAccessor;
import com.ryankshah.skyrimcraft.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.*;
import static net.minecraft.world.level.block.state.properties.DoorHingeSide.LEFT;
import static net.minecraft.world.level.block.state.properties.DoubleBlockHalf.LOWER;

public final class CommonUtil {
    public static ResourceManager resourceManager;

    private CommonUtil() {
    }

    public static void shuffle(byte[] array, Random rng) {
        for (int a = array.length - 1; a > 0; --a) {
            int index = rng.nextInt(a + 1);
            byte temp = array[index];
            array[index] = array[a];
            array[a] = temp;
        }
    }

    public static boolean chance(RandomSource rng, double ch) {
        return ch == 1d || ch != 0d && rng.nextDouble() <= ch;
    }

    public static BlockPos transform(int x, int y, int z, StructurePlaceSettings settings) {
        switch (settings.getMirror()) {
            case LEFT_RIGHT:
                z = -z + 1;
                break;
            case FRONT_BACK:
                x = -x + 1;
                break;
            default:
                break;
        }
        int x1 = settings.getRotationPivot().getX();
        int z1 = settings.getRotationPivot().getZ();
        switch (settings.getRotation()) {
            case COUNTERCLOCKWISE_90:
                return new BlockPos(x1 - z1 + z, y, x1 + z1 - x + 1);
            case CLOCKWISE_90:
                return new BlockPos(x1 + z1 - z + 1, y, z1 - x1 + x);
            case CLOCKWISE_180:
                return new BlockPos(x1 + x1 - x + 1, y, z1 + z1 - z + 1);
            default:
                return new BlockPos(x, y, z);
        }
    }

    public static AttachFace faceFromDir(Direction dir) {
        return dir == Direction.UP ? AttachFace.CEILING : dir == Direction.DOWN ? AttachFace.FLOOR : AttachFace.WALL;
    }

    public static AABB rotateY(AABB bb) {
        return new AABB(bb.minZ, bb.minY, bb.minX, bb.maxZ, bb.maxY, bb.maxX);
    }

    public static AABB rotateX(AABB bb) {
        return new AABB(bb.minX, bb.minZ, bb.minY, bb.maxX, bb.maxZ, bb.maxY);
    }

    public static boolean intersectsInclusive(AABB bb1, AABB bb2) {
        return bb1.minX <= bb2.maxX && bb1.maxX >= bb2.minX && bb1.minY <= bb2.maxY && bb1.maxY >= bb2.minY && bb1.minZ <= bb2.maxZ && bb1.maxZ >= bb2.minZ;
    }

    public static Vec3 sideCenter(AABB bb, Direction side) {
        Vec3i dir = side.getNormal();
        return new Vec3((bb.minX + bb.maxX + (bb.maxX - bb.minX) * dir.getX()) * 0.5d, (bb.minY + bb.maxY + (bb.maxY - bb.minY) * dir.getY()) * 0.5d, (bb.minZ + bb.maxZ + (bb.maxZ - bb.minZ) * dir.getZ()) * 0.5d);
    }

    //   public static LootTable lootTableFrom(ResourceLocation loc) throws IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    // JsonElement json = GsonHelper.fromJson(LootTableManager.GSON, new BufferedReader(new InputStreamReader(resourceManager.getResource(loc).getInputStream(), StandardCharsets.UTF_8)), JsonElement.class);

//        JsonElement json = GsonHelper.fromJson(LootDataType.TABLE.parser(), new BufferedReader(new InputStreamReader(resourceManager.getResource(loc).orElseThrow().open(), StandardCharsets.UTF_8)), JsonElement.class);
//        Deque que = ForgeHooksAccessor.getLootContext().get();
//        Object lootCtx = lootTableContextConstructor.newInstance(loc, false);
//        try {
//            que.push(lootCtx);
//            return LootDataType.TABLE.parser().fromJson(json, LootTable.class);
//        } catch (JsonSyntaxException e) {
//            throw e;
//        } finally // Still executes even if catch throws according to SO!
//        {
//            que.pop();
//        }
    //   }

    // Only merges entries, not conditions and functions
    public static LootTable mergeEntries(LootTable table, LootTable inject) {
        List<LootPool> list = Arrays.asList(((LootTableAccessor) table).getPools());
        for (LootPool injectPool : ((LootTableAccessor) inject).getPools()) {
            if (list.contains(injectPool)) {
                ((LootPoolAccessor) injectPool).getEntries().addAll(((LootPoolAccessor) injectPool).getEntries());
            } else {
                list.add(injectPool);
            }
        }
        ((LootTableAccessor) inject).setPools(list.toArray(new LootPool[0]));
        return table;
    }

    public static Stream<Lockable> intersecting(Level world, BlockPos pos) {
        return Services.PLATFORM.getLockableHandler(world).getInChunk(pos).values().stream().filter(
                lkb -> lkb.bb.intersects(pos)
        );
    }

    public static boolean lockedAndRelated(Level world, BlockPos pos) {
        BlockPos above = pos.above();
        Block aboveBlock = world.getBlockState(above).getBlock();
        boolean checkAbove = CommonUtil.locked(world, above) && aboveBlock instanceof DoorBlock;
        return locked(world, pos) || checkAbove;
    }

    public static boolean locked(Level world, BlockPos pos) {
        return intersecting(world, pos).anyMatch(LocksPredicates.LOCKED);
    }

    // TODO: 方块遮挡判断
    public static Lockable lockWhenGen(LevelAccessor levelAccessor, ServerLevel level, BlockPos blockPos, RandomSource randomSource) {
        BlockState state = levelAccessor.getBlockState(blockPos);
        Block block = state.getBlock();
        if (!CommonConfig.canGen(randomSource, block)) return null;
        BlockPos pos1 = blockPos;
        Direction dir = null;
        if (state.hasProperty(FACING)) {
            dir = state.getValue(FACING);
        } else if (state.hasProperty(HORIZONTAL_FACING)) {
            dir = state.getValue(HORIZONTAL_FACING);
        } else {
            return null;
        }

        if (state.hasProperty(CHEST_TYPE)) {
            switch (state.getValue(CHEST_TYPE)) {
                case LEFT -> pos1 = blockPos.relative(ChestBlock.getConnectedDirection(state));
                case RIGHT -> {
                    return null;
                }
            }
        }
        if (state.hasProperty(DOUBLE_BLOCK_HALF)) {
            if (state.getValue(DOUBLE_BLOCK_HALF) == LOWER) return null;
            pos1 = blockPos.below();
            if (state.hasProperty(DOOR_HINGE)) {
                if (state.hasProperty(DOOR_HINGE) && state.hasProperty(HORIZONTAL_FACING)) {
                    BlockPos pos2 = pos1.relative(state.getValue(DOOR_HINGE) == LEFT ? dir.getClockWise() : dir.getCounterClockWise());
                    if (levelAccessor.getBlockState(pos2).is(state.getBlock())) {
                        if (state.getValue(DOOR_HINGE) == LEFT) {
                            return null;
                        }
                        pos1 = pos2;
                    }
                }
                dir = dir.getOpposite();
            }
        }
        Cuboid6i bb = new Cuboid6i(blockPos, pos1);
        ItemStack stack = CommonConfig.getRandomLock(randomSource);
        Lock lock = Lock.from(stack);
        Transform tr = Transform.fromDirection(dir, dir);
        return new Lockable(bb, lock, tr, stack, level);
    }

    public static Lockable lockCheck(LevelAccessor levelAccessor, ServerLevel level, BlockPos blockPos, RandomSource randomSource) {
        if (levelAccessor.hasChunk(blockPos.getX() >> 4, blockPos.getZ() >> 4)){
            return lockWhenGen(levelAccessor, level, blockPos, randomSource);
        }
        return null;
    }

    public static boolean lockChunk(LevelAccessor levelAccessor, ServerLevel level, BlockPos blockPos, RandomSource randomSource, ChunkAccess chunkAccess){
        Lockable lkb = CommonUtil.lockCheck(levelAccessor, level, blockPos, randomSource);
        if (lkb == null) return false;
        ((ILockableProvider) chunkAccess).getLockables().add(lkb);
        return true;
    }

    public static boolean lockChunk(LevelAccessor levelAccessor, ServerLevel level, BlockPos blockPos, RandomSource randomSource){
        Lockable lkb = CommonUtil.lockCheck(levelAccessor, level, blockPos, randomSource);
        if (lkb == null) return false;
        lkb.bb.getContainedChunks((x, z) -> {
            ((ILockableProvider) levelAccessor.getChunk(x, z)).getLockables().add(lkb);
            return true;
        });
        return true;
    }
}