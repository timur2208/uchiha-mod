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