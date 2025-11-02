package com.uchiha.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import com.uchiha.capability.UchihaCapabilities;

public class BecomeUchihaCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
            Commands.literal("becomeuchiha")
                .requires(cs -> cs.hasPermission(2))
                .executes(context -> {
                    CommandSourceStack source = context.getSource();
                    Player player = source.getPlayerOrException();

                    // Get capability and initialize
                    var capability = UchihaCapabilities.getCapability(player);
                    capability.setUchiha(true);
                    capability.resetMana();
                    capability.setSharinganLevel(0);

                    player.displayClientMessage(
                        Component.literal("§cYou are now an Uchiha! Use /sharingan <level> to activate your eye."),
                        false
                    );

                    return 1;
                })
        );
    }
}
