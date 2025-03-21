package com.ryankshah.skyrimcraft.network.character;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.attachment.LevelUpdates;
import com.ryankshah.skyrimcraft.platform.Services;
import com.ryankshah.skyrimcraft.screen.SkyrimGuiOverlay;
import com.ryankshah.skyrimcraft.util.LevelUpdate;
import commonnetwork.api.Dispatcher;
import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;

public record AddToLevelUpdates(String updateName, int level, int levelUpRenderTime)
{
    public static final ResourceLocation TYPE = new ResourceLocation(Constants.MODID, "addtolevelupdates");

    public AddToLevelUpdates(final FriendlyByteBuf buffer) {
        this(buffer.readUtf(), buffer.readInt(), buffer.readInt());
    }

    public AddToLevelUpdates() {
        this("", 0, 0);
    }

    public static AddToLevelUpdates decode(FriendlyByteBuf buf) {
        return new AddToLevelUpdates(buf.readUtf(), buf.readInt(), buf.readInt());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUtf(updateName);
        buf.writeInt(level);
        buf.writeInt(levelUpRenderTime);
    }

    public static void handle(PacketContext<AddToLevelUpdates> context) {
        if(context.side().equals(Side.CLIENT)) {
            Minecraft minecraft = Minecraft.getInstance();
            minecraft.execute(() -> {
                Player player = minecraft.player;
                player.playSound(SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, 1.0f, 1.0f);
                if (context.message().updateName.equals("characterLevel")) {
                    Services.PLATFORM.setLevelUpdates(player, new LevelUpdates(Services.PLATFORM.getLevelUpdates(player).getLevelUpdates() +1));
                    Minecraft.getInstance().getToasts().addToast(new SystemToast(SystemToast.SystemToastIds.NARRATOR_TOGGLE, Component.literal("Level Up"), Component.literal("You have a new attribute point to use!")));
                }
                //TODO: add gui overlay
                SkyrimGuiOverlay.LEVEL_UPDATES.add(new LevelUpdate(context.message().updateName, context.message().level, context.message().levelUpRenderTime));
            });
        } else {
            ServerPlayer player = context.sender();
            if(context.message().updateName.equals("characterLevel"))
                Services.PLATFORM.setLevelUpdates(player, new LevelUpdates(Services.PLATFORM.getLevelUpdates(player).getLevelUpdates() +1));
            final AddToLevelUpdates updates = new AddToLevelUpdates(context.message().updateName, context.message().level, context.message().levelUpRenderTime);
            Dispatcher.sendToClient(updates, player);
//        PacketDistributor.PLAYER.with((ServerPlayer) context.player().orElseThrow()).send(updates);
        }
    }
}