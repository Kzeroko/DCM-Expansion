package net.kzeroko.dcmexpansion.item.weather;

import mekanism.api.Action;
import mekanism.api.AutomationType;
import mekanism.api.energy.IEnergyContainer;
import mekanism.api.math.FloatingLong;
import mekanism.common.item.ItemEnergized;
import mekanism.common.util.StorageUtils;
import net.kzeroko.dcmexpansion.DcmExpansion;
import net.kzeroko.dcmexpansion.registry.DcmSounds;
import net.kzeroko.dcmexpansion.util.CooldownUtil;
import net.kzeroko.dcmexpansion.util.EnergyUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import weather2.ClientTickHandler;
import weather2.ServerTickHandler;
import weather2.weathersystem.WeatherManager;
import weather2.weathersystem.storm.StormObject;

import java.util.ArrayList;
import java.util.List;

public class WeatherTestItem extends ItemEnergized {
    private final int cooldowns;
    private final int scanDistance;
    private final static int energyCostUsage = (int) EnergyUtil.feToJoules(100);

    public WeatherTestItem(double chargeRate, double capacity, int cooldowns, int scanDistance) {
        super(()-> FloatingLong.createConst(chargeRate), ()-> FloatingLong.createConst(capacity),
                (new Properties()).tab(DcmExpansion.INTEGRATION_GROUP).stacksTo(1));
        this.cooldowns = cooldowns;
        this.scanDistance = scanDistance;
    }


    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack heldStack = player.getItemInHand(hand);

        IEnergyContainer energyContainer = StorageUtils.getEnergyContainer(heldStack, 0);
        FloatingLong energy = energyContainer == null ? FloatingLong.ZERO : energyContainer.getEnergy();
        FloatingLong energyCost = FloatingLong.create(energyCostUsage);

        if (!level.isClientSide() && energyContainer != null && !energy.isZero() && !energy.smallerThan(energyCost)) {
            Vec3 playerPos = player.position();

            StormObject stormAny;
            WeatherManager weatherManager = getWeatherManagerFor(level);
            if (player.isCrouching()) {
                stormAny = weatherManager.getClosestStorm(playerPos, scanDistance, 4);
            } else {
                stormAny = weatherManager.getClosestStormAny(playerPos, scanDistance);
            }

            String message;

            if (stormAny != null) {
                Vec3 stormPos = stormAny.posGround;
                double distance = playerPos.distanceTo(stormPos);

                List<String> elements = new ArrayList<>();

                if (stormAny.isCloudlessStorm()) elements.add(I18n.get("message.dcmexpansion.weather.cloudless_storm"));
                if (stormAny.isRealStorm()) elements.add(I18n.get("message.dcmexpansion.weather.real_storm"));
                if (stormAny.isTornadoFormingOrGreater()) elements.add(I18n.get("message.dcmexpansion.weather.tornado_forming"));
                if (stormAny.isCycloneFormingOrGreater()) elements.add(I18n.get("message.dcmexpansion.weather.cyclone_forming"));
                if (stormAny.isSpinning()) elements.add(I18n.get("message.dcmexpansion.weather.spinning"));
                if (stormAny.isTropicalCyclone()) elements.add(I18n.get("message.dcmexpansion.weather.tropical_cyclone"));
                if (stormAny.isHurricane()) elements.add(I18n.get("message.dcmexpansion.weather.hurricane"));
                if (stormAny.isSharknado()) elements.add(I18n.get("message.dcmexpansion.weather.sharknado"));

                double dx = stormPos.x - playerPos.x;
                double dz = stormPos.z - playerPos.z;
                String directionS = getCardinalDirection(dx, dz);

                message = I18n.get("message.dcmexpansion.weather.distance_storm",
                        String.format("%.2f", distance) + "M",
                        I18n.get("message.dcmexpansion.weather.direction." + directionS)
                );

                if (!elements.isEmpty()) {
                    message += " | " + String.join(" | ", elements);
                }

            }
            else {
                message = I18n.get("message.dcmexpansion.weather.no_storm");
            }

            player.playSound(DcmSounds.WEATHER_TESTER.get(), 0.75F, 1.0F);
            player.displayClientMessage(new TextComponent(message).withStyle(ChatFormatting.GREEN), false);

            energyContainer.extract(energyCost, Action.EXECUTE, AutomationType.MANUAL);
            player.getCooldowns().addCooldown(this, 20 * cooldowns);

            return InteractionResultHolder.success(heldStack);
        } else {
            return InteractionResultHolder.fail(heldStack);
        }

    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> text, @NotNull TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, text, pIsAdvanced);

        if (pLevel != null && pLevel.isClientSide) {
            LocalPlayer clientPlayer = Minecraft.getInstance().player;
            if (clientPlayer != null) {
                Player player = pLevel.getPlayerByUUID(clientPlayer.getUUID());
                if (player != null) {
                    int cooldownSeconds = CooldownUtil.getCooldownSeconds(player, this);
                    boolean isReady = cooldownSeconds == 0;
                    text.add(new TranslatableComponent("tooltip.dcmexpansion.weather_tester.cooldown")
                            .append(new TextComponent(isReady ? ("READY") : (cooldownSeconds + "S"))
                                    .withStyle(isReady ? ChatFormatting.GREEN : ChatFormatting.AQUA)));
                }
            }
        }

        text.add(new TranslatableComponent("tooltip.dcmexpansion.weather_tester.range")
                .append(new TextComponent(String.valueOf(scanDistance)).withStyle(ChatFormatting.YELLOW)));
        text.add(TextComponent.EMPTY);
        text.add(new TranslatableComponent("tooltip.dcmexpansion.weather_tester.usage1"));
        text.add(new TranslatableComponent("tooltip.dcmexpansion.weather_tester.usage2"));


    }

    @Override
    public boolean isEnchantable(@NotNull ItemStack pStack) {
        return false;
    }

    public static WeatherManager getWeatherManagerFor(Level world) {
        if (world.isClientSide) {
            return getWeatherManagerClient();
        } else {
            return ServerTickHandler.getWeatherManagerFor((world.dimension()));
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static WeatherManager getWeatherManagerClient() {
        return ClientTickHandler.weatherManager;
    }

    private String getCardinalDirection(double dx, double dz) {
        if (Math.abs(dx) > Math.abs(dz)) {
            return dx > 0 ? "east" : "west";
        } else {
            return dz > 0 ? "south" : "north";
        }
    }

}