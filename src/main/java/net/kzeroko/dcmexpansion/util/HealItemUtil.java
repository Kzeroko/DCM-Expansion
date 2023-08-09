package net.kzeroko.dcmexpansion.util;

import ichttt.mods.firstaid.FirstAidConfig;
import net.kzeroko.dcmexpansion.registry.DcmSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class HealItemUtil {

    /** Play sound base on item's usage*/
    public static void playMedKitSound(Player player, Level world) {
        int usage = player.getTicksUsingItem();

        if (usage == 0 /*&& usage < 29*/) {
            world.playSound(null, player.getX(), player.getY(), player.getZ(),
                    DcmSounds.MODERNMEDKIT_1.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
        }

        else if (usage == 25 /*&& usage < 59*/)  {
            world.playSound(null, player.getX(), player.getY(), player.getZ(),
                    DcmSounds.MODERNMEDKIT_2.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
        }

        else if (usage == 50 /*&& usage < 89*/) {
            world.playSound(null, player.getX(), player.getY(), player.getZ(),
                    DcmSounds.MODERNMEDKIT_3.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
        }

        else if (usage == 75 /*&& usage < 119*/) {
            world.playSound(null, player.getX(), player.getY(), player.getZ(),
                    DcmSounds.MODERNMEDKIT_4.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
        }
    }

    // I don't get it ;_;
    public static int getMaxHealth() {
        FirstAidConfig.Server server = FirstAidConfig.SERVER;
        return server.maxHealthHead.get()
                + server.maxHealthLeftArm.get()
                + server.maxHealthLeftLeg.get()
                + server.maxHealthLeftFoot.get()
                + server.maxHealthBody.get()
                + server.maxHealthRightArm.get()
                + server.maxHealthRightLeg.get()
                + server.maxHealthRightFoot.get();
    }
}
