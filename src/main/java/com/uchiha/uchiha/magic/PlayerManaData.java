package com.uchiha.uchiha.magic;

import net.minecraft.world.entity.player.Player;

public class PlayerManaData {
    public static final String MANA_KEY = "Uchiha_Mana";
    public static final String MAX_MANA_KEY = "Uchiha_MaxMana";
    public static final float DEFAULT_MAX_MANA = 200f;
    public static final float DEFAULT_CURRENT_MANA = 0f;

    public static float getCurrentMana(Player player) {
        return player.getPersistentData().getFloat(MANA_KEY);
    }

    public static void setCurrentMana(Player player, float mana) {
        float maxMana = getMaxMana(player);
        float newMana = Math.max(0, Math.min(mana, maxMana));
        player.getPersistentData().putFloat(MANA_KEY, newMana);
    }

    public static float getMaxMana(Player player) {
        float max = player.getPersistentData().getFloat(MAX_MANA_KEY);
        if (max == 0) {
            max = DEFAULT_MAX_MANA;
            player.getPersistentData().putFloat(MAX_MANA_KEY, max);
        }
        return max;
    }

    public static void initializePlayer(Player player) {
        if (player.getPersistentData().getFloat(MANA_KEY) == 0 &&
                player.getPersistentData().getFloat(MAX_MANA_KEY) == 0) {
            player.getPersistentData().putFloat(MANA_KEY, DEFAULT_CURRENT_MANA);
            player.getPersistentData().putFloat(MAX_MANA_KEY, DEFAULT_MAX_MANA);
        }
    }
}
