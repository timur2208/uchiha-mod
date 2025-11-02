package com.uchiha.uchiha.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientChatEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import com.uchiha.uchiha.magic.PlayerManaData;

@Mod.EventBusSubscriber(modid = "uchiha", value = Dist.CLIENT)
public class ClientChatHandler {

    @SubscribeEvent
    public static void onClientChat(ClientChatEvent event) {
        String message = event.getMessage();

        if (!message.startsWith("/uchihamana")) return;

        event.setCanceled(true);
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null) return;

        String[] args = message.split(" ");

        if (args.length < 2) {
            minecraft.player.displayClientMessage(Component.literal("§c/uchihamana <add/remove/set/info> <amount>"), true);
            return;
        }

        String command = args[1];

        try {
            switch (command) {
                case "add" -> {
                    if (args.length < 3) return;
                    float amount = Float.parseFloat(args[2]);
                    float current = PlayerManaData.getCurrentMana(minecraft.player);
                    PlayerManaData.setCurrentMana(minecraft.player, current + amount);
                    float newMana = PlayerManaData.getCurrentMana(minecraft.player);
                    minecraft.player.displayClientMessage(
                            Component.literal("§a✦ +§b" + String.format("%.0f", amount) + " §bMana → §a" + String.format("%.0f", newMana)),
                            false
                    );
                }
                case "remove" -> {
                    if (args.length < 3) return;
                    float amount = Float.parseFloat(args[2]);
                    float current = PlayerManaData.getCurrentMana(minecraft.player);
                    if (current >= amount) {
                        PlayerManaData.setCurrentMana(minecraft.player, current - amount);
                        float newMana = PlayerManaData.getCurrentMana(minecraft.player);
                        minecraft.player.displayClientMessage(
                                Component.literal("§c✦ -§b" + String.format("%.0f", amount) + " §bMana → §a" + String.format("%.0f", newMana)),
                                false
                        );
                    } else {
                        minecraft.player.displayClientMessage(
                                Component.literal("§c✗ Недостаточно маны! (Есть: " + String.format("%.0f", current) + ")"),
                                false
                        );
                    }
                }
                case "set" -> {
                    if (args.length < 3) return;
                    float amount = Float.parseFloat(args[2]);
                    PlayerManaData.setCurrentMana(minecraft.player, amount);
                    float newMana = PlayerManaData.getCurrentMana(minecraft.player);
                    minecraft.player.displayClientMessage(
                            Component.literal("§b✦ Мана = §a" + String.format("%.0f", newMana)),
                            false
                    );
                }
                case "info" -> {
                    float current = PlayerManaData.getCurrentMana(minecraft.player);
                    float max = PlayerManaData.getMaxMana(minecraft.player);
                    minecraft.player.displayClientMessage(
                            Component.literal("§b✦ Мана: §a" + String.format("%.0f", current) + "§8/§a" + String.format("%.0f", max)),
                            false
                    );
                }
            }
        } catch (Exception e) {
            minecraft.player.displayClientMessage(Component.literal("§c✗ Ошибка!"), false);
        }
    }
}
