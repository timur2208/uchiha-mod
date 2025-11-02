package com.uchiha.uchiha.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import com.uchiha.uchiha.mana.ManaAttachment;

public class ManaCommands {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("mana")
                .then(Commands.literal("add")
                        .then(Commands.argument("amount", FloatArgumentType.floatArg())
                                .executes(ctx -> {
                                    var player = ctx.getSource().getPlayerOrException();
                                    float amount = FloatArgumentType.getFloat(ctx, "amount");
                                    var mana = ManaAttachment.getMana(player);
                                    mana.addMana(amount);
                                    player.displayClientMessage(Component.literal("§a+§b" + amount + " §aMana"), false);
                                    return 1;
                                })
                        )
                )
                .then(Commands.literal("remove")
                        .then(Commands.argument("amount", FloatArgumentType.floatArg())
                                .executes(ctx -> {
                                    var player = ctx.getSource().getPlayerOrException();
                                    float amount = FloatArgumentType.getFloat(ctx, "amount");
                                    var mana = ManaAttachment.getMana(player);
                                    if (mana.spendMana(amount)) {
                                        player.displayClientMessage(Component.literal("§c-§b" + amount + " §cMana"), false);
                                    } else {
                                        player.displayClientMessage(Component.literal("§c✗ Недостаточно маны!"), false);
                                    }
                                    return 1;
                                })
                        )
                )
                .then(Commands.literal("set")
                        .then(Commands.argument("amount", FloatArgumentType.floatArg())
                                .executes(ctx -> {
                                    var player = ctx.getSource().getPlayerOrException();
                                    float amount = FloatArgumentType.getFloat(ctx, "amount");
                                    var mana = ManaAttachment.getMana(player);
                                    mana.setMana(amount);
                                    player.displayClientMessage(Component.literal("§bMana = §a" + amount), false);
                                    return 1;
                                })
                        )
                )
                .then(Commands.literal("info")
                        .executes(ctx -> {
                            var player = ctx.getSource().getPlayerOrException();
                            var mana = ManaAttachment.getMana(player);
                            player.displayClientMessage(
                                    Component.literal("§bMana: §a" + mana.getMana() + "§8/§a" + mana.getMaxMana()),
                                    false
                            );
                            return 1;
                        })
                )
        );
    }
}
