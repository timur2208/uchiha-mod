package com.uchiha.util;

import net.minecraft.world.entity.player.Player;
import com.uchiha.capability.UchihaCapabilities;

public class ManaManager {
    
    public static void addMana(Player player, int amount) {
        var capability = UchihaCapabilities.getCapability(player);
        capability.addMana(amount);
    }

    public static void consumeMana(Player player, int amount) {
        var capability = UchihaCapabilities.getCapability(player);
        capability.consumeMana(amount);
    }

    public static int getMana(Player player) {
        var capability = UchihaCapabilities.getCapability(player);
        return capability.getMana();
    }

    public static int getMaxMana(Player player) {
        var capability = UchihaCapabilities.getCapability(player);
        return capability.getMaxMana();
    }

    public static boolean hasMana(Player player, int amount) {
        return getMana(player) >= amount;
    }

    public static float getManaPercentage(Player player) {
        int mana = getMana(player);
        int maxMana = getMaxMana(player);
        return maxMana > 0 ? (float) mana / maxMana : 0;
    }
}
