package com.ryankshah.skyrimcraft.registry;

import com.mojang.serialization.Codec;
import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.particle.EmittingLightningParticle;
import com.ryankshah.skyrimcraft.particle.LightningParticle;
import com.ryankshah.skyrimcraft.registration.RegistrationProvider;
import com.ryankshah.skyrimcraft.registration.RegistryObject;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;

public class ParticleRegistry
{
    public static void init() {}

    public static final RegistrationProvider<ParticleType<?>> PARTICLE_TYPES = RegistrationProvider.get(BuiltInRegistries.PARTICLE_TYPE, Constants.MODID);

    public static RegistryObject<ParticleType<EmittingLightningParticle.EmittingLightningParticleOptions>> EMITTING_LIGHTNING = PARTICLE_TYPES.register("emitting_lightning", () ->
            new ParticleType<EmittingLightningParticle.EmittingLightningParticleOptions>(false, EmittingLightningParticle.EmittingLightningParticleOptions.DESERIALIZER) {
                @Override
                public Codec<EmittingLightningParticle.EmittingLightningParticleOptions> codec() {
                    return null;
                }

//                @Override
//                public StreamCodec<? super RegistryFriendlyByteBuf, EmittingLightningParticle.EmittingLightningParticleOptions> streamCodec() {
//                    return EmittingLightningParticle.EmittingLightningParticleOptions.STREAM_CODEC;
//                }
            });

    public static RegistryObject<ParticleType<LightningParticle.LightningParticleOptions>> LIGHTNING = PARTICLE_TYPES.register("lightning", () ->
            new ParticleType<LightningParticle.LightningParticleOptions>(false, LightningParticle.LightningParticleOptions.DESERIALIZER) {
                @Override
                public Codec<LightningParticle.LightningParticleOptions> codec() {
                    return null;
                }

//                @Override
//                public StreamCodec<? super RegistryFriendlyByteBuf, LightningParticle.LightningParticleOptions> streamCodec() {
//                    return LightningParticle.LightningParticleOptions.STREAM_CODEC;
//                }
            });

//    public static RegistryObject<ParticleType<?>,ParticleType<FireParticle.FireParticleOptions>> FIRE = PARTICLE_TYPES.register("fire", () ->
//            new ParticleType<>(false, FireParticle.FireParticleOptions.DESERIALIZER) {
//                @Override
//                public Codec<FireParticle.FireParticleOptions> codec() {
//                    return null;
//                }
//
//                @Override
//                public StreamCodec<? super RegistryFriendlyByteBuf, FireParticle.FireParticleOptions> streamCodec() {
//                    return null;
//                }
//            });
}