package com.ryankshah.skyrimcraft.network.character;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.attachment.Character;
import com.ryankshah.skyrimcraft.character.feature.Race;
import com.ryankshah.skyrimcraft.character.magic.Spell;
import com.ryankshah.skyrimcraft.character.magic.SpellRegistry;
import commonnetwork.api.Dispatcher;
import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.function.Supplier;

public record CreateCharacter(int raceID, boolean fin) //(int raceID, String raceName, Map<Integer, IntList> skills)
{
    public static final ResourceLocation TYPE = new ResourceLocation(Constants.MODID, "createcharacter");

    public CreateCharacter(final FriendlyByteBuf buf) {
        this(buf.readInt(), buf.readBoolean()); //, buf.readUtf(), buf.readMap(FriendlyByteBuf::readInt, FriendlyByteBuf::readIntIdList));
    }

    public static CreateCharacter decode(FriendlyByteBuf buf) {
        return new CreateCharacter(buf.readInt(), buf.readBoolean());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(raceID);
        buf.writeBoolean(fin);
    }

    public static void handle(PacketContext<CreateCharacter> context) {
        System.out.println("create character handle");
        if(context.side().equals(Side.CLIENT))
            handleClient(context);
        else
            handleServer(context);
    }

    public static void handleServer(PacketContext<CreateCharacter> context) {
        System.out.println("create character server");
        ServerPlayer player = context.sender();
        Race race = Race.getRaces().stream().filter(r -> r.getId() == context.message().raceID()).findFirst().get();
        Character character = Character.get(player);

        character.setRace(race);
        character.setSkills(new ArrayList<>(character.getStartingSkills(race)));
        character.setHasSetup(context.message().fin);

        if(context.message().fin) {
            for (Supplier<Spell> spell : SpellRegistry.getPowersForRace(race)) {
                character.addNewSpell(spell.get());
            }
        }

        final CreateCharacter sendToClient = new CreateCharacter(context.message().raceID, context.message().fin);
        Dispatcher.sendToClient(sendToClient, player);

//        final UpdateCharacter sendToClient = new UpdateCharacter(character);
//        Dispatcher.sendToClient(sendToClient, player);
////        PacketDistributor.PLAYER.with(player).send(sendToClient);
    }

    public static void handleClient(PacketContext<CreateCharacter> context) {
        System.out.println("create character client");
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.execute(() -> {
            Player player = minecraft.player;

            Race race = Race.getRaces().stream().filter(r -> r.getId() == context.message().raceID()).findFirst().get();
            Character character = Character.get(player);

            character.setRace(race);
            character.setSkills(new ArrayList<>(character.getStartingSkills(race)));
            character.setHasSetup(context.message().fin);

            if(context.message().fin) {
                for (Supplier<Spell> spell : SpellRegistry.getPowersForRace(race)) {
                    character.addNewSpell(spell.get());
                }
            }

            final UpdateCharacter sendToServer = new UpdateCharacter(character);
            Dispatcher.sendToServer(sendToServer);
        });
    }
}