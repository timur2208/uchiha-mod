package com.uchiha.uchiha.magic;

import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

@Mod.EventBusSubscriber(modid = "uchiha")
public class MagicEvents {
    public static AttachmentType<MagicData> MAGIC_DATA_ATTACHMENTS;
    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        if (!player.level().isClientSide) {
            MagicData magicData = MagicData.get(player);
            if (magicData == null) {
                magicData = new MagicData();
                player.setData(MAGIC_DATA_ATTACHMENTS.get(), magicData);
            }
        }
    }
    @SubscribeEvent
    public static void onServerTick(ServerTickEvent event) {
        if (event.getServer() != null && event.phase == ServerTickEvent.Phase.END) {
            event.getServer().getPlayerList().getPlayers().forEach(player -> {
                MagicData magicData = MagicData.get(player);
                if (magicData != null) magicData.tick();
            });
        }
    }
}
```

        ---

        ## src/main/java/com/uchiha/uchiha/mod/uchiha.java
```java
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
import com.uchiha.uchiha.mod.Config;
import com.uchiha.uchiha.client.uchihaClient;
import com.uchiha.uchiha.magic.MagicEvents;

@Mod("uchiha")
public class uchiha {
    public static final String MODID = "uchiha";
    private static final Logger LOGGER = LogUtils.getLogger();
    public uchiha(IEventBus modEventBus) {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        modEventBus.addListener(this::commonSetup);
        NeoForge.EVENT_BUS.register(MagicEvents.class);
        NeoForge.EVENT_BUS.register(this);
    }
    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Uchiha Mod: Common Setup!");
    }
    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("Uchiha Mod: Server Starting!");
    }
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            LOGGER.info("Uchiha Mod: Client Setup!");
            uchihaClient.setup();
        }
    }
}