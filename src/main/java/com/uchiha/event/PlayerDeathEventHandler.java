package com.uchiha.event;

import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.minecraft.world.entity.player.Player;
import com.uchiha.UchihaModMain;
import com.uchiha.capability.UchihaCapabilities;

@Mod.EventBusSubscriber(modid = UchihaModMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerDeathEventHandler {

    @SubscribeEvent
    public static void onPlayerDeath(PlayerEvent.Clone event) {
        Player originalPlayer = event.getOriginal();
        Player newPlayer = event.getEntity();

        // Copy Uchiha capability data on death
        if (originalPlayer.level().isClientSide) return;

        var oldCapability = UchihaCapabilities.getCapability(originalPlayer);
        var newCapability = UchihaCapabilities.getCapability(newPlayer);

        // Reset mana but keep Uchiha status and Sharingan level
        newCapability.setUchiha(oldCapability.hasUchiha());
        newCapability.setSharinganLevel(oldCapability.getSharinganLevel());
        newCapability.resetMana();
        newCapability.resetBlindnessCounter();
    }
}
