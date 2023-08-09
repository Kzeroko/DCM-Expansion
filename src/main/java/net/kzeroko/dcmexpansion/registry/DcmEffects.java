package net.kzeroko.dcmexpansion.registry;

import net.kzeroko.dcmexpansion.DcmExpansion;
import net.kzeroko.dcmexpansion.effect.AntiInfectionEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DcmEffects {
    public static final DeferredRegister<MobEffect> REGISTER = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, DcmExpansion.MOD_ID);
    public static RegistryObject<MobEffect> ANTI_INFECTION = REGISTER.register("anti_infection", AntiInfectionEffect::new);

}