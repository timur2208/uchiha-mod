package com.uchiha.uchiha.mana;

public class CustomMana {
    public static int maxMana = 100;
    public static int currentMana = 100;

    public static void spendMana(int amount) {
        currentMana = Math.max(0, currentMana - amount);
    }

    public static void regenMana(int amount) {
        currentMana = Math.min(maxMana, currentMana + amount);
    }

    public static void setMana(int amount) {
        currentMana = Math.max(0, Math.min(maxMana, amount));
    }
}
