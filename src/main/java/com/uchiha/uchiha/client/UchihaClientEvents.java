package com.uchiha.uchiha.client;

import com.uchiha.uchiha.magic.MagicData;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.event.RenderGuiOverlayEvent;

public class UchihaClientEvents {

    public static void onRenderGuiOverlay(RenderGuiOverlayEvent.Pre event) {
        Player player = Minecraft.getInstance().player;
        if (player == null) return;
        MagicData magicData = new MagicData(100); // тестовый объект
        ManaHud.render(event.getGuiGraphics(), magicData, 10, 10);
    }
}

