package net.kzeroko.dcmexpansion.mixin.common.scpdtj;

import net.rian.scpdtj.procedures.MessageWhenJoinedProcedure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MessageWhenJoinedProcedure.class, remap = false)
public class MessageWhenJoinedProcedureMixin {
    @Inject(
            method = "execute(Lnet/minecraftforge/eventbus/api/Event;Lnet/minecraft/world/level/LevelAccessor;)V",
            at = @At(value = "HEAD"),
            cancellable = true)
    private static void cancelsJoinMessage(CallbackInfo ci) {
        ci.cancel();
    }

}
