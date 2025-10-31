package com.uchiha.uchiha.magic;

public class MagicEvents {
    public static void onPlayerTick(MagicData data) {
        // Регенерация, можно делать раз в N тиков
        data.regenMana(1);
    }
}
