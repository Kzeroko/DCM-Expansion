package net.kzeroko.dcmexpansion.mixin.common;

import net.kzeroko.dcmexpansion.registry.DcmEffects;
import net.minecraft.world.entity.LivingEntity;
import net.smileycorp.hordes.common.infection.HordesInfection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class InfectionMixin {
    @Inject(at = @At("HEAD"), method = "tick")
    private void onTick(CallbackInfo info) {
        LivingEntity entity = (LivingEntity) (Object) this;

        if (entity.hasEffect(HordesInfection.INFECTED.get()) && entity.hasEffect(DcmEffects.ANTI_INFECTION.get())){
            entity.removeEffect(HordesInfection.INFECTED.get());
        }
    }

}