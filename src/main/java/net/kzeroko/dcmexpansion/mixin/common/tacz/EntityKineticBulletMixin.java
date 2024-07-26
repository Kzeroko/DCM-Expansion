package net.kzeroko.dcmexpansion.mixin.common.tacz;

import com.tacz.guns.entity.EntityKineticBullet;
import com.tacz.guns.util.TacHitResult;
import net.kzeroko.dcmexpansion.item.CurioItem;
import net.kzeroko.dcmexpansion.registry.modintegration.TaczItems;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityKineticBullet.class, priority = 1006, remap = false)
public abstract class EntityKineticBulletMixin extends Projectile {
    protected EntityKineticBulletMixin(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Shadow protected abstract void tacAttackEntity(DamageSource source, Entity entity, float damage);
    @Shadow public abstract float getDamage(Vec3 hitVec);

    @Inject(method = "onHitEntity", at = @At("HEAD"), cancellable = true)
    public void onBulletHitEntity(TacHitResult result, Vec3 startVec, Vec3 endVec, CallbackInfo ci) {
        if (result.getEntity() instanceof Player player && !player.level.isClientSide()) {
            dcmexpansion$handleVestDamage(player, result, ci);
        }
    }

    @Unique
    private void dcmexpansion$handleVestDamage(Player player, TacHitResult result, CallbackInfo ci) {
        @NotNull CurioItem[] vests = {TaczItems.BP_VEST_LIGHT.get(), TaczItems.BP_VEST_MEDIUM.get()};
        float[] damageFactors = {0.1F, 0.01F};

        for (int i = 0; i < vests.length; i++) {
            if (vests[i].isEquippedBy(player)) {
                vests[i].damageAllEquipped(player, 1);

                if (!result.isHeadshot()) {
                    float damage = this.getDamage(result.getLocation()) * damageFactors[i];
                    this.tacAttackEntity(DamageSource.thrown(this, this.getOwner()), result.getEntity(), damage);
                    ci.cancel();
                }

                break;
            }
        }
    }
}