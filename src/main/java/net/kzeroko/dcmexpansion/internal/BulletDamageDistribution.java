package net.kzeroko.dcmexpansion.internal;

import ichttt.mods.firstaid.api.IDamageDistribution;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class BulletDamageDistribution implements IDamageDistribution {
    @Override
    public float distributeDamage(float damage, @NotNull Player player, @NotNull DamageSource damageSource, boolean addStat) {
        // WIP to do correct damage for bullets
        return damage;
    }
}
