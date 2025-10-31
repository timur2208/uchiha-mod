package com.uchiha.uchiha.client;

import com.uchiha.uchiha.magic.MagicData;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "uchiha", value = Dist.CLIENT)
public class HudEventHandler {

    @SubscribeEvent
    public static void onRenderHUD(RenderGameOverlayEvent.Post event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) return;

        GuiGraphics guiGraphics = new GuiGraphics(event.getMatrixStack(), event.getWindow().getWidth(), event.getWindow().getHeight());
        // Создаем тестовый объект маны
        MagicData testData = new MagicData(100);
        testData.setMana(50);

        ManaHud.render(guiGraphics, testData, 10, 10);
    }
}
