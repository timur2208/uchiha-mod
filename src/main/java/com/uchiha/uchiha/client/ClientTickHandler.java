package com.uchiha.uchiha.client;

import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import com.uchiha.uchiha.magic.PlayerManaData;

public class ClientTickHandler {

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player != null && minecraft.level != null && !minecraft.isPaused()) {
            float current = PlayerManaData.getCurrentMana(minecraft.player);
            float max = PlayerManaData.getMaxMana(minecraft.player);

            if (current < max) {
                PlayerManaData.setCurrentMana(minecraft.player, current + 0.25f);
            }
        }
    }
}
