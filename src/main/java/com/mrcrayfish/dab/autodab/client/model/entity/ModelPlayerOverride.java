package com.mrcrayfish.dab.autodab.client.model.entity;

import api.player.model.ModelPlayerAPI;
import api.player.model.ModelPlayerBase;
import club.sk1er.mods.autodab.Sk1erMod;
import com.mrcrayfish.dab.autodab.event.InputEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import pw._2pi.autogg.autodab.gg.AutoGG;

public class ModelPlayerOverride extends ModelPlayerBase {
    private static float thirdPersonPartialTicks;
    private boolean applied = false;
    private boolean failed = false;

    public ModelPlayerOverride(final ModelPlayerAPI modelPlayerAPI) {
        super(modelPlayerAPI);
    }

    public void afterSetLivingAnimations(final EntityLivingBase arg0, final float arg1, final float arg2, final float partialTicks) {
        ModelPlayerOverride.thirdPersonPartialTicks = partialTicks;
    }

    public void afterSetRotationAngles(final float nameFloat1, final float nameFloat2, final float nameFloat3, final float nameFloat4, final float nameFloat5, final float nameFloat6, final Entity nameEntity) {
        final boolean isOurPlayer = nameEntity.getEntityId() == Minecraft.getMinecraft().thePlayer.getEntityId();
        if (isOurPlayer && InputEvent.prevDabbingHeld > 0) {
            if (!applied && !failed && AutoGG.getInstance().isChroma() && InputEvent.dabbing) {
                 try {
                    EntityRenderer entityRenderer = Minecraft.getMinecraft().entityRenderer;
                    if (!entityRenderer.isShaderActive()) {
                        try {
                            Method loadShader = ReflectionHelper.findMethod(EntityRenderer.class, entityRenderer,
                                    new String[]{"func_175069_a", "loadShader"},ResourceLocation.class);
                            loadShader.invoke(entityRenderer, new ResourceLocation("shaders/post/wobble2.json"));
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                            failed = true;
                            Sk1erMod.getInstance().sendMessage("Your forge version is not compatible with the Chroma Dab setting. Please contact Sk1er (@Sk1er_ on Twitter). (1)");

                        }
                    }

                } catch (Throwable ignored) {
                    ignored.printStackTrace();
                    failed = true;
                    Sk1erMod.getInstance().sendMessage("Your forge version is not compatible with the Chroma Dab setting. Please contact Sk1er (@Sk1er_ on Twitter). (2)");
                }
                applied = true;
            }
            float heldPercent = (InputEvent.prevDabbingHeld + (InputEvent.dabbingHeld - InputEvent.prevDabbingHeld) * ModelPlayerOverride.thirdPersonPartialTicks) / 8.0f;
            this.modelPlayer.bipedRightArm.rotateAngleX = (float) Math.toRadians(-90.0f * heldPercent);
            this.modelPlayer.bipedRightArm.rotateAngleY = (float) Math.toRadians(-35.0f * heldPercent);
            this.modelPlayer.bipedRightArmwear.rotateAngleX = (float) Math.toRadians(-90.0f * heldPercent);
            this.modelPlayer.bipedRightArmwear.rotateAngleY = (float) Math.toRadians(-35.0f * heldPercent);
            this.modelPlayer.bipedLeftArm.rotateAngleX = (float) Math.toRadians(15.0f * heldPercent);
            this.modelPlayer.bipedLeftArm.rotateAngleY = (float) Math.toRadians(15.0f * heldPercent);
            this.modelPlayer.bipedLeftArm.rotateAngleZ = (float) Math.toRadians(-110.0f * heldPercent);
            this.modelPlayer.bipedLeftArmwear.rotateAngleX = (float) Math.toRadians(15.0f * heldPercent);
            this.modelPlayer.bipedLeftArmwear.rotateAngleY = (float) Math.toRadians(15.0f * heldPercent);
            this.modelPlayer.bipedLeftArmwear.rotateAngleZ = (float) Math.toRadians(-110.0f * heldPercent);
            final float rotationX = nameEntity.rotationPitch;
            this.modelPlayer.bipedHead.rotateAngleX = (float) Math.toRadians(-rotationX * heldPercent) + (float) Math.toRadians(45.0f * heldPercent + rotationX);
            final float rotationY = ((EntityPlayer) nameEntity).renderYawOffset - nameEntity.rotationYaw;
            this.modelPlayer.bipedHead.rotateAngleY = (float) Math.toRadians(rotationY * heldPercent) + (float) Math.toRadians(35.0f * heldPercent - rotationY);
            this.modelPlayer.bipedHeadwear.rotateAngleX = (float) Math.toRadians(45.0f * heldPercent);
            this.modelPlayer.bipedHeadwear.rotateAngleY = (float) Math.toRadians(35.0f * heldPercent);
            if (isOurPlayer && Minecraft.getMinecraft().gameSettings.thirdPersonView == 0) {
                heldPercent = (InputEvent.prevDabbingHeld + (InputEvent.dabbingHeld - InputEvent.prevDabbingHeld) * InputEvent.firstPersonPartialTicks) / 8.0f;
                GlStateManager.rotate(-50.0f * heldPercent, 1.0f, 0.0f, 0.0f);
                GlStateManager.rotate(30.0f * heldPercent, 0.0f, 1.0f, 0.0f);
                GlStateManager.rotate(-30.0f * heldPercent, 0.0f, 0.0f, 1.0f);
                GlStateManager.translate(-0.3 * heldPercent, -0.2 * heldPercent, -0.5 * heldPercent);
            }
        } else if (isOurPlayer || !InputEvent.dabbing) {
            applied = false;
            try {
                Minecraft.getMinecraft().entityRenderer.stopUseShader();
            } catch (Exception ignored) {

            }
        }
    }
}
