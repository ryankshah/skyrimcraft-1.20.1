package com.ryankshah.skyrimcraft.data.loot_table;

import com.ryankshah.skyrimcraft.Constants;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

import java.util.concurrent.CompletableFuture;

public class SkyrimLootModifierProvider extends GlobalLootModifierProvider
{
    protected CompletableFuture<HolderLookup.Provider> registries;
    public SkyrimLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, String modid) {
        super(output, modid);
        this.registries = registries;
    }

    @Override
    protected void start() {
        HolderGetter<EntityType<?>> holdergetter = this.registries.getNow(null).lookupOrThrow(Registries.ENTITY_TYPE);
        this.add("grass_pod_from_small_vegetation",
                new AddTableLootModifier(
                    new LootItemCondition[] {
                            LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.TALL_GRASS)
                                    .or(LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.LARGE_FERN)).build()
                    },
                    new ResourceLocation(Constants.MODID, "grasspod")
                )
        );

        this.add("gem_drops_from_ores",
                new AddTableLootModifier(
                        new LootItemCondition[] {
                                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.COAL_ORE)
                                        .or(LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.DEEPSLATE_COAL_ORE))
                                        .or(LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.COPPER_ORE))
                                        .or(LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.DEEPSLATE_COPPER_ORE))
                                        .or(LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.IRON_ORE))
                                        .or(LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.DEEPSLATE_IRON_ORE))
                                        .build()
                        },
                        new ResourceLocation(Constants.MODID, "oregemdrops")
                )
        );

        this.add("beehive",
                new AddTableLootModifier(
                        new LootItemCondition[] {
                                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.BEEHIVE).build()
                        },
                        new ResourceLocation(Constants.MODID, "beehive")
                )
        );

        this.add("spider",
                new AddTableLootModifier(
                        new LootItemCondition[] {
                                LootItemKilledByPlayerCondition.killedByPlayer()
                                        .and(LootItemEntityPropertyCondition.hasProperties(
                                                LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().of(EntityType.SPIDER))
                                        ).build()
                        },
                        new ResourceLocation(Constants.MODID, "spider")
                )
        );

        this.add("witch",
                new AddTableLootModifier(
                        new LootItemCondition[] {
                                LootItemKilledByPlayerCondition.killedByPlayer()
                                        .and(LootItemEntityPropertyCondition.hasProperties(
                                                LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().of(EntityType.WITCH)
                                        )).build()
                        },
                        new ResourceLocation(Constants.MODID, "witch")
                )
        );

        this.add("evoker",
                new AddTableLootModifier(
                        new LootItemCondition[] {
                                LootItemKilledByPlayerCondition.killedByPlayer()
                                        .and(LootItemEntityPropertyCondition.hasProperties(
                                                LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().of(EntityType.EVOKER)
                                        )).build()
                        },
                        new ResourceLocation(Constants.MODID, "evoker")
                )
        );

        this.add("salmon",
                new AddTableLootModifier(
                        new LootItemCondition[] {
                                LootItemKilledByPlayerCondition.killedByPlayer()
                                        .and(LootItemEntityPropertyCondition.hasProperties(
                                                LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().of(EntityType.SALMON)
                                        )).build()
                        },
                        new ResourceLocation(Constants.MODID, "salmon")
                )
        );

        this.add("goat",
                new AddTableLootModifier(
                        new LootItemCondition[] {
                                LootItemKilledByPlayerCondition.killedByPlayer()
                                        .and(LootItemEntityPropertyCondition.hasProperties(
                                                LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().of(EntityType.GOAT)
                                )).build()
                        },
                        new ResourceLocation(Constants.MODID, "goat")
                )
        );

        this.add("bee",
                new AddTableLootModifier(
                        new LootItemCondition[] {
                                LootItemKilledByPlayerCondition.killedByPlayer()
                                        .and(LootItemEntityPropertyCondition.hasProperties(
                                                LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().of(EntityType.BEE)
                                        )).build()
                        },
                        new ResourceLocation(Constants.MODID, "bee")
                )
        );


        this.add("chests/simple_dungeon", new AddTableLootModifier(
            new LootItemCondition[] {
                    LootTableIdCondition.builder(new ResourceLocation("chests/simple_dungeon")).build()
            }, new ResourceLocation(Constants.MODID, "chests/simple_dungeon")
        ));

        this.add("chests/abandoned_mineshaft", new AddTableLootModifier(
                new LootItemCondition[] {
                        LootTableIdCondition.builder(new ResourceLocation("chests/abandoned_mineshaft")).build()
                }, new ResourceLocation(Constants.MODID, "chests/abandoned_mineshaft")
        ));
        this.add("chests/buried_treasure", new AddTableLootModifier(
                new LootItemCondition[] {
                        LootTableIdCondition.builder(new ResourceLocation("chests/buried_treasure")).build()
                }, new ResourceLocation(Constants.MODID, "chests/buried_treasure")
        ));
        this.add("chests/desert_pyramid", new AddTableLootModifier(
                new LootItemCondition[] {
                        LootTableIdCondition.builder(new ResourceLocation("chests/desert_pyramid")).build()
                }, new ResourceLocation(Constants.MODID, "chests/desert_pyramid")
        ));
        this.add("chests/shipwreck_supply", new AddTableLootModifier(
                new LootItemCondition[] {
                        LootTableIdCondition.builder(new ResourceLocation("chests/shipwreck_supply")).build()
                }, new ResourceLocation(Constants.MODID, "chests/shipwreck_supply")
        ));
        this.add("chests/stronghold_corridor", new AddTableLootModifier(
                new LootItemCondition[] {
                        LootTableIdCondition.builder(new ResourceLocation("chests/stronghold_corridor")).build()
                }, new ResourceLocation(Constants.MODID, "chests/stronghold_corridor")
        ));
        this.add("gameplay/piglin_bartering", new AddTableLootModifier(
                new LootItemCondition[] {
                        LootTableIdCondition.builder(new ResourceLocation("gameplay/piglin_bartering")).build()
                }, new ResourceLocation(Constants.MODID, "gameplay/piglin_bartering")
        ));
        this.add("gameplay/sniffer_digging", new AddTableLootModifier(
                new LootItemCondition[] {
                        LootTableIdCondition.builder(new ResourceLocation("gameplay/sniffer_digging")).build()
                }, new ResourceLocation(Constants.MODID, "gameplay/sniffer_digging")
        ));
    }
}
