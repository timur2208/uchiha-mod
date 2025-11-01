package com.uchiha.uchiha.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import com.uchiha.uchiha.magic.PlayerManaData;

public class ManaCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("uchihamana")
                .then(Commands.literal("add")
                        .then(Commands.argument("amount", FloatArgumentType.floatArg())
                                .executes(context -> {
                                    float amount = FloatArgumentType.getFloat(context, "amount");
                                    CommandSourceStack source = context.getSource();
                                    Player player = source.getPlayerOrException();

                                    float current = PlayerManaData.getCurrentMana(player);
                                    float newMana = Math.min(current + amount, PlayerManaData.getMaxMana(player));
                                    PlayerManaData.setCurrentMana(player, newMana);

                                    // Синхронизируем данные с клиентом
                                    player.getPersistentData().putFloat("Uchiha_Mana", newMana);

                                    player.displayClientMessage(
                                            Component.literal("§b✦ Мана добавлена: +" + String.format("%.0f", amount) +
                                                    " | Текущая: " + String.format("%.0f", newMana)),
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
                                    CommandSourceStack source = context.getSource();
                                    Player player = source.getPlayerOrException();

                                    if (PlayerManaData.spendMana(player, amount)) {
                                        float current = PlayerManaData.getCurrentMana(player);

                                        // Синхронизируем данные с клиентом
                                        player.getPersistentData().putFloat("Uchiha_Mana", current);

                                        player.displayClientMessage(
                                                Component.literal("§b✦ Мана потрачена: -" + String.format("%.0f", amount) +
                                                        " | Осталось: " + String.format("%.0f", current)),
                                                false
                                        );
                                    } else {
                                        player.displayClientMessage(
                                                Component.literal("§c✗ Недостаточно маны!"),
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
                                    CommandSourceStack source = context.getSource();
                                    Player player = source.getPlayerOrException();

                                    PlayerManaData.setCurrentMana(player, amount);

                                    // Синхронизируем данные с клиентом
                                    player.getPersistentData().putFloat("Uchiha_Mana", amount);

                                    player.displayClientMessage(
                                            Component.literal("§b✦ Мана установлена на: " + String.format("%.0f", amount)),
                                            false
                                    );
                                    return 1;
                                })
                        )
                )
                .then(Commands.literal("info")
                        .executes(context -> {
                            CommandSourceStack source = context.getSource();
                            Player player = source.getPlayerOrException();
                            float current = PlayerManaData.getCurrentMana(player);
                            float max = PlayerManaData.getMaxMana(player);
                            float percent = (current / max) * 100;

                            player.displayClientMessage(
                                    Component.literal("§b✦ Мана: " + String.format("%.0f", current) + " / " +
                                            String.format("%.0f", max) + " (§6" + String.format("%.0f", percent) + "%§b)"),
                                    false
                            );
                            return 1;
                        })
                )
        );
    }
}
