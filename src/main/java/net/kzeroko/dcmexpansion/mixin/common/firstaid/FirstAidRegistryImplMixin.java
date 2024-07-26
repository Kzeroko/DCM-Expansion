package net.kzeroko.dcmexpansion.mixin.common.firstaid;

import ichttt.mods.firstaid.common.apiimpl.FirstAidRegistryImpl;
import net.kzeroko.dcmexpansion.registry.DcmEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = FirstAidRegistryImpl.class, priority = 1001, remap = false)
public class FirstAidRegistryImplMixin {

    @Inject(method = "getPartHealingTime", at = @At("RETURN"), cancellable = true)
    private void modifyFirstAidPartHealingTime(ItemStack stack, CallbackInfoReturnable<Integer> cir) {

        Integer originalTime = cir.getReturnValue();

        if (originalTime != null) {
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer player = minecraft.player;

            if (player != null && player.hasEffect(DcmEffects.FAST_HEAL.get())) {

                long modifiedTime = (long) originalTime / 2L;
                cir.setReturnValue((int) Math.max(0L, modifiedTime));
                cir.cancel();
            }
        }

    }
}
