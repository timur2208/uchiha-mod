package com.uchiha.uchiha.client;

import net.neoforged.bus.api.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.RenderGuiOverlayEvent; // твой ивент

@EventBusSubscriber(modid = "uchiha", value = Dist.CLIENT)
public class HudEventHandler {
    @SubscribeEvent
    public static void onRenderHUD(RenderGuiOverlayEvent.Post event) {
        // Логика рендера HUD
    }
}
