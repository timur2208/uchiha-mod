package com.uchiha.event;

import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import com.uchiha.UchihaModMain;
import com.uchiha.capability.UchihaCapabilities;
import com.uchiha.config.UchihaConfig;

@Mod.EventBusSubscriber(modid = UchihaModMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerTickHandler {

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide) return;

        var capability = UchihaCapabilities.getCapability(player);

        if (!capability.hasUchiha() || capability.getSharinganLevel() == 0) {
            return;
        }

        // Mana regeneration
        capability.addMana((int) (UchihaConfig.MANA_REGEN_RATE.get() * 10));

        // Mana drain based on level
        int manaDrain = getManaDrain(capability.getSharinganLevel());
        capability.consumeMana(manaDrain);

        // Apply passive abilities
        applyPassiveAbilities(player, capability);
    }

    private static void applyPassiveAbilities(Player player, com.uchiha.capability.UchihaCapability capability) {
        int level = capability.getSharinganLevel();

        if (level >= 1) {
            // Speed I effect
            player.addEffect(new MobEffectInstance(
                MobEffects.MOVEMENT_SPEED,
                20,
                0,
                false,
                false
            ));

            // Entity detection would be handled client-side
        }

        if (level >= 4) {
            // Speed II effect for Mangekyo
            player.addEffect(new MobEffectInstance(
                MobEffects.MOVEMENT_SPEED,
                20,
                1,
                false,
                false
            ));
        }
    }

    private static int getManaDrain(int level) {
        return switch (level) {
            case 1 -> UchihaConfig.LEVEL_1_MANA_DRAIN.get();
            case 2 -> UchihaConfig.LEVEL_2_MANA_DRAIN.get();
            case 3 -> UchihaConfig.LEVEL_3_MANA_DRAIN.get();
            case 4 -> UchihaConfig.LEVEL_4_MANA_DRAIN.get();
            default -> 0;
        };
    }
}
