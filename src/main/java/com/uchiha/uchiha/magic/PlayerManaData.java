package com.uchiha.uchiha.magic;

import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;
import com.uchiha.uchiha.network.ManaUpdatePacket;

public class PlayerManaData {
    public static final String MANA_KEY = "Uchiha_Mana";
    public static final String MAX_MANA_KEY = "Uchiha_MaxMana";

    public static float getMana(Player player) {
        return player.getPersistentData().getFloat(MANA_KEY);
    }

    public static void setMana(Player player, float value) {
        float max = getMaxMana(player);
        float clamped = Math.max(0, Math.min(value, max));
        player.getPersistentData().putFloat(MANA_KEY, clamped);

        // СИНХРОНИЗИРУЕМ С КЛИЕНТОМ!
        if (!player.level().isClientSide) {
            PacketDistributor.sendToPlayer(new ManaUpdatePacket(clamped), (net.minecraft.server.level.ServerPlayer) player);
        }
    }

    public static float getMaxMana(Player player) {
        float max = player.getPersistentData().getFloat(MAX_MANA_KEY);
        return max > 0 ? max : 200f;
    }

    public static void initMana(Player player) {
        if (getMana(player) == 0) {
            setMana(player, 0);
            player.getPersistentData().putFloat(MAX_MANA_KEY, 200f);
        }
    }
}
