package com.ryankshah.skyrimcraft.network.spell;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.attachment.Character;
import com.ryankshah.skyrimcraft.character.magic.Spell;
import com.ryankshah.skyrimcraft.character.magic.SpellRegistry;
import commonnetwork.api.Dispatcher;
import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public record UpdateShoutCooldown(ResourceKey<Spell> spell, float cooldown)
{
    public static final ResourceLocation TYPE = new ResourceLocation(Constants.MODID, "updateshoutcooldown");

    public UpdateShoutCooldown(final FriendlyByteBuf buffer) {
        this(buffer.readResourceKey(SpellRegistry.SPELLS_KEY), buffer.readFloat());
    }

    public static UpdateShoutCooldown decode(FriendlyByteBuf buf) {
        return new UpdateShoutCooldown(buf.readResourceKey(SpellRegistry.SPELLS_KEY), buf.readFloat());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeResourceKey(spell);
        buf.writeFloat(cooldown);
    }

    public static void handle(PacketContext<UpdateShoutCooldown> context) {
        if(context.side().equals(Side.CLIENT))
            handleClient(context);
        else
            handleServer(context);
    }

    public static void handleServer(PacketContext<UpdateShoutCooldown> context) {
        ServerPlayer serverPlayer = context.sender();
        UpdateShoutCooldown data = context.message();
        Character character = Character.get(serverPlayer);

        character.addSpellAndCooldown(SpellRegistry.SPELLS_REGISTRY.get().get(data.spell), data.cooldown);

        final UpdateShoutCooldown sendToClient = new UpdateShoutCooldown(data.spell, data.cooldown);
        Dispatcher.sendToClient(sendToClient, serverPlayer);
//            PacketDistributor.PLAYER.with(serverPlayer).send(sendToClient);
    }

    public static void handleClient(PacketContext<UpdateShoutCooldown> context) {
        Minecraft minecraft = Minecraft.getInstance();
        UpdateShoutCooldown data = context.message();
        minecraft.execute(() -> {
            Player player = Minecraft.getInstance().player;
            Character character = Character.get(player);
            character.addSpellAndCooldown(SpellRegistry.SPELLS_REGISTRY.get().get(data.spell), data.cooldown);
        });
    }
}
