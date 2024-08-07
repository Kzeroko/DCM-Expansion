package net.kzeroko.dcmexpansion.mixin.common.ie;

import blusunrize.immersiveengineering.api.wires.localhandlers.WireDamageHandler;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = WireDamageHandler.class, remap = false)
public class WireDamageHandlerMixin {
    /*@ModifyVariable(
            method = "onCollided",
            at = @At(value = "INVOKE_ASSIGN", target = "Lblusunrize/immersiveengineering/api/wires/utils/IElectricDamageSource;getDamage()F", ordinal = 0)
    )
    public float onWireCollidedDamage(final float v) {
        return Mth.clamp(v, 0.0F, 12.0F);
    }*/

    @Redirect(
            method = "onCollided",
            at = @At(value = "INVOKE", target = "Lblusunrize/immersiveengineering/api/ApiUtils;knockbackNoSource(Lnet/minecraft/world/entity/LivingEntity;DDD)V")
    )
    public void onWireCollidedKnockback(LivingEntity entity, double strength, double xRatio, double zRatio) {
        // Original code, we just cancels it since it is too overpowered
        // entity.hasImpulse = true;
        // Vec3 motionOld = entity.getDeltaMovement();
        // Vec3 toAdd = (new Vec3(xRatio, 0.0D, zRatio)).normalize().scale(strength);
        // entity.setDeltaMovement(
                // motionOld.x / 2.0D - toAdd.x,
                // entity.isOnGround() ? Math.min(0.4D, motionOld.y/ 2.0D + strength): motionOld.y,
                // motionOld.z / 2.0D - toAdd.z);
    }

}
