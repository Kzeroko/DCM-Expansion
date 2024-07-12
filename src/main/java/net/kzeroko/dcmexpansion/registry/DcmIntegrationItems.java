package net.kzeroko.dcmexpansion.registry;

import net.kzeroko.dcmexpansion.DcmExpansion;
import net.kzeroko.dcmexpansion.item.MaterialItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DcmIntegrationItems {

    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, DcmExpansion.MOD_ID);
    public static final RegistryObject<Item> SCREEN_COMPONENT = REGISTER.register( "screen_component", MaterialItem::new);

}