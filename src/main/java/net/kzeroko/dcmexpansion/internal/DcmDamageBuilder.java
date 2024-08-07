package net.kzeroko.dcmexpansion.internal;

import ichttt.mods.firstaid.api.distribution.DamageDistributionBuilderFactory;
import ichttt.mods.firstaid.api.enums.EnumPlayerPart;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;

import java.util.Objects;

public class DcmDamageBuilder {
    public static void register() {
        DamageDistributionBuilderFactory builderFactory = Objects.requireNonNull(DamageDistributionBuilderFactory.getInstance());
        builderFactory.newEqualBuilder().registerStatic(DcmDamageSources.HAZARD_GAS);
        //builderFactory.newCustomBuilder(new BulletDamageDistribution()).registerStatic();
        builderFactory.newStandardBuilder()
                .addDistributionLayer(EquipmentSlot.CHEST, EnumPlayerPart.BODY)
                .addDistributionLayer(EquipmentSlot.HEAD, EnumPlayerPart.HEAD)
                .ignoreOrder()
                .disableNeighbourRestDistribution()
                .registerStatic(new DamageSource("radiation").bypassArmor());
    }
}
