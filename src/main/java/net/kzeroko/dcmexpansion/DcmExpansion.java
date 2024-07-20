package net.kzeroko.dcmexpansion;

import com.mojang.logging.LogUtils;
import com.tacz.guns.api.event.common.EntityHurtByGunEvent;
import net.kzeroko.dcmexpansion.config.DcmExpansionConfig;
import net.kzeroko.dcmexpansion.registry.*;
import net.kzeroko.dcmexpansion.registry.modintegration.*;
import net.kzeroko.dcmexpansion.util.RefUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
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
import org.slf4j.LoggerFactory;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;

@Mod("dcmexpansion")
public class DcmExpansion {
    public static final String MOD_ID = "dcmexpansion";
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final Logger MOD_LOGGER = LoggerFactory.getLogger(MOD_ID);
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

    private static final ResourceLocation CURIOS_ICON_UNIT = new ResourceLocation("curios:slot/unit");
    private static final ResourceLocation CURIOS_ICON_BPVEST = new ResourceLocation("curios:slot/bpvest");

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

        if (ModList.get().isLoaded(RefUtil.cold_sweat_MODID)) {
            ColdSweatItems.REGISTER.register(eventBus);
        }

        if (ModList.get().isLoaded(RefUtil.tacz_MODID)) {
            TaczItems.REGISTER.register(eventBus);
        }

        modLoadingContext.registerConfig(ModConfig.Type.SERVER, DcmExpansionConfig.serverSpec);
        modLoadingContext.registerConfig(ModConfig.Type.CLIENT, DcmExpansionConfig.clientSpec);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        //
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // Init slot curios
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () ->
                new SlotTypeMessage.Builder("unit").priority(400).icon(CURIOS_ICON_UNIT).build());
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () ->
                new SlotTypeMessage.Builder("bpvest").priority(500).icon(CURIOS_ICON_BPVEST).build());
    }

    private void processIMC(final InterModProcessEvent event)
    {
        //
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        //
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents
    {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent)
        {
            //
        }
    }

    public void registerFirstAidItems(RegistryEvent.Register<Item> event) {
        DcmFirstaidItems.init(event.getRegistry());
    }

}
