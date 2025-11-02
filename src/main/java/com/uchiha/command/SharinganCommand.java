package com.uchiha.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import com.uchiha.capability.UchihaCapabilities;

public class SharinganCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
            Commands.literal("sharingan")
                .requires(cs -> cs.hasPermission(2))
                .then(Commands.argument("level", IntegerArgumentType.integer(0, 4))
                    .executes(context -> {
                        CommandSourceStack source = context.getSource();
                        Player player = source.getPlayerOrException();
                        int level = IntegerArgumentType.getInteger(context, "level");

                        var capability = UchihaCapabilities.getCapability(player);

                        if (!capability.hasUchiha()) {
                            player.displayClientMessage(
                                Component.literal("§cYou must become an Uchiha first! Use /becomeuchiha"),
                                false
                            );
                            return 0;
                        }

                        capability.setSharinganLevel(level);
                        String levelName = getLevelName(level);

                        player.displayClientMessage(
                            Component.literal("§cSharingan: " + levelName + " §cactivated!"),
                            false
                        );

                        return 1;
                    })
                )
        );
    }

    private static String getLevelName(int level) {
        return switch (level) {
            case 0 -> "Deactivated";
            case 1 -> "1-Tomoe";
            case 2 -> "2-Tomoe";
            case 3 -> "3-Tomoe";
            case 4 -> "Mangekyo";
            default -> "Unknown";
        };
    }
}
