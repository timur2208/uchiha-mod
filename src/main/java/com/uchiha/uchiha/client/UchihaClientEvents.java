package com.uchiha.uchiha.client;

import com.uchiha.uchiha.magic.MagicData;
import net.minecraft.world.entity.player.Player;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;

@Mod.EventBusSubscriber(modid = "uchiha", value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class UchihaClientEvents {

    @SubscribeEvent
    public static void onRenderGuiOverlay(RenderGuiOverlayEvent.Pre event) {
        Player player = Minecraft.getInstance().player;
        if (player == null) return;

        // Получи реальный MagicData игрока!
        MagicData magicData = new MagicData(100); // для теста — max 100

        // Вызови рендер маны
        ManaHud.render(event.getGuiGraphics(), magicData, 10, 10);
    }
}
