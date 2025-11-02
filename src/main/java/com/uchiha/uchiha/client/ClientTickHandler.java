package com.uchiha.uchiha.client;

import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import com.uchiha.uchiha.magic.PlayerManaData;

public class ClientTickHandler {

    @SubscribeEvent
    public static void onTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.isPaused()) return;

        float mana = PlayerManaData.getMana(mc.player);
        float max = PlayerManaData.getMaxMana(mc.player);
        if (mana < max) {
            PlayerManaData.setMana(mc.player, mana + 0.25f);
        }
    }
}