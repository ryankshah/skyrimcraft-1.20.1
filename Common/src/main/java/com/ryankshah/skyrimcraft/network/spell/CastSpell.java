package com.ryankshah.skyrimcraft.network.spell;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.magic.Spell;
import com.ryankshah.skyrimcraft.character.magic.SpellRegistry;
import commonnetwork.networking.data.PacketContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public record CastSpell(ResourceKey<Spell> spell)
{
    public static final ResourceLocation TYPE = new ResourceLocation(Constants.MODID, "castspell");

    public CastSpell(final FriendlyByteBuf buffer) {
        this(buffer.readResourceKey(SpellRegistry.SPELLS_KEY));
    }

    public static CastSpell decode(FriendlyByteBuf buf) {
        return new CastSpell(buf.readResourceKey(SpellRegistry.SPELLS_KEY));
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeResourceKey(SpellRegistry.SPELLS_KEY);
    }

    public static void handle(final PacketContext<CastSpell> context) {
        if (context.sender() instanceof ServerPlayer) {
            ServerPlayer serverPlayer = (ServerPlayer) context.sender();
            Spell spellInstance = SpellRegistry.SPELLS_REGISTRY.get(context.message().spell());
            spellInstance.setCaster(serverPlayer);
            spellInstance.cast();
        }
    }
}
