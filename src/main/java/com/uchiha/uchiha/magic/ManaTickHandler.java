package com.uchiha.uchiha.magic;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

public class ManaTickHandler {

    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        PlayerManaData.initializePlayer(event.getEntity());
    }

    public static void onServerTick(ServerTickEvent.Pre event) {
        MinecraftServer server = event.getServer();
        if (server != null) {
            for (Player player : server.getPlayerList().getPlayers()) {
                PlayerManaData.regenerateMana(player);
            }
        }
    }
}