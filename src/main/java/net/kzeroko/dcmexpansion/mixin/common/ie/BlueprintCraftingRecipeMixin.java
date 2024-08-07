package net.kzeroko.dcmexpansion.mixin.common.ie;

import blusunrize.immersiveengineering.api.crafting.BlueprintCraftingRecipe;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Mixin(value = BlueprintCraftingRecipe.class, remap = false)
public class BlueprintCraftingRecipeMixin {

    @Shadow @Final public static Set<String> recipeCategories;

    @Inject(
            method = "registerDefaultCategories",
            at = @At(value = "TAIL")
    )
    private static void onRegisterDefaults(CallbackInfo ci) {
        recipeCategories.add("tech_comp");
        recipeCategories.add("advanced_tech_comp");
        recipeCategories.add("chemical");
        recipeCategories.add("advanced_chemical");
        recipeCategories.add("advanced_ammunition");
        recipeCategories.add("advanced_armor");
        recipeCategories.add("tech_melee");
        recipeCategories.add("backpack_chip");
    }

}
