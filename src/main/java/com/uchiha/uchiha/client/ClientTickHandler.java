package com.uchiha.uchiha.client;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.tick.TickEvent;
import com.uchiha.uchiha.magic.PlayerManaData;

@Mod.EventBusSubscriber(modid = "uchiha", value = Dist.CLIENT)
public class ClientTickHandler {

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.player != null && minecraft.level != null) {
                PlayerManaData.regenerateMana(minecraft.player);
            }
        }
    }
}