package com.uchiha.uchiha.client;

import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;

public class ClientTickHandler {

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player != null && minecraft.level != null) {
            // Восстанавливаем ману напрямую в NBT клиента
            float current = minecraft.player.getPersistentData().getFloat("Uchiha_Mana");
            float max = minecraft.player.getPersistentData().getFloat("Uchiha_MaxMana");

            if (max == 0) {
                max = 200f;
                minecraft.player.getPersistentData().putFloat("Uchiha_MaxMana", max);
            }

            // Восстанавливаем ману: 0.25 в тик
            if (current < max) {
                current += 0.25f;
                if (current > max) current = max;
                minecraft.player.getPersistentData().putFloat("Uchiha_Mana", current);
            }
        }
    }
}
