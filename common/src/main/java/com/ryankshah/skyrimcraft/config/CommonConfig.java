package com.ryankshah.skyrimcraft.config;

import com.google.common.collect.Lists;
import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.util.CommonUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.regex.Pattern;

public class CommonConfig
{
    public static final double generationChance = 0.85d;
    public static final double generationEnchantChance = 0.4d;
    public static final boolean randomizeLoadedLocks = true;
    private static final List<? extends String> locks = Lists.newArrayList(Constants.MODID + ":novice_lock", Constants.MODID + ":apprentice_lock", Constants.MODID + ":adept_lock", Constants.MODID + ":expert_lock", Constants.MODID + ":master_lock");
    private static final List<? extends Integer> weights = Lists.newArrayList(5, 4, 2, 3, 1);
    public static int weightTotal;
    public static Pattern[] lockableGenBlocks = Lists.newArrayList("minecraft:chest", "minecraft:barrel", "lootr:.*", "quark:.*_chest").stream().map(Pattern::compile).toArray(Pattern[]::new);

    public static final NavigableMap<Integer, Item> weightedGeneratedLocks = new TreeMap<Integer, Item>() {{
        weightTotal = 0;
        for (int a = 0; a < locks.size(); ++a) {
            weightTotal += weights.get(a);
            put(weightTotal, BuiltInRegistries.ITEM.get(new ResourceLocation(locks.get(a))));
        }
    }};

    public static final int MAX_LOCKABLE_VOLUME = 6;
    public static final List<? extends String> LOCKABLE_BLOCKS = Lists.newArrayList(".*chest", ".*barrel", ".*hopper", ".*door", ".*trapdoor", ".*fence_gate", ".*shulker_box");
    public static final boolean ALLOW_REMOVING_LOCKS = true;
    public static final boolean PROTECT_LOCKABLES = true;
    public static final boolean EASY_LOCK = true;
    public static final boolean STRONG_PREVENTION = false;
    public static Pattern[] lockableBlocks = LOCKABLE_BLOCKS.stream().map(Pattern::compile).toArray(Pattern[]::new);

    public static boolean canLock(Level world, BlockPos pos) {
        Block block = world.getBlockState(pos).getBlock();
        String name = BuiltInRegistries.BLOCK.getKey(block).toString();
        for (Pattern p : lockableBlocks) {
            if (p.matcher(name).matches()) {
                return true;
            }
        }
        return false;
    }

    public static boolean canGen(RandomSource rng, Block block) {
        boolean random = CommonUtil.chance(rng, generationChance);
        return random && matchString(block);
    }

    public static boolean matchString(Block block) {
        String name = BuiltInRegistries.BLOCK.getKey(block).toString();
        for (Pattern p : lockableGenBlocks) {
            if (p.matcher(name).matches()) {
                return true;
            }
        }
        return false;
    }

    public static boolean canEnchant(RandomSource rng) {
        return CommonUtil.chance(rng, generationEnchantChance);
    }

    public static ItemStack getRandomLock(RandomSource rng) {
        ItemStack stack = new ItemStack(weightedGeneratedLocks.ceilingEntry(rng.nextInt(weightTotal) + 1).getValue());
        return canEnchant(rng) ? EnchantmentHelper.enchantItem(rng, stack, 5 + rng.nextInt(30), false) : stack;
    }
}