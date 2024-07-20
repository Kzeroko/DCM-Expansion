package net.kzeroko.dcmexpansion.client;

import net.kzeroko.dcmexpansion.DcmExpansion;
import net.kzeroko.dcmexpansion.client.curios.CurioModel;
import net.kzeroko.dcmexpansion.client.curios.CurioRenderer;
import net.kzeroko.dcmexpansion.client.curios.ICurioRenderable;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

@Mod.EventBusSubscriber(modid = DcmExpansion.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class DcmExpansionClient {
    @SubscribeEvent
    public static void setupClient(final FMLClientSetupEvent event) {

        for (Item item : ForgeRegistries.ITEMS.getValues()) {
            if (!(item instanceof ICurioRenderable)) continue;

            CuriosRendererRegistry.register(item, CurioRenderer::new);
            DcmExpansion.MOD_LOGGER.info("Registered curios renderer for {}", ForgeRegistries.ITEMS.getKey(item));
        }
    }

    @SubscribeEvent
    public static void registerLayers(final EntityRenderersEvent.RegisterLayerDefinitions event) {
        for (Item item : ForgeRegistries.ITEMS.getValues()) {
            if (!(item instanceof ICurioRenderable renderable)) continue;

            event.registerLayerDefinition(CurioModel.getLayerLocation(item), renderable::constructLayerDefinition);
        }
    }
}
