package com.uchiha.uchiha.client;

import com.uchiha.uchiha.magic.MagicData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RenderGuiOverlayEvent;

@Mod.EventBusSubscriber(modid = "uchiha", value = Dist.CLIENT)
public class HudEventHandler {

    @SubscribeEvent
    public static void onRenderHUD(RenderGuiOverlayEvent.Post event) {
        // Это важно! — HUD рисовать только на общем экране
        if (!event.getOverlay().id().toString().equals("minecraft:all")) return;

        MagicData testData = new MagicData(100);
        testData.setMana(50);

        ManaHud.render(event.getGuiGraphics(), testData, 10, 10);
    }
}
