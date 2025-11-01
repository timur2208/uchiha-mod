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
        dispatcher.register(Commands.literal("mana")
                .then(Commands.literal("add")
                        .then(Commands.argument("amount", FloatArgumentType.floatArg())
                                .executes(context -> {
                                    float amount = FloatArgumentType.getFloat(context, "amount");
                                    Player player = context.getSource().getPlayerOrException();
                                    PlayerManaData.addMana(player, amount);
                                    float current = PlayerManaData.getCurrentMana(player);
                                    player.displayClientMessage(
                                            Component.literal("§bМана добавлена! Текущая: " + String.format("%.0f", current)),
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
                                    if (PlayerManaData.spendMana(player, amount)) {
                                        float current = PlayerManaData.getCurrentMana(player);
                                        player.displayClientMessage(
                                                Component.literal("§bМана потрачена! Осталось: " + String.format("%.0f", current)),
                                                false
                                        );
                                    } else {
                                        player.displayClientMessage(
                                                Component.literal("§cНедостаточно маны!"),
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
                                    PlayerManaData.setCurrentMana(player, amount);
                                    float current = PlayerManaData.getCurrentMana(player);
                                    player.displayClientMessage(
                                            Component.literal("§bМана установлена на: " + String.format("%.0f", current)),
                                            false
                                    );
                                    return 1;
                                })
                        )
                )
                .then(Commands.literal("info")
                        .executes(context -> {
                            Player player = context.getSource().getPlayerOrException();
                            float current = PlayerManaData.getCurrentMana(player);
                            float max = PlayerManaData.getMaxMana(player);
                            player.displayClientMessage(
                                    Component.literal("§bМана: " + String.format("%.0f", current) + " / " + String.format("%.0f", max)),
                                    false
                            );
                            return 1;
                        })
                )
        );
    }
}
