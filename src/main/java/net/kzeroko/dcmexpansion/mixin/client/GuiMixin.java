package net.kzeroko.dcmexpansion.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Gui;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
@Mixin(value = Gui.class, priority = 9999)
public class GuiMixin {
    @Shadow @Nullable protected Component overlayMessageString;

    @Inject(method = "renderPlayerHealth", at = @At("HEAD"), cancellable = true)
    private void onRenderPlayerHealth(PoseStack pPoseStack, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "renderVehicleHealth", at = @At("HEAD"), cancellable = true)
    private void onRenderVehicleHealth(PoseStack poseStack, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "renderJumpMeter", at = @At("HEAD"), cancellable = true)
    private void onRenderJumpMeter(PoseStack pPoseStack, int pX, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "renderExperienceBar", at = @At("HEAD"), cancellable = true)
    private void onRenderExperienceBar(PoseStack pPoseStack, int pX, CallbackInfo ci) {
        ci.cancel();
    }

    @ModifyConstant(
            method = "setOverlayMessage",
            constant = @Constant(intValue = 60)
    )
    private int modifyOverlayMessageTime(int constant) {
        if (this.overlayMessageString != null && this.overlayMessageString instanceof TranslatableComponent translatable
                && translatable.getKey().startsWith("message.dcmexpansion.weather")) {
            return 160;
        }
        return constant;
    }

}