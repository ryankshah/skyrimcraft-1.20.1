package com.ryankshah.skyrimcraft.registry;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.magic.entity.*;
import com.ryankshah.skyrimcraft.entity.boss.dragon.SkyrimDragon;
import com.ryankshah.skyrimcraft.entity.creature.*;
import com.ryankshah.skyrimcraft.entity.furniture.DwemerBench;
import com.ryankshah.skyrimcraft.entity.furniture.DwemerChair;
import com.ryankshah.skyrimcraft.entity.npc.Falmer;
import com.ryankshah.skyrimcraft.entity.npc.Khajiit;
import com.ryankshah.skyrimcraft.entity.passive.flying.*;
import com.ryankshah.skyrimcraft.registration.RegistrationProvider;
import com.ryankshah.skyrimcraft.registration.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;

import java.util.function.BiConsumer;

public class EntityRegistry
{
    public static void init() {}

    public static final String PICKPOCKET_TAG = "pickpocketable";
    public static final RegistrationProvider<EntityType<?>> ENTITY_TYPES = RegistrationProvider.get(Registries.ENTITY_TYPE, Constants.MODID);

    // Spell entity types
    public static final RegistryObject<EntityType<FireballEntity>> SPELL_FIREBALL_ENTITY = ENTITY_TYPES.register("spell_fireball",
            () -> EntityType.Builder.<FireballEntity>of(FireballEntity::new, MobCategory.MISC)
                    .sized(1.25f, 1.25f) // Hitbox Size
                    .clientTrackingRange(64)
                    //.setShouldReceiveVelocityUpdates(true)
                    .updateInterval(2)
                    .build(new ResourceLocation(Constants.MODID,  "spell_fireball").toString()));
    public static final RegistryObject<EntityType<LightningEntity>> SKYRIM_LIGHTNING = ENTITY_TYPES.register("skyrim_lightning", () ->
            EntityType.Builder.<LightningEntity>of(LightningEntity::new, MobCategory.MISC)
                    .build(new ResourceLocation(Constants.MODID,  "skyrim_lightning").toString()));
    public static final RegistryObject<EntityType<LightBallEntity>> LIGHTBALL_ENTITY = ENTITY_TYPES.register("lightball",
            () -> EntityType.Builder.<LightBallEntity>of(LightBallEntity::new, MobCategory.MISC)
                    .sized(1.25f, 1.25f) // Hitbox Size
                    .clientTrackingRange(64)
                    //.setShouldReceiveVelocityUpdates(true)
                    .updateInterval(2)
                    .build(new ResourceLocation(Constants.MODID,  "lightball").toString()));

    // Shout entity types
    public static final RegistryObject<EntityType<UnrelentingForceEntity>> SHOUT_UNRELENTING_FORCE_ENTITY = ENTITY_TYPES.register(
            "shout_unrelenting_force_entity",
            () -> EntityType.Builder.<UnrelentingForceEntity>of(UnrelentingForceEntity::new, MobCategory.MISC)
                    .sized(4f, 4f) // Hitbox Size
                    .clientTrackingRange(64)
                    //.setShouldReceiveVelocityUpdates(true)
                    .updateInterval(2).build(new ResourceLocation(Constants.MODID,  "shout_unrelenting_force").toString()));
    public static final RegistryObject<EntityType<DisarmEntity>> SHOUT_DISARM_ENTITY = ENTITY_TYPES.register(
            "shout_disarm_entity",
            () -> EntityType.Builder.<DisarmEntity>of(DisarmEntity::new, MobCategory.MISC)
                    .sized(4f, 4f) // Hitbox Size
                    .clientTrackingRange(64)
                    //.setShouldReceiveVelocityUpdates(true)
                    .updateInterval(2).build(new ResourceLocation(Constants.MODID,  "shout_disarm").toString()));

    // Mobs
    public static final RegistryObject<EntityType<SabreCat>> SABRE_CAT = ENTITY_TYPES.register("sabre_cat",
            () -> EntityType.Builder.of(SabreCat::new, MobCategory.MONSTER)
                    .sized(1.25f, 1.25f) // Hitbox Size
                    .build(new ResourceLocation(Constants.MODID,  "sabre_cat").toString()));
    public static final RegistryObject<EntityType<ValeSabreCat>> VALE_SABRE_CAT = ENTITY_TYPES.register("vale_sabre_cat",
            () -> EntityType.Builder.of(ValeSabreCat::new, MobCategory.MONSTER)
                    .sized(1.25f, 1.25f) // Hitbox Size
                    .build(new ResourceLocation(Constants.MODID,  "vale_sabre_cat").toString()));
    public static final RegistryObject<EntityType<Giant>> GIANT = ENTITY_TYPES.register("giant",
            () -> EntityType.Builder.of(Giant::new, MobCategory.MONSTER)
                    .sized(1.0f, 4.0f) // Hitbox Size
                    .build(new ResourceLocation(Constants.MODID,  "giant").toString()));
    public static final RegistryObject<EntityType<Mammoth>> MAMMOTH = ENTITY_TYPES.register("mammoth",
            () -> EntityType.Builder.of(Mammoth::new, MobCategory.MONSTER)
                    .sized(4.0f, 4.0f) // Hitbox Size
                    .build(new ResourceLocation(Constants.MODID,  "mammoth").toString()));
    public static final RegistryObject<EntityType<SkyrimDragon>> DRAGON = ENTITY_TYPES.register("dragon",
            () -> EntityType.Builder.of(SkyrimDragon::new, MobCategory.MONSTER)
                    .fireImmune().sized(4.0F, 4.0F).clientTrackingRange(128)
                    .build(new ResourceLocation(Constants.MODID,  "dragon").toString()));
    public static final RegistryObject<EntityType<DwarvenSpider>> DWARVEN_SPIDER = ENTITY_TYPES.register("dwarven_spider",
            () -> EntityType.Builder.of(DwarvenSpider::new, MobCategory.MONSTER)
                    .sized(2.0f, 2.0f) // Hitbox Size
                    .build(new ResourceLocation(Constants.MODID,  "dwarven_spider").toString()));
    public static final RegistryObject<EntityType<DwemerCreeper>> DWEMER_CREEPER = ENTITY_TYPES.register("dwemer_creeper",
            () -> EntityType.Builder.of(DwemerCreeper::new, MobCategory.MONSTER)
                    .sized(1.0f, 2.0f) // Hitbox Size
                    .build(new ResourceLocation(Constants.MODID,  "dwemer_creeper").toString()));
    public static final RegistryObject<EntityType<DwemerCube>> DWEMER_CUBE = ENTITY_TYPES.register("dwemer_cube",
            () -> EntityType.Builder.of(DwemerCube::new, MobCategory.MONSTER)
                    .sized(1.0f, 2.0f) // Hitbox Size
                    .build(new ResourceLocation(Constants.MODID,  "dwemer_cube").toString()));
    public static final RegistryObject<EntityType<BlueButterfly>> BLUE_BUTTERFLY = ENTITY_TYPES.register("blue_butterfly",
            () -> EntityType.Builder.of(BlueButterfly::new, MobCategory.AMBIENT)
                    .sized(0.5f, 0.5f) // Hitbox Size
                    .build(new ResourceLocation(Constants.MODID,  "blue_butterfly").toString()));
    public static final RegistryObject<EntityType<MonarchButterfly>> MONARCH_BUTTERFLY = ENTITY_TYPES.register("monarch_butterfly",
            () -> EntityType.Builder.of(MonarchButterfly::new, MobCategory.AMBIENT)
                    .sized(0.5f, 0.5f) // Hitbox Size
                    .build(new ResourceLocation(Constants.MODID,  "monarch_butterfly").toString()));
    public static final RegistryObject<EntityType<OrangeDartwing>> ORANGE_DARTWING = ENTITY_TYPES.register("orange_dartwing",
            () -> EntityType.Builder.of(OrangeDartwing::new, MobCategory.AMBIENT)
                    .sized(0.5f, 0.5f) // Hitbox Size
                    .build(new ResourceLocation(Constants.MODID,  "orange_dartwing").toString()));
    public static final RegistryObject<EntityType<BlueDartwing>> BLUE_DARTWING = ENTITY_TYPES.register("blue_dartwing",
            () -> EntityType.Builder.of(BlueDartwing::new, MobCategory.AMBIENT)
                    .sized(0.5f, 0.5f) // Hitbox Size
                    .build(new ResourceLocation(Constants.MODID,  "blue_dartwing").toString()));
    public static final RegistryObject<EntityType<LunarMoth>> LUNAR_MOTH = ENTITY_TYPES.register("lunar_moth",
            () -> EntityType.Builder.of(LunarMoth::new, MobCategory.AMBIENT)
                    .sized(0.5f, 0.5f) // Hitbox Size
                    .build(new ResourceLocation(Constants.MODID,  "lunar_moth").toString()));
    public static final RegistryObject<EntityType<TorchBug>> TORCHBUG = ENTITY_TYPES.register("torchbug",
            () -> EntityType.Builder.of(TorchBug::new, MobCategory.AMBIENT)
                    .sized(0.5f, 0.5f) // Hitbox Size
                    .build(new ResourceLocation(Constants.MODID,  "torchbug").toString()));
    public static final RegistryObject<EntityType<Khajiit>> KHAJIIT = ENTITY_TYPES.register("khajiit",
            () -> EntityType.Builder.of(Khajiit::new, MobCategory.AMBIENT)
                    .sized(1.0f, 2.0f) // Hitbox Size
                    .build(new ResourceLocation(Constants.MODID,  "khajiit").toString()));
    public static final RegistryObject<EntityType<Falmer>> FALMER = ENTITY_TYPES.register("falmer",
            () -> EntityType.Builder.of(Falmer::new, MobCategory.AMBIENT)
                    .sized(1.0f, 2.0f) // Hitbox Size
                    .build(new ResourceLocation(Constants.MODID,  "falmer").toString()));
    public static final RegistryObject<EntityType<Draugr>> DRAUGR = ENTITY_TYPES.register("draugr",
            () -> EntityType.Builder.of(Draugr::new, MobCategory.MONSTER)
                    .sized(1.0f, 2.0f) // Hitbox Size
                    .build(new ResourceLocation(Constants.MODID,  "draugr").toString()));
    public static final RegistryObject<EntityType<Skeever>> SKEEVER = ENTITY_TYPES.register("skeever",
            () -> EntityType.Builder.of(Skeever::new, MobCategory.MONSTER)
                    .sized(1.0f, 1.0f) // Hitbox Size
                    .build(new ResourceLocation(Constants.MODID,  "skeever").toString()));
    public static final RegistryObject<EntityType<VenomfangSkeever>> VENOMFANG_SKEEVER = ENTITY_TYPES.register("venomfang_skeever",
            () -> EntityType.Builder.of(VenomfangSkeever::new, MobCategory.MONSTER)
                    .sized(1.0f, 1.0f) // Hitbox Size
                    .build(new ResourceLocation(Constants.MODID,  "venomfang_skeever").toString()));
    public static final RegistryObject<EntityType<Vampire>> VAMPIRE = ENTITY_TYPES.register("vampire",
            () -> EntityType.Builder.of(Vampire::new, MobCategory.MONSTER)
                    .sized(1.0f, 1.0f) // Hitbox Size
                    .build(new ResourceLocation(Constants.MODID,  "vampire").toString()));

    public static final RegistryObject<EntityType<AbeceanLongfin>> ABECEAN_LONGFIN = ENTITY_TYPES.register("abecean_longfin",
            () -> EntityType.Builder.of(AbeceanLongfin::new, MobCategory.WATER_CREATURE)
                    .sized(1.0f, 1.0f) // Hitbox Size
                    .build(new ResourceLocation(Constants.MODID,  "abecean_longfin").toString()));

    public static final RegistryObject<EntityType<CyrodilicSpadetail>> CYRODILIC_SPADETAIL = ENTITY_TYPES.register("cyrodilic_spadetail",
            () -> EntityType.Builder.of(CyrodilicSpadetail::new, MobCategory.WATER_CREATURE)
                    .sized(1.0f, 1.0f) // Hitbox Size
                    .build(new ResourceLocation(Constants.MODID,  "cyrodilic_spadetail").toString()));

    public static final RegistryObject<EntityType<Slaughterfish>> SLAUGHTERFISH = ENTITY_TYPES.register("slaughterfish",
            () -> EntityType.Builder.of(Slaughterfish::new, MobCategory.WATER_CREATURE)
                    .sized(1.0f, 1.0f) // Hitbox Size
                    .build(new ResourceLocation(Constants.MODID,  "slaughterfish").toString()));

    // Furniture
    public static final RegistryObject<EntityType<Entity>> DWEMER_CHAIR = ENTITY_TYPES.register("dwemer_chair",
            () -> EntityType.Builder.of(DwemerChair::new, MobCategory.MISC)
                    .sized(1.0f, 1.0f) // Hitbox Size
                    .build(new ResourceLocation(Constants.MODID,  "dwemer_chair").toString()));
    public static final RegistryObject<EntityType<Entity>> DWEMER_BENCH = ENTITY_TYPES.register("dwemer_metal_bench",
            () -> EntityType.Builder.of(DwemerBench::new, MobCategory.MISC)
                    .sized(1.0f, 1.0f) // Hitbox Size
                    .build(new ResourceLocation(Constants.MODID,  "dwemer_metal_bench").toString()));


    public static void registerEntityAttributes(BiConsumer<EntityType<? extends LivingEntity>, AttributeSupplier> registrar) {
        AttributeSupplier.Builder genericAttribs = PathfinderMob.createMobAttributes()
                .add(Attributes.FOLLOW_RANGE, 16)
                .add(Attributes.MAX_HEALTH, 1)
                .add(Attributes.FLYING_SPEED, 1);
        AttributeSupplier.Builder genericMovingAttribs = PathfinderMob.createMobAttributes()
                .add(Attributes.FOLLOW_RANGE, 16)
                .add(Attributes.MAX_HEALTH, 1)
                .add(Attributes.MOVEMENT_SPEED, 0.25f);
        AttributeSupplier.Builder genericMonsterAttribs = Monster.createMobAttributes()
                .add(Attributes.FOLLOW_RANGE, 16)
                .add(Attributes.MAX_HEALTH, 1)
                .add(Attributes.MOVEMENT_SPEED, 0.25f)
                .add(Attributes.ATTACK_DAMAGE, 5)
                .add(Attributes.ATTACK_KNOCKBACK, 0.1);

        registrar.accept(EntityRegistry.SABRE_CAT.get(), SabreCat.createAttributes().build());
        registrar.accept(EntityRegistry.VALE_SABRE_CAT.get(), ValeSabreCat.createAttributes().build());
        registrar.accept(EntityRegistry.GIANT.get(), Giant.createAttributes().build());
        registrar.accept(EntityRegistry.MAMMOTH.get(), Mammoth.createAttributes().build());
        registrar.accept(EntityRegistry.DRAGON.get(), SkyrimDragon.createAttributes().build());
        registrar.accept(EntityRegistry.DWARVEN_SPIDER.get(), DwarvenSpider.createAttributes().build());
        registrar.accept(EntityRegistry.DWEMER_CREEPER.get(), DwemerCreeper.createAttributes().build());
        registrar.accept(EntityRegistry.DWEMER_CUBE.get(), DwemerCube.createAttributes().build());
        registrar.accept(EntityRegistry.BLUE_BUTTERFLY.get(), BlueButterfly.createAttributes().build());
        registrar.accept(EntityRegistry.MONARCH_BUTTERFLY.get(), MonarchButterfly.createAttributes().build());
        registrar.accept(EntityRegistry.ORANGE_DARTWING.get(), OrangeDartwing.createAttributes().build());
        registrar.accept(EntityRegistry.BLUE_DARTWING.get(), BlueDartwing.createAttributes().build());
        registrar.accept(EntityRegistry.LUNAR_MOTH.get(), LunarMoth.createAttributes().build());
        registrar.accept(EntityRegistry.TORCHBUG.get(), TorchBug.createAttributes().build());
        registrar.accept(EntityRegistry.KHAJIIT.get(), Khajiit.createMobAttributes().build());
        registrar.accept(EntityRegistry.FALMER.get(), Falmer.createMobAttributes().build());
        registrar.accept(EntityRegistry.DRAUGR.get(), Draugr.createAttributes().build());
        registrar.accept(EntityRegistry.SKEEVER.get(), Skeever.createAttributes().build());
        registrar.accept(EntityRegistry.VENOMFANG_SKEEVER.get(), VenomfangSkeever.createAttributes().build());
        registrar.accept(EntityRegistry.VAMPIRE.get(), Vampire.createAttributes().build());

        registrar.accept(EntityRegistry.ABECEAN_LONGFIN.get(), AbeceanLongfin.createAttributes().build());
        registrar.accept(EntityRegistry.CYRODILIC_SPADETAIL.get(), AbeceanLongfin.createAttributes().build());
        registrar.accept(EntityRegistry.SLAUGHTERFISH.get(), Slaughterfish.createAttributes().build());

        registrar.accept(EntityRegistry.LIGHTBALL_ENTITY.get(), LightBallEntity.createAttributes().build());
    }
}