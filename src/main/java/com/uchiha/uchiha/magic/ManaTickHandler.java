package com.uchiha.uchiha.magic;

import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import com.uchiha.uchiha.magic.PlayerManaData;

public class ManaTickHandler {
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        PlayerManaData.initMana(event.getEntity());
    }
}