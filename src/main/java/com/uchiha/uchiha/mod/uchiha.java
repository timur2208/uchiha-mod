package com.uchiha.uchiha.mod;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import org.slf4j.Logger;
import com.uchiha.uchiha.command.ManaCommands;
import com.uchiha.uchiha.mana.ManaAttachment;

@Mod("uchiha")
public class uchiha {
    public static final String MODID = "uchiha";
    public static final Logger LOGGER = LogUtils.getLogger();

    public uchiha(IEventBus modEventBus) {
        modEventBus.addListener(this::commonSetup);

        // Регистрируем аттачменты
        modEventBus.register(ManaAttachment.ATTACHMENT_TYPES);

        // Регистрируем команду
        NeoForge.EVENT_BUS.addListener((RegisterCommandsEvent event) -> {
            ManaCommands.register(event.getDispatcher());
        });
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Uchiha Mod: Loaded!");
    }
}
