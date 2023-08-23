package net.kzeroko.dcmexpansion.registry.modIntegration;

import net.kzeroko.dcmexpansion.DcmExpansion;
import net.kzeroko.dcmexpansion.item.pncr.CompressedBattery;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PneumaticCraftItems {

    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, DcmExpansion.MOD_ID);

    /** PneumaticCraft */
    public static final RegistryObject<Item> COMPRESSED_BATTERY = REGISTER.register( "compressed_battery", CompressedBattery::new);

}