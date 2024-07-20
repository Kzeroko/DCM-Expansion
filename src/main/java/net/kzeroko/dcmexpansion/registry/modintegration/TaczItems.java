package net.kzeroko.dcmexpansion.registry.modintegration;

import net.kzeroko.dcmexpansion.DcmExpansion;
import net.kzeroko.dcmexpansion.item.DcmCurioItem;
import net.kzeroko.dcmexpansion.item.tacz.BPVestLight;
import net.kzeroko.dcmexpansion.item.tacz.BPVestMedium;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TaczItems {

    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, DcmExpansion.MOD_ID);
    public static final RegistryObject<DcmCurioItem> BP_VEST_LIGHT =
            REGISTER.register("bp_vest_light", BPVestLight::new);
    public static final RegistryObject<DcmCurioItem> BP_VEST_MEDIUM =
            REGISTER.register("bp_vest_medium", BPVestMedium::new);
}