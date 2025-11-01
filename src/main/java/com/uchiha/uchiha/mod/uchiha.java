package com.uchiha.uchiha.mod;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;
import com.uchiha.uchiha.client.HudEventHandler;
import com.uchiha.uchiha.magic.ManaTickHandler;

@Mod("uchiha")
public class uchiha {
    public static final String MODID = "uchiha";
    public static final Logger LOGGER = LogUtils.getLogger();

    public uchiha(IEventBus modEventBus) {
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(HudEventHandler::registerGuiLayers);
        NeoForge.EVENT_BUS.register(ManaTickHandler.class);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Uchiha Mod: Common Setup!");
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        LOGGER.info("Uchiha Mod: Client Setup!");
    }
}