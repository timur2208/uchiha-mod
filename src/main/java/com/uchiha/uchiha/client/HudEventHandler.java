package com.uchiha.uchiha.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.bus.api.SubscribeEvent;
import com.uchiha.uchiha.magic.PlayerManaData;

public class HudEventHandler {
    private static final ResourceLocation MANA_LAYER_ID = ResourceLocation.fromNamespaceAndPath("uchiha", "mana_bar");

    @SubscribeEvent
    public static void registerGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAboveAll(MANA_LAYER_ID, (guiGraphics, partialTick) -> {
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.level == null || minecraft.player == null) return;

            // Инициализируем при первом отображении
            PlayerManaData.initializePlayer(minecraft.player);

            // ОДНА переменная маны
            float currentMana = PlayerManaData.getCurrentMana(minecraft.player);
            float maxMana = PlayerManaData.getMaxMana(minecraft.player);

            int screenHeight = minecraft.getWindow().getGuiScaledHeight();
            int x = 10, y = screenHeight - 39, width = 81, height = 9;

            float manaRatio = Math.max(0, Math.min(1, currentMana / maxMana));
            int filledWidth = (int) (width * manaRatio);

            // Рамка
            guiGraphics.fill(x - 1, y - 1, x + width + 1, y + height + 1, 0xFF000000);
            // Фон
            guiGraphics.fill(x, y, x + width, y + height, 0xFF1a1a1a);

            // Цвет маны
            int fillColor;
            if (manaRatio > 0.7f) fillColor = 0xFF0099FF;
            else if (manaRatio > 0.4f) fillColor = 0xFF0066FF;
            else if (manaRatio > 0.2f) fillColor = 0xFF0044AA;
            else if (manaRatio > 0f) fillColor = 0xFF6600FF;
            else fillColor = 0xFF330033;

            // Заполнение
            if (filledWidth > 0) {
                guiGraphics.fill(x, y, x + filledWidth, y + height, fillColor);
                guiGraphics.fill(x, y, x + filledWidth, y + 1, 0xFF33CCFF);
            }

            // Текст
            guiGraphics.drawString(minecraft.font, String.format("Mana: %.0f/%.0f", currentMana, maxMana), x, y - 10, 0x33CCFF);
            String percentText = String.format("%.0f%%", manaRatio * 100);
            int percentX = x + width - minecraft.font.width(percentText) - 2;
            guiGraphics.drawString(minecraft.font, percentText, percentX, y + 1, 0xFFFFFF);
        });
    }
}
