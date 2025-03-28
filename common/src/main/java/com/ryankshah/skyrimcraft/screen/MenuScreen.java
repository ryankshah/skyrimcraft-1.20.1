package com.ryankshah.skyrimcraft.screen;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.attachment.Character;
import com.ryankshah.skyrimcraft.character.magic.Spell;
import com.ryankshah.skyrimcraft.platform.Services;
import com.ryankshah.skyrimcraft.registry.KeysRegistry;
import com.ryankshah.skyrimcraft.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class MenuScreen extends Screen
{
    protected static final ResourceLocation MENU_ICONS = new ResourceLocation(Constants.MODID, "textures/gui/cross.png");
    private final ResourceLocation OVERLAY_ICONS = new ResourceLocation(Constants.MODID, "textures/gui/overlay_icons.png");
    protected static final int PLAYER_BAR_MAX_WIDTH = 78;

    private Direction currentDirection;


    Component SKILLS = Component.translatable("skyrimcraft.menu.skills");
    Component MAP = Component.translatable("skyrimcraft.menu.map");
    Component QUESTS = Component.translatable("skyrimcraft.menu.quests");
    Component MAGIC = Component.translatable("skyrimcraft.menu.magic");

    public MenuScreen() {
        super(Component.translatable(Constants.MODID + ".menu.title"));

        this.currentDirection = Direction.NONE;
    }

    private double getXpNeededForNextCharacterLevel(int level) {
        return 12.5*Math.pow(level+1, 2) + 62.5*level - 75;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        PoseStack poseStack = graphics.pose();
        Minecraft mc = this.minecraft;
        Window window = mc.getWindow();
        int scaledWidth = window.getGuiScaledWidth();
        int scaledHeight = window.getGuiScaledHeight();

        Character character = Services.PLATFORM.getCharacter(mc.player); //mc.player.getData(PlayerAttachments.CHARACTER);
        float characterProgress = character.getCharacterTotalXp() / (float)getXpNeededForNextCharacterLevel(character.getCharacterLevel()+1);
        float characterProgressBarWidth = PLAYER_BAR_MAX_WIDTH * characterProgress;

        poseStack.pushPose();
        RenderUtil.bind(MENU_ICONS);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
//        this.renderTransparentBackground(graphics);
        this.renderBackground(graphics);
        RenderUtil.blitWithBlend(poseStack, scaledWidth / 2 - 103, scaledHeight / 2 - 50, 24, 81, 207, 100, 256, 256, 1, 1.0f);

        if(currentDirection == Direction.NORTH) {
            RenderUtil.blitWithBlend(poseStack, scaledWidth / 2 - 9, scaledHeight / 2 - 85 - 9, 0, 0, 18, 17, 256, 256, 2, 1.0f);
            graphics.drawCenteredString(font, SKILLS, this.width / 2, this.height / 2 - 65, 0x00FFFFFF);
            graphics.drawCenteredString(font, MAP, this.width / 2, this.height / 2 + 55, 0x00FFFFFF);
            graphics.drawCenteredString(font, QUESTS, this.width / 2 + 99 + font.width(QUESTS), this.height / 2 - 4, 0x00FFFFFF);
            graphics.drawCenteredString(font, MAGIC, this.width / 2 - 103 - font.width(MAGIC), this.height / 2 - 4, 0x00FFFFFF);
        } else if(currentDirection == Direction.SOUTH) {
            RenderUtil.blitWithBlend(poseStack, scaledWidth / 2 - 9, scaledHeight / 2 + 65 + 9, 18, 0, 18, 17, 256, 256, 2, 1.0f);
            graphics.drawCenteredString(font, SKILLS, this.width / 2, this.height / 2 - 65, 0x00FFFFFF);
            graphics.drawCenteredString(font, MAP, this.width / 2, this.height / 2 + 55, 0x00FFFFFF);
            graphics.drawCenteredString(font, QUESTS, this.width / 2 + 99 + font.width(QUESTS), this.height / 2 - 4, 0x00FFFFFF);
            graphics.drawCenteredString(font, MAGIC, this.width / 2 - 103 - font.width(MAGIC), this.height / 2 - 4, 0x00FFFFFF);
        } else if(currentDirection == Direction.EAST) {
            RenderUtil.blitWithBlend(poseStack, scaledWidth / 2 - 160 - 9, scaledHeight / 2 - 9, 53, 0, 18, 17, 256, 256, 2, 1.0f);
            graphics.drawCenteredString(font, SKILLS, this.width / 2, this.height / 2 - 65, 0x00FFFFFF);
            graphics.drawCenteredString(font, MAP, this.width / 2, this.height / 2 + 55, 0x00FFFFFF);
            graphics.drawCenteredString(font, QUESTS, this.width / 2 + 99 + font.width(QUESTS), this.height / 2 - 4, 0x00FFFFFF);
            graphics.drawCenteredString(font, MAGIC, this.width / 2 - 103 - font.width(MAGIC), this.height / 2 - 4, 0x00FFFFFF);
        } else if(currentDirection == Direction.WEST) {
            RenderUtil.blitWithBlend(poseStack, scaledWidth / 2 + 150 + 9, scaledHeight / 2 - 9, 36, 0, 18, 17, 256, 256, 2, 1.0f);
            graphics.drawCenteredString(font, SKILLS, this.width / 2, this.height / 2 - 65, 0x00FFFFFF);
            graphics.drawCenteredString(font, MAP, this.width / 2, this.height / 2 + 55, 0x00FFFFFF);
            graphics.drawCenteredString(font, QUESTS, this.width / 2 + 99 + font.width(QUESTS), this.height / 2 - 4, 0x00FFFFFF);
            graphics.drawCenteredString(font, MAGIC, this.width / 2 - 103 - font.width(MAGIC), this.height / 2 - 4, 0x00FFFFFF);
        } else {
            graphics.drawCenteredString(font, SKILLS, this.width / 2, this.height / 2 - 65, 0x00FFFFFF);
            graphics.drawCenteredString(font, MAP, this.width / 2, this.height / 2 + 55, 0x00FFFFFF);
            graphics.drawCenteredString(font, QUESTS, this.width / 2 + 99 + font.width(QUESTS), this.height / 2 - 4, 0x00FFFFFF);
            graphics.drawCenteredString(font, MAGIC, this.width / 2 - 103 - font.width(MAGIC), this.height / 2 - 4, 0x00FFFFFF);
        }
        // Render bottom bars
        graphics.fillGradient(0, this.height * 3 / 4 + 20, this.width, this.height, 0xAA000000, 0xAA000000);
        graphics.fillGradient(0, this.height * 3 / 4 + 22, this.width, this.height * 3 / 4 + 23, 0xFF6E6B64, 0xFF6E6B64);

        poseStack.pushPose();
        RenderUtil.bind(OVERLAY_ICONS);
        RenderUtil.blitWithBlend(poseStack, 39, this.height - 25, 0, 51, 102, 10, 256, 256, 0, 1);
        RenderUtil.blitWithBlend(poseStack, 51, this.height - 23, 96 + ((PLAYER_BAR_MAX_WIDTH - characterProgressBarWidth) / 2.0f), 64, (int)(78 * characterProgress), 6, 256, 256, 0, 1);
        poseStack.popPose();

        String time = calculateSkyrimTime(minecraft.player.level());
        drawScaledString(graphics, time,this.width - font.width(time) + 30, this.height - 24, 0xFFFFFFFF, 0.75F);

        poseStack.popPose();

//        minecraft.getTextureManager().bind(AbstractGui.GUI_ICONS_LOCATION);
    }

    // Helper method to check if mouse is over a given area
    private boolean isMouseOver(int mouseX, int mouseY, int x, int y, int width, int height) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        // Calculate positions of menu directions
        int skillsX = this.width / 2 - font.width(SKILLS) / 2;
        int skillsY = this.height / 2 - 65;
        int mapX = this.width / 2 - font.width(MAP) / 2;
        int mapY = this.height / 2 + 55;
        int questsX = this.width / 2 + 99 - font.width(QUESTS) / 2;
        int questsY = this.height / 2 - 4;
        int magicX = this.width / 2 - 103 - 2*font.width(MAGIC);
        int magicY = this.height / 2 - 4;

        // Detect mouse hover and update currentDirection
        if (isMouseOver((int)mouseX, (int)mouseY, skillsX, skillsY, font.width(SKILLS), font.lineHeight)) {
            currentDirection = Direction.NORTH;
            GLFW.glfwSetCursor(minecraft.getWindow().getWindow(), GLFW.glfwCreateStandardCursor(GLFW.GLFW_HAND_CURSOR));
            return true;
        } else if (isMouseOver((int)mouseX, (int)mouseY, mapX, mapY, font.width(MAP), font.lineHeight)) {
            currentDirection = Direction.SOUTH;
            GLFW.glfwSetCursor(minecraft.getWindow().getWindow(), GLFW.glfwCreateStandardCursor(GLFW.GLFW_HAND_CURSOR));
            return true;
        } else if (isMouseOver((int)mouseX, (int)mouseY, questsX, questsY, font.width(QUESTS), font.lineHeight)) {
            currentDirection = Direction.WEST;
            GLFW.glfwSetCursor(minecraft.getWindow().getWindow(), GLFW.glfwCreateStandardCursor(GLFW.GLFW_HAND_CURSOR));
            return true;
        } else if (isMouseOver((int)mouseX, (int)mouseY, magicX, magicY, font.width(MAGIC), font.lineHeight)) {
            currentDirection = Direction.EAST;
            GLFW.glfwSetCursor(minecraft.getWindow().getWindow(), GLFW.glfwCreateStandardCursor(GLFW.GLFW_HAND_CURSOR));
            return true;
        } else {
            currentDirection = Direction.NONE;
            GLFW.glfwSetCursor(minecraft.getWindow().getWindow(), GLFW.glfwCreateStandardCursor(GLFW.GLFW_ARROW_CURSOR));
        }
        
        return super.isMouseOver(mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) { // Left click
            if (currentDirection == Direction.NORTH) {
                GLFW.glfwSetCursor(minecraft.getWindow().getWindow(), GLFW.glfwCreateStandardCursor(GLFW.GLFW_ARROW_CURSOR));
                minecraft.setScreen(null);
                minecraft.setScreen(new SkillScreen());
            } else if (currentDirection == Direction.SOUTH) {
                GLFW.glfwSetCursor(minecraft.getWindow().getWindow(), GLFW.glfwCreateStandardCursor(GLFW.GLFW_ARROW_CURSOR));
                minecraft.setScreen(null);
                minecraft.setScreen(new MapScreen());
                minecraft.player.displayClientMessage(Component.translatable("skyrimcraft.menu.option.unavailable"), false);
            } else if (currentDirection == Direction.WEST) {
                GLFW.glfwSetCursor(minecraft.getWindow().getWindow(), GLFW.glfwCreateStandardCursor(GLFW.GLFW_ARROW_CURSOR));
                minecraft.setScreen(null);
                minecraft.player.displayClientMessage(Component.translatable("skyrimcraft.menu.option.unavailable"), false);
            } else if (currentDirection == Direction.EAST) {
                AtomicReference<List<Spell>> knownSpells;
                if(minecraft.player != null)
                    knownSpells = new AtomicReference<>(Services.PLATFORM.getCharacter(Minecraft.getInstance().player).getKnownSpells()); //Minecraft.getInstance().player.getData(PlayerAttachments.CHARACTER).getKnownSpells());
                else
                    knownSpells = new AtomicReference<>(new ArrayList<>());

                if(knownSpells.get().isEmpty()) {
                    GLFW.glfwSetCursor(minecraft.getWindow().getWindow(), GLFW.glfwCreateStandardCursor(GLFW.GLFW_ARROW_CURSOR));
                    minecraft.setScreen(null);
                    minecraft.player.displayClientMessage(Component.translatable("skyrimcraft.menu.option.magic.none"), false);
                } else {
                    GLFW.glfwSetCursor(minecraft.getWindow().getWindow(), GLFW.glfwCreateStandardCursor(GLFW.GLFW_ARROW_CURSOR));
                    minecraft.setScreen(null);
                    minecraft.setScreen(new MagicScreen(knownSpells.get()));
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    public void drawScaledString(GuiGraphics graphics, String str, int x, int y, int color, float scale) {
        graphics.pose().pushPose();
        graphics.pose().scale(scale, scale, scale);
        graphics.drawString(font, str, (int)(x/scale), (int)(y/scale), color);
        graphics.pose().popPose();
    }

    public void drawScaledCenteredString(GuiGraphics graphics, String str, int x, int y, int color, float scale) {
        graphics.pose().pushPose();
        graphics.pose().scale(scale, scale, scale);
        float w = this.font.width(str) * scale;
        x = (int)((x - w / 2) / scale);
        graphics.drawString(font, str, x, (int)(y/scale), color);
        graphics.pose().popPose();
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if(KeysRegistry.SKYRIM_MENU_WEST.matches(keyCode, scanCode))
            currentDirection = Direction.WEST;
        else if(KeysRegistry.SKYRIM_MENU_EAST.matches(keyCode, scanCode))
            currentDirection = Direction.EAST;
        else if(KeysRegistry.SKYRIM_MENU_NORTH.matches(keyCode, scanCode))
            currentDirection = Direction.NORTH;
        else if(KeysRegistry.SKYRIM_MENU_SOUTH.matches(keyCode, scanCode))
            currentDirection = Direction.SOUTH;
        else if(KeysRegistry.SKYRIM_MENU_ENTER.matches(keyCode, scanCode)) {
            GLFW.glfwSetCursor(minecraft.getWindow().getWindow(), GLFW.glfwCreateStandardCursor(GLFW.GLFW_ARROW_CURSOR));
            if(currentDirection == Direction.NORTH) {
                minecraft.setScreen(null);
                minecraft.setScreen(new SkillScreen());
            } else if(currentDirection == Direction.SOUTH) {
                 minecraft.setScreen(null);
//                 minecraft.setScreen(new SkyrimLoadingScreen());
                 minecraft.setScreen(new MapScreen());
            } else if(currentDirection == Direction.WEST) {
                minecraft.setScreen(null);
                minecraft.player.displayClientMessage(Component.translatable("skyrimcraft.menu.option.unavailable"), false);
//                minecraft.setScreen(new QuestScreen());
//                minecraft.setScreen(new InventoryScreen(minecraft.player));
            } else if(currentDirection == Direction.EAST) {
                AtomicReference<List<Spell>> knownSpells;
                if(minecraft.player != null)
                    knownSpells = new AtomicReference<>(Services.PLATFORM.getCharacter(Minecraft.getInstance().player).getKnownSpells()); //Minecraft.getInstance().player.getData(PlayerAttachments.CHARACTER).getKnownSpells());
                else
                    knownSpells = new AtomicReference<>(new ArrayList<>());

                if(knownSpells.get().isEmpty()) {
                    minecraft.setScreen(null);
                    minecraft.player.displayClientMessage(Component.translatable("skyrimcraft.menu.option.magic.none"), false);
                } else {
                    minecraft.setScreen(null);
                    minecraft.setScreen(new MagicScreen(knownSpells.get()));
                }
            }  else
                minecraft.player.displayClientMessage(Component.translatable("skyrimcraft.menu.option.invalid"), false);
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private String calculateSkyrimTime(Level world) {
        StringBuilder builder = new StringBuilder();
        int dayNum = (int)(this.minecraft.level.getDayTime() / 24000L);
        int monthNum = (int)(this.minecraft.level.getDayTime() / (24000L * 31)) + 1;
        String day = getDayName((dayNum%7) + 1);
        String month = getMonthName(monthNum);
        String year = "4E 201";

        builder.append(day);
        builder.append(", ");
        builder.append(ordinal(dayNum));
        builder.append(" day of ");
        builder.append(month);
        builder.append(", ");
        builder.append(year);

        return builder.toString();
    }

    private String ordinal(int num) {
        String[] suffix = {"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th"};
        int m = num % 100;
        return String.valueOf(num) + suffix[(m > 3 && m < 21) ? 0 : (m % 10)];
    }

    private String getDayName(int day) {
        String name = "";
        switch(day) {
            case 1:
                name = "Morndas";
                break;
            case 2:
                name = "Tirdas";
                break;
            case 3:
                name = "Middas";
                break;
            case 4:
                name = "Turdas";
                break;
            case 5:
                name = "Fredas";
                break;
            case 6:
                name = "Loredas";
                break;
            case 7:
                name = "Sundas";
                break;
            default:
                name = "Invalid Day!";
                break;
        }
        return name;
    }

    private String getMonthName(int month) {
        String name = "";
        switch(month) {
            case 1:
                name = "Morning Star";
                break;
            case 2:
                name = "Sun's Dawn";
                break;
            case 3:
                name = "First Seed";
                break;
            case 4:
                name = "Rain's Hand";
                break;
            case 5:
                name = "Second Seed";
                break;
            case 6:
                name = "Midyear";
                break;
            case 7:
                name = "Sun's Height";
                break;
            case 8:
                name = "Last Seed";
                break;
            case 9:
                name = "Heart Fire";
                break;
            case 10:
                name = "Frostfall";
                break;
            case 11:
                name = "Sun's Dusk";
                break;
            case 12:
                name = "Evening Star";
                break;
            default:
                name = "Invalid Month!";
                break;
        }
        return name;
    }

    enum Direction {
        NONE(),
        NORTH(),
        SOUTH(),
        EAST(),
        WEST();

        Direction() {
        }
    }
}
