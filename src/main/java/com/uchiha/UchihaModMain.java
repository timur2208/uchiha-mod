package com.uchiha;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mojang.logging.LogUtils;
import com.uchiha.command.BecomeUchihaCommand;
import com.uchiha.command.SharinganCommand;
import com.uchiha.config.UchihaConfig;
import com.uchiha.entity.EntityRegistry;
import com.uchiha.event.DamageEventHandler;
import com.uchiha.event.PlayerDeathEventHandler;
import com.uchiha.event.PlayerTickHandler;

@Mod("uchiha")
public class UchihaModMain {
    public static final String MOD_ID = "uchiha";
    public static final Logger LOGGER = LogUtils.getLogger();

    public UchihaModMain(IEventBus modEventBus, ModContainer modContainer) {
        // Регистрируем конфиг
        modContainer.registerConfig(net.neoforged.fml.config.ModConfig.Type.COMMON, UchihaConfig.SPEC);

        // Подписываем события
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);

        // Регистрируем команды
        NeoForge.EVENT_BUS.addListener(this::registerCommands);

        // Регистрируем обработчики событий
        NeoForge.EVENT_BUS.addListener(DamageEventHandler::onLivingDamage);
        NeoForge.EVENT_BUS.addListener(PlayerDeathEventHandler::onPlayerDeath);
        NeoForge.EVENT_BUS.addListener(PlayerTickHandler::onPlayerTick);

        // Регистрируем сущности
        EntityRegistry.register(modEventBus);

        LOGGER.info("Uchiha Mod initialized!");
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            LOGGER.info("Common setup complete!");
        });
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            LOGGER.info("Client setup complete!");
        });
    }

    private void registerCommands(final RegisterCommandsEvent event) {
        BecomeUchihaCommand.register(event.getDispatcher());
        SharinganCommand.register(event.getDispatcher());
        LOGGER.info("Commands registered!");
    }
}
