package com.ryankshah.skyrimcraft.registry;

import com.mojang.blaze3d.platform.InputConstants;
import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.attachment.Character;
import com.ryankshah.skyrimcraft.character.magic.Spell;
import com.ryankshah.skyrimcraft.character.magic.SpellRegistry;
import com.ryankshah.skyrimcraft.network.skill.HandlePickpocket;
import com.ryankshah.skyrimcraft.network.spell.CastSpell;
import com.ryankshah.skyrimcraft.screen.MenuScreen;
import com.ryankshah.skyrimcraft.util.ClientUtil;
import commonnetwork.api.Dispatcher;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import org.lwjgl.glfw.GLFW;

public class KeysRegistry
{
    public static void init() {}

    public static final KeyMapping MENU_KEY = new KeyMapping(
            "key." + Constants.MODID + ".toggle_menu", // Will be localized using this translation key
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_M, // Default key is M (people wanted tab but no can do)
            "key.categories.skyrimcraft" // Mapping will be in the misc category
    );
    public static final KeyMapping SPELL_SLOT_1_KEY = new KeyMapping(
            "key." + Constants.MODID + ".toggle_spell_1", // Will be localized using this translation key
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_V, // Default key is V
            "key.categories.skyrimcraft" // Mapping will be in the misc category
    );
    public static final KeyMapping SPELL_SLOT_2_KEY = new KeyMapping(
            "key." + Constants.MODID + ".toggle_spell_2", // Will be localized using this translation key
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_B, // Default key is B
            "key.categories.skyrimcraft" // Mapping will be in the misc category
    );
    public static final KeyMapping PICKPOCKET_KEY = new KeyMapping(
            "key." + Constants.MODID + ".toggle_pickpocket", // Will be localized using this translation key
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_H, // Default key is H
            "key.categories.skyrimcraft" // Mapping will be in the misc category
    );

    public static final KeyMapping SKYRIM_MENU_ENTER = new KeyMapping(
            "key." + Constants.MODID + ".menu.enter", // Will be localized using this translation key
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_ENTER, // Default key is P
            "key.categories.skyrimcraft" // Mapping will be in the misc category
    );
    public static final KeyMapping SKYRIM_MENU_NORTH = new KeyMapping(
            "key." + Constants.MODID + ".menu.north", // Will be localized using this translation key
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_UP, // Default key is P
            "key.categories.skyrimcraft" // Mapping will be in the misc category
    );
    public static final KeyMapping SKYRIM_MENU_SOUTH = new KeyMapping(
            "key." + Constants.MODID + ".menu.south", // Will be localized using this translation key
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_DOWN, // Default key is P
            "key.categories.skyrimcraft" // Mapping will be in the misc category
    );
    public static final KeyMapping SKYRIM_MENU_EAST = new KeyMapping(
            "key." + Constants.MODID + ".menu.east", // Will be localized using this translation key
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_LEFT, // Default key is P
            "key.categories.skyrimcraft" // Mapping will be in the misc category
    );
    public static final KeyMapping SKYRIM_MENU_WEST = new KeyMapping(
            "key." + Constants.MODID + ".menu.west", // Will be localized using this translation key
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_RIGHT, // Default key is P
            "key.categories.skyrimcraft" // Mapping will be in the misc category
    );

    public static final KeyMapping SKYRIM_MENU_MB1_CLICK = new KeyMapping(
            "key." + Constants.MODID + ".menu_button_1.click", // Will be localized using this translation key
            InputConstants.Type.MOUSE,
            GLFW.GLFW_MOUSE_BUTTON_1, // Default key is P
            "key.categories.skyrimcraft" // Mapping will be in the misc category
    );

    public static void onKeyInput(int key, int scanCode, int action, int modifiers) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null)
            return;
        if (mc.screen != null)
            return;

        while (MENU_KEY.consumeClick()) {
            mc.setScreen(new MenuScreen());
            return;
        }
        while (SPELL_SLOT_1_KEY.consumeClick()) { // TODO: Check if `isDown` for continuous cast?
            Character character = Character.get(mc.player);
            Spell spell = character.getSelectedSpell1();
            if (spell != null && spell.getID() != SpellRegistry.EMPTY_SPELL.get().getID()) {
                final CastSpell castSpell = new CastSpell(SpellRegistry.SPELLS_REGISTRY.get().getResourceKey(spell).get());
                Dispatcher.sendToServer(castSpell);
//                    PacketDistributor.SERVER.noArg().send(castSpell);
            } else
                mc.player.displayClientMessage(Component.translatable("skyrimcraft.spell.noselect"), false);
            return;
        }
        while (SPELL_SLOT_2_KEY.consumeClick()) {
            Character character = Character.get(mc.player);
            Spell spell = character.getSelectedSpell2();
            if (spell != null && spell.getID() != SpellRegistry.EMPTY_SPELL.get().getID()) {
                final CastSpell castSpell = new CastSpell(SpellRegistry.SPELLS_REGISTRY.get().getResourceKey(spell).get());
                Dispatcher.sendToServer(castSpell);
//                    PacketDistributor.SERVER.noArg().send(castSpell);
            } else
                mc.player.displayClientMessage(Component.translatable("skyrimcraft.spell.noselect"), false);
            return;
        }
        while (PICKPOCKET_KEY.consumeClick()) {
            if (mc.crosshairPickEntity instanceof LivingEntity && mc.player.isCrouching()) {
                LivingEntity entity = (LivingEntity) mc.crosshairPickEntity;

                if(entity.getTags().contains(EntityRegistry.PICKPOCKET_TAG)) {
                    if(ClientUtil.canEntitySee(entity, mc.player)) {
                        mc.player.hurt(mc.player.damageSources().mobAttack(entity), 0.5f);
                        mc.player.knockback(0.5f, (double) -Mth.sin(mc.player.yRotO * ((float)Math.PI / 180F)), (double)(Mth.cos(mc.player.yRotO * ((float)Math.PI / 180F))));
                    } else {
                        final HandlePickpocket handlePickpocket = new HandlePickpocket(entity.getId());
                        Dispatcher.sendToServer(handlePickpocket);
//                            PacketDistributor.SERVER.noArg().send(handlePickpocket);
                    }
                }
            }
        }
    }

    private static boolean didPress(int key, int scanCode, int action, KeyMapping keyBinding) {
        return action == GLFW.GLFW_PRESS && isKey(key,scanCode, keyBinding);
    }
    private static boolean isKey(int key, int scanCode, KeyMapping keyBinding) {
        return keyBinding.matches(key, scanCode);
    }
}