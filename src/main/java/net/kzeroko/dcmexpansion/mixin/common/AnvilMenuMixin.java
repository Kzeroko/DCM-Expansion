package net.kzeroko.dcmexpansion.mixin.common;

import net.kzeroko.dcmexpansion.internal.DcmTags;
import net.kzeroko.dcmexpansion.item.RepairKitItem;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilMenu.class)
public abstract class AnvilMenuMixin extends ItemCombinerMenu {
    @Shadow @Final private DataSlot cost;

    public AnvilMenuMixin(@Nullable MenuType<?> pType, int pContainerId, Inventory pPlayerInventory, ContainerLevelAccess pAccess) {
        super(pType, pContainerId, pPlayerInventory, pAccess);
    }

    @Inject(method = "createResult", at = @At(value = "RETURN"))
    private void updateAnvilResult(CallbackInfo ci) {
        ItemStack leftStack = this.inputSlots.getItem(0).copy();
        ItemStack rightStack = this.inputSlots.getItem(1).copy();

        if (leftStack.isDamageableItem() && leftStack.isDamaged() && rightStack.getItem() instanceof RepairKitItem repairKit) {

            if (leftStack.is(DcmTags.REPAIRKIT_BLACKLIST)) return;

            float fixAmount = repairKit.getFixAmount();
            int damage = leftStack.getDamageValue();
            int durability = leftStack.getMaxDamage();

            int repairAmount = (int) (durability * fixAmount);

            int newDamage = damage - repairAmount;
            if (newDamage < 0) newDamage = 0;

            leftStack.setDamageValue(newDamage);

            int costV = (int) Mth.clamp(fixAmount * 5F, 1F, 5F);

            this.cost.set(costV);
            this.resultSlots.setItem(0, leftStack);
            this.broadcastChanges();
        }
    }
}
