package com.uchiha.uchiha.mod;

import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.logging.LogUtils;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.slf4j.Logger;
import com.uchiha.uchiha.client.ClientTickHandler;
import com.uchiha.uchiha.client.HudEventHandler;
import com.uchiha.uchiha.magic.ManaTickHandler;
import com.uchiha.uchiha.magic.PlayerManaData;

@Mod("uchiha")
public class uchiha {
    public static final String MODID = "uchiha";
    public static final Logger LOGGER = LogUtils.getLogger();

    public uchiha(IEventBus modEventBus) {
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);

        // Регистрируем HUD
        NeoForge.EVENT_BUS.register(new HudEventHandler());

        // КОМАНДА
        NeoForge.EVENT_BUS.addListener((RegisterCommandsEvent event) -> {
            event.getDispatcher().register(Commands.literal("uchihamana")
                    .then(Commands.literal("add")
                            .then(Commands.argument("amount", FloatArgumentType.floatArg())
                                    .executes(ctx -> {
                                        var p = ctx.getSource().getPlayerOrException();
                                        float amt = FloatArgumentType.getFloat(ctx, "amount");
                                        PlayerManaData.setMana(p, PlayerManaData.getMana(p) + amt);
                                        p.displayClientMessage(Component.literal("§a+§b" + amt + " §aMana"), false);
                                        return 1;
                                    })
                            )
                    )
                    .then(Commands.literal("remove")
                            .then(Commands.argument("amount", FloatArgumentType.floatArg())
                                    .executes(ctx -> {
                                        var p = ctx.getSource().getPlayerOrException();
                                        float amt = FloatArgumentType.getFloat(ctx, "amount");
                                        PlayerManaData.setMana(p, PlayerManaData.getMana(p) - amt);
                                        p.displayClientMessage(Component.literal("§c-§b" + amt + " §cMana"), false);
                                        return 1;
                                    })
                            )
                    )
                    .then(Commands.literal("set")
                            .then(Commands.argument("amount", FloatArgumentType.floatArg())
                                    .executes(ctx -> {
                                        var p = ctx.getSource().getPlayerOrException();
                                        float amt = FloatArgumentType.getFloat(ctx, "amount");
                                        PlayerManaData.setMana(p, amt);
                                        p.displayClientMessage(Component.literal("§bMana = §a" + amt), false);
                                        return 1;
                                    })
                            )
                    )
                    .then(Commands.literal("info")
                            .executes(ctx -> {
                                var p = ctx.getSource().getPlayerOrException();
                                float m = PlayerManaData.getMana(p);
                                float mm = PlayerManaData.getMaxMana(p);
                                p.displayClientMessage(Component.literal("§bMana: §a" + m + "§8/§a" + mm), false);
                                return 1;
                            })
                    )
            );
        });

        // Инициализация
        NeoForge.EVENT_BUS.addListener((PlayerEvent.PlayerLoggedInEvent event) -> {
            ManaTickHandler.onPlayerLoggedIn(event);
        });

        // Восстановление
        NeoForge.EVENT_BUS.addListener((ClientTickEvent.Post event) -> {
            ClientTickHandler.onTick(event);
        });
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Uchiha Mod: Common Setup!");
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        LOGGER.info("Uchiha Mod: Client Setup!");
    }
}
