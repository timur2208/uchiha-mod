package com.uchiha.event;

import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.minecraft.world.entity.player.Player;
import com.uchiha.UchihaModMain;
import com.uchiha.capability.UchihaCapabilities;

@Mod.EventBusSubscriber(modid = UchihaModMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DamageEventHandler {

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            var capability = UchihaCapabilities.getCapability(player);

            if (!capability.hasUchiha() || capability.getSharinganLevel() == 0) {
                return;
            }

            // Get evasion chance based on level
            double evasionChance = getEvasionChance(capability.getSharinganLevel());

            // Check if evasion triggers
            if (Math.random() < evasionChance) {
                event.setCanceled(true);
            }
        }
    }

    private static double getEvasionChance(int level) {
        return switch (level) {
            case 1 -> 0.05;
            case 2 -> 0.10;
            case 3 -> 0.15;
            case 4 -> 0.30;
            default -> 0.0;
        };
    }
}
