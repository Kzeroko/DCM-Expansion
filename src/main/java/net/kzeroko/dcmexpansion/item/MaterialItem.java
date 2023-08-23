package net.kzeroko.dcmexpansion.item;

import net.kzeroko.dcmexpansion.DcmExpansion;
import net.minecraft.world.item.Item;

public class MaterialItem extends Item {
    public MaterialItem() {
        super((new Properties()).tab(DcmExpansion.INTEGRATION_GROUP).stacksTo(64));
    }
}