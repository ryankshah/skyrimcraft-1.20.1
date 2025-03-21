package com.ryankshah.skyrimcraft.screen.sprite;


import com.mojang.blaze3d.vertex.PoseStack;
import com.ryankshah.skyrimcraft.screen.sprite.action.IAction;
import com.ryankshah.skyrimcraft.util.ClientUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Quaternionf;

import java.util.ArrayDeque;
import java.util.Queue;

@OnlyIn(Dist.CLIENT)
public class Sprite
{
    private Queue<IAction> actions = new ArrayDeque<>(4);
    public TextureInfo tex;
    public float posX , posY, oldPosX, oldPosY, speedX, speedY, rot, oldRot, rotSpeed, originX, originY, alpha = 1f, oldAlpha = 1f;

    public Sprite(TextureInfo tex)
    {
        this.tex = tex;
    }

    public Sprite position(float posX, float posY)
    {
        this.posX = this.oldPosX = posX;
        this.posY = this.oldPosY = posY;
        return this;
    }

    public Sprite rotation(float rot, float originX, float originY)
    {
        this.rot = this.oldRot = rot;
        this.originX = originX;
        this.originY = originY;
        return this;
    }

    public Sprite alpha(float alpha)
    {
        this.alpha = this.oldAlpha = alpha;
        return this;
    }

    public void execute(IAction... actions)
    {
        for(IAction action : actions)
            this.actions.offer(action);
    }

    public boolean isExecuting()
    {
        return !this.actions.isEmpty();
    }

    // FIXME new quat obj every frame? JFC
    public void draw(GuiGraphics guiGraphics, float partialTick, ResourceLocation location)
    {
        if(this.alpha <= 0f)
            return;
        PoseStack mtx = guiGraphics.pose();
        mtx.pushPose();
        mtx.translate(this.originX, this.originY, 0f);
        mtx.mulPose(new Quaternionf().rotateZ(-ClientUtil.lerp(this.oldRot, this.rot, partialTick)));
        mtx.translate(-this.originX, -this.originY, 0f);
        this.tex.draw(guiGraphics, ClientUtil.lerp(this.oldPosX, this.posX, partialTick), ClientUtil.lerp(this.oldPosY, this.posY, partialTick), ClientUtil.lerp(this.oldAlpha, this.alpha, partialTick), location);
        mtx.popPose();
    }

    public void update()
    {
        this.oldPosX = this.posX;
        this.oldPosY = this.posY;
        this.oldRot = this.rot;
        this.oldAlpha = this.alpha;
        this.posX += this.speedX;
        this.posY += this.speedY;
        this.rot += this.rotSpeed;
        IAction action = this.actions.peek();
        if(action == null)
            return;
        if(action.isFinished(this))
            this.actions.poll().finish(this);
        else
            action.update(this);
    }
}