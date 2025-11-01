package com.uchiha.uchiha.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class ManaCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("uchihamana")
                .then(Commands.literal("add")
                        .then(Commands.argument("amount", FloatArgumentType.floatArg())
                                .executes(context -> {
                                    float amount = FloatArgumentType.getFloat(context, "amount");
                                    Player player = context.getSource().getPlayerOrException();

                                    float current = player.getPersistentData().getFloat("Uchiha_Mana");
                                    float max = player.getPersistentData().getFloat("Uchiha_MaxMana");
                                    if (max == 0) max = 200f;

                                    float newMana = Math.min(current + amount, max);
                                    player.getPersistentData().putFloat("Uchiha_Mana", newMana);

                                    player.displayClientMessage(
                                            Component.literal("§b✦ Мана добавлена: +§a" + String.format("%.0f", amount) +
                                                    "§b | Текущая: §a" + String.format("%.0f", newMana) + "§b/§a" + String.format("%.0f", max)),
                                            false
                                    );
                                    return 1;
                                })
                        )
                )
                .then(Commands.literal("remove")
                        .then(Commands.argument("amount", FloatArgumentType.floatArg())
                                .executes(context -> {
                                    float amount = FloatArgumentType.getFloat(context, "amount");
                                    Player player = context.getSource().getPlayerOrException();

                                    float current = player.getPersistentData().getFloat("Uchiha_Mana");

                                    if (current >= amount) {
                                        float newMana = current - amount;
                                        player.getPersistentData().putFloat("Uchiha_Mana", newMana);

                                        player.displayClientMessage(
                                                Component.literal("§b✦ Мана потрачена: §c-" + String.format("%.0f", amount) +
                                                        "§b | Осталось: §a" + String.format("%.0f", newMana)),
                                                false
                                        );
                                    } else {
                                        player.displayClientMessage(
                                                Component.literal("§c✗ Недостаточно маны! (Есть: " + String.format("%.0f", current) + ")"),
                                                false
                                        );
                                    }
                                    return 1;
                                })
                        )
                )
                .then(Commands.literal("set")
                        .then(Commands.argument("amount", FloatArgumentType.floatArg())
                                .executes(context -> {
                                    float amount = FloatArgumentType.getFloat(context, "amount");
                                    Player player = context.getSource().getPlayerOrException();
                                    float max = player.getPersistentData().getFloat("Uchiha_MaxMana");
                                    if (max == 0) max = 200f;

                                    float newMana = Math.min(Math.max(amount, 0), max);
                                    player.getPersistentData().putFloat("Uchiha_Mana", newMana);

                                    player.displayClientMessage(
                                            Component.literal("§b✦ Мана установлена на: §a" + String.format("%.0f", newMana)),
                                            false
                                    );
                                    return 1;
                                })
                        )
                )
                .then(Commands.literal("info")
                        .executes(context -> {
                            Player player = context.getSource().getPlayerOrException();
                            float current = player.getPersistentData().getFloat("Uchiha_Mana");
                            float max = player.getPersistentData().getFloat("Uchiha_MaxMana");
                            if (max == 0) max = 200f;
                            float percent = (current / max) * 100;

                            player.displayClientMessage(
                                    Component.literal("§b✦ Мана: §a" + String.format("%.0f", current) + "§b/§a" +
                                            String.format("%.0f", max) + " §b(§6" + String.format("%.0f", percent) + "%§b)"),
                                    false
                            );
                            return 1;
                        })
                )
        );
    }
}
