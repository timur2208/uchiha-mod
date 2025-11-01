package com.uchiha.uchiha.mod;

import com.mojang.logging.LogUtils;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;
import com.timur208.uchiha.config.Config;
import com.timur208.uchiha.client.uchihaClient;
import com.timur208.uchiha.data.MagicEvents;

/**
 * Главный класс мода Uchiha
 * Инициализирует все компоненты и события мода
 *
 * Правильная аннотация для NeoForge 1.21.1:
 * @Mod("modid") - регистрирует мод в системе
 */
@Mod("uchiha")
public class uchiha {
    public static final String MODID = "uchiha";
    private static final Logger LOGGER = LogUtils.getLogger();

    public uchiha(IEventBus modEventBus) {
        // Регистрируем конфиг
        ModLoadingContext.getInstance().registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        // Добавляем обработчик события общей подготовки
        modEventBus.addListener(this::commonSetup);

        // Регистрируем события на FORGE шине (для сервера)
        NeoForge.EVENT_BUS.register(MagicEvents.class);
        NeoForge.EVENT_BUS.register(this);
    }

    /**
     * Общая подготовка мода (вызывается на сервере и клиенте)
     */
    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("=== Uchiha Mod: Common Setup ===");
        // Здесь можно инициализировать общие ресурсы
    }

    /**
     * Событие запуска сервера
     */
    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("=== Uchiha Mod: Server Starting ===");
    }

    /**
     * Класс для инициализации клиентских событий
     *
     * Правильная регистрация для клиента:
     * - Bus.MOD для событий загрузки
     * - value = Dist.CLIENT для эксклюзивного доступа на клиенте
     */
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            LOGGER.info("=== Uchiha Mod: Client Setup ===");
            // Инициализируем клиентскую часть мода
            uchihaClient.setup();
        }
    }
}