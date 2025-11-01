package com.uchiha.uchiha.magic;

import net.minecraft.world.entity.player.Player;

public class PlayerManaData {
    private static final String MANA_KEY = "Uchiha_Mana";
    private static final String MAX_MANA_KEY = "Uchiha_MaxMana";
    private static final float DEFAULT_MAX_MANA = 200f;
    private static final float DEFAULT_CURRENT_MANA = 100f;
    private static final float MANA_REGEN_PER_TICK = 0.5f;

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
        return max > 0 ? max : DEFAULT_MAX_MANA;
    }

    public static void setMaxMana(Player player, float maxMana) {
        player.getPersistentData().putFloat(MAX_MANA_KEY, Math.max(1, maxMana));
    }

    public static void addMana(Player player, float amount) {
        float current = getCurrentMana(player);
        setCurrentMana(player, current + amount);
    }

    public static boolean spendMana(Player player, float amount) {
        float current = getCurrentMana(player);
        if (current >= amount) {
            setCurrentMana(player, current - amount);
            return true;
        }
        return false;
    }

    public static void initializePlayer(Player player) {
        if (getCurrentMana(player) == 0 && getMaxMana(player) == 0) {
            setCurrentMana(player, DEFAULT_CURRENT_MANA);
            setMaxMana(player, DEFAULT_MAX_MANA);
        }
    }

    public static void regenerateMana(Player player) {
        if (!player.level().isClientSide) {
            float current = getCurrentMana(player);
            float max = getMaxMana(player);
            if (current < max) {
                setCurrentMana(player, current + MANA_REGEN_PER_TICK);
            }
        }
    }
}
