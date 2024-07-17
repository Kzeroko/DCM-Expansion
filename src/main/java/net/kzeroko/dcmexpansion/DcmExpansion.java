package net.kzeroko.dcmexpansion;

import com.mojang.logging.LogUtils;
import net.kzeroko.dcmexpansion.config.DcmExpansionConfig;
import net.kzeroko.dcmexpansion.registry.*;
import net.kzeroko.dcmexpansion.registry.modintegration.*;
import net.kzeroko.dcmexpansion.util.RefUtil;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.stream.Collectors;

@Mod("dcmexpansion")
public class DcmExpansion {
    public static final String MOD_ID = "dcmexpansion";
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final CreativeModeTab INTEGRATION_GROUP = new CreativeModeTab(MOD_ID + "." + "modIntegrationItems") {
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(DcmIntegrationItems.SCREEN_COMPONENT.get());
        }
    };
    public static final CreativeModeTab HEALING_GROUP = new CreativeModeTab(MOD_ID + "." + "healingItems") {
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(DcmHealingItems.ADRENALINE.get());
        }
    };
    public static final CreativeModeTab FOODS_AND_DRINKS = new CreativeModeTab(MOD_ID + "." + "foodsAndDrinks") {
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(DcmMiscItems.WATERBOTTLE_FILLED.get());
        }
    };

    public DcmExpansion()
    {
        MinecraftForge.EVENT_BUS.register(this);

        final ModLoadingContext modLoadingContext = ModLoadingContext.get();
        final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        eventBus.addListener(this::setup);
        eventBus.addListener(this::enqueueIMC);
        eventBus.addListener(this::processIMC);

        eventBus.addGenericListener(Item.class, this::registerFirstAidItems);

        DcmSounds.REGISTER.register(eventBus);
        DcmHealingItems.REGISTER.register(eventBus);
        DcmMiscItems.REGISTER.register(eventBus);
        DcmIntegrationItems.REGISTER.register(eventBus);
        DcmEffects.REGISTER.register(eventBus);

        if (ModList.get().isLoaded(RefUtil.immersive_aircraft_MODID)) {
            ImmersiveAircraftItems.REGISTER.register(eventBus);
        }

        if (ModList.get().isLoaded(RefUtil.pneumaticcraft_MODID)) {
            PneumaticCraftItems.REGISTER.register(eventBus);
        }

        if (ModList.get().isLoaded(RefUtil.weather2_MODID) && ModList.get().isLoaded(RefUtil.mekanism_MODID)) {
            WeatherItems.REGISTER.register(eventBus);
        }

        modLoadingContext.registerConfig(ModConfig.Type.SERVER, DcmExpansionConfig.serverSpec);
        modLoadingContext.registerConfig(ModConfig.Type.CLIENT, DcmExpansionConfig.clientSpec);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        InterModComms.sendTo("dcmexpansion", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.messageSupplier().get()).
                collect(Collectors.toList()));
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        LOGGER.info("HELLO from server starting");
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents
    {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent)
        {
            LOGGER.info("HELLO from Register Block");
        }
    }

    public void registerFirstAidItems(RegistryEvent.Register<Item> event) {
        DcmFirstaidItems.init(event.getRegistry());
    }
}
