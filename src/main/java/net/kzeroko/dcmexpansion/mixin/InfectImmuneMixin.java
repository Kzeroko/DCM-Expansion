package net.kzeroko.dcmexpansion.mixin;

import net.kzeroko.dcmexpansion.registry.DcmEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.smileycorp.hordes.common.infection.HordesInfection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class InfectImmuneMixin {
    @Inject(at = @At("HEAD"), method = "addEffect(Lnet/minecraft/world/effect/MobEffectInstance;Lnet/minecraft/world/entity/Entity;)Z", cancellable = true)
    private void immuneInfection(MobEffectInstance effectInstance, Entity entity, CallbackInfoReturnable<Boolean> cir) {

        if(entity instanceof Player player) {
            if(effectInstance.getEffect() == HordesInfection.INFECTED.get() && player.hasEffect(DcmEffects.ANTI_INFECTION.get())) {
                cir.setReturnValue(false);
            }
        }
    }
}