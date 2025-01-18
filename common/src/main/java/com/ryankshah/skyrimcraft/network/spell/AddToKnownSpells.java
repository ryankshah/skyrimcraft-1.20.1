package com.ryankshah.skyrimcraft.network.spell;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.advancement.LearnSpellTrigger;
import com.ryankshah.skyrimcraft.character.attachment.Character;
import com.ryankshah.skyrimcraft.character.magic.Spell;
import com.ryankshah.skyrimcraft.character.magic.SpellRegistry;
import com.ryankshah.skyrimcraft.registry.AdvancementTriggersRegistry;
import commonnetwork.api.Dispatcher;
import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.data.advancements.packs.VanillaAdventureAdvancements;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public record AddToKnownSpells(ResourceKey<Spell> spell)
{
    public static final ResourceLocation TYPE = new ResourceLocation(Constants.MODID, "addtoknownspells");

    public AddToKnownSpells(final FriendlyByteBuf buffer) {
        this(buffer.readResourceKey(SpellRegistry.SPELLS_KEY));
    }

    public static AddToKnownSpells decode(FriendlyByteBuf buf) {
        return new AddToKnownSpells(buf.readResourceKey(SpellRegistry.SPELLS_KEY));
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeResourceKey(spell);
    }

    public static void handle(PacketContext<AddToKnownSpells> context) {
        if(context.side().equals(Side.CLIENT))
            handleClient(context);
        else
            handleServer(context);
    }

    public static void handleServer(PacketContext<AddToKnownSpells> context) {
        ServerPlayer player = context.sender();
        Character character = Character.get(player);
        AddToKnownSpells data = context.message();
        character.addNewSpell(SpellRegistry.SPELLS_REGISTRY.get().get(data.spell));

        // TODO: fix the advancements registry and fix this!
        ((LearnSpellTrigger)AdvancementTriggersRegistry.LEARN_SPELL).trigger(player, SpellRegistry.SPELLS_REGISTRY.get().get(data.spell));

        final AddToKnownSpells sendToClient = new AddToKnownSpells(data.spell);
        Dispatcher.sendToClient(sendToClient, player);
//        PacketDistributor.PLAYER.with(player).send(sendToClient);
    }

    public static void handleClient(PacketContext<AddToKnownSpells> context) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.execute(() -> {
            Player player = Minecraft.getInstance().player;
            Character character = Character.get(player);
            character.addNewSpell(SpellRegistry.SPELLS_REGISTRY.get().get(context.message().spell()));
        });
    }
}

