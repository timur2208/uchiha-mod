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
                                    Player player = context.getSource().getPlayerOrException();
                                    float current = PlayerManaData.getCurrentMana(player);
                                    PlayerManaData.setCurrentMana(player, current + amount);
                                    float newMana = PlayerManaData.getCurrentMana(player);
                                    player.displayClientMessage(Component.literal("§a+§b" + amount + " Mana → §a" + newMana), false);
                                    return 1;
                                })
                        )
                )
                .then(Commands.literal("remove")
                        .then(Commands.argument("amount", FloatArgumentType.floatArg())
                                .executes(context -> {
                                    float amount = FloatArgumentType.getFloat(context, "amount");
                                    Player player = context.getSource().getPlayerOrException();
                                    float current = PlayerManaData.getCurrentMana(player);
                                    if (current >= amount) {
                                        PlayerManaData.setCurrentMana(player, current - amount);
                                        float newMana = PlayerManaData.getCurrentMana(player);
                                        player.displayClientMessage(Component.literal("§c-§b" + amount + " Mana → §a" + newMana), false);
                                    } else {
                                        player.displayClientMessage(Component.literal("§cНет маны!"), false);
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
                                    float newMana = PlayerManaData.getCurrentMana(player);
                                    player.displayClientMessage(Component.literal("§bMana = §a" + newMana), false);
                                    return 1;
                                })
                        )
                )
                .then(Commands.literal("info")
                        .executes(context -> {
                            Player player = context.getSource().getPlayerOrException();
                            float current = PlayerManaData.getCurrentMana(player);
                            float max = PlayerManaData.getMaxMana(player);
                            player.displayClientMessage(Component.literal("§bMana: §a" + current + "§8/§a" + max), false);
                            return 1;
                        })
                )
        );
    }
}
