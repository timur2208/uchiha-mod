package com.uchiha.uchiha.magic;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

@Mod.EventBusSubscriber(modid = "uchiha")
public class ManaTickHandler {

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        PlayerManaData.initializePlayer(event.getEntity());
    }

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Pre event) {
        if (event.getServer() != null) {
            event.getServer().getPlayerList().getPlayers().forEach(player -> {
                PlayerManaData.regenerateMana(player);
            });
        }
    }
}
