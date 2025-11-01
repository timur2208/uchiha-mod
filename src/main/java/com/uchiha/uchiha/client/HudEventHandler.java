package com.uchiha.uchiha.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.bus.api.SubscribeEvent;
import com.uchiha.uchiha.magic.PlayerManaData;

public class HudEventHandler {
    private static final ResourceLocation MANA_LAYER_ID = ResourceLocation.fromNamespaceAndPath("uchiha", "mana_bar");

    @SubscribeEvent
    public static void registerGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAboveAll(MANA_LAYER_ID, createManaLayer());
    }

    private static LayeredDraw.Layer createManaLayer() {
        return (guiGraphics, partialTick) -> {
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.level == null || minecraft.player == null) return;

            float currentMana = PlayerManaData.getCurrentMana(minecraft.player);
            float maxMana = PlayerManaData.getMaxMana(minecraft.player);

            int screenHeight = minecraft.getWindow().getGuiScaledHeight();
            int x = 10, y = screenHeight - 39, width = 81, height = 9;

            float manaRatio = Math.max(0, Math.min(1, currentMana / maxMana));
            int filledWidth = (int) (width * manaRatio);

            // Рамка (чёрная)
            guiGraphics.fill(x - 1, y - 1, x + width + 1, y + height + 1, 0xFF000000);

            // Фон (тёмно-серый)
            guiGraphics.fill(x, y, x + width, y + height, 0xFF1a1a1a);

            // Определяем цвет заполнения в зависимости от процента маны
            int fillColor;
            if (manaRatio > 0.7f) {
                fillColor = 0xFF0099FF;  // Светлый синий (много маны)
            } else if (manaRatio > 0.4f) {
                fillColor = 0xFF0066FF;  // Средний синий
            } else if (manaRatio > 0.2f) {
                fillColor = 0xFF0044AA;  // Тёмный синий
            } else {
                fillColor = 0xFF6600FF;  // Фиолетовый (мало маны)
            }

            // Заполняющая часть (со слоями для 3D эффекта)
            guiGraphics.fill(x, y, x + filledWidth, y + height, fillColor);

            // Блик сверху (светлая полоска)
            guiGraphics.fill(x, y, x + filledWidth, y + 1, 0xFF33CCFF);

            // Тень снизу (тёмная полоска)
            if (filledWidth > 0) {
                guiGraphics.fill(x, y + height - 1, x + filledWidth, y + height, 0xFF003366);
            }

            // Текст с маной (синий, над полоской)
            String manaText = String.format("Mana: %.0f / %.0f", currentMana, maxMana);
            guiGraphics.drawString(minecraft.font, manaText, x, y - 10, 0x33CCFF);

            // Процент маны (опционально, справа)
            String percentText = String.format("%.0f%%", manaRatio * 100);
            int percentX = x + width - minecraft.font.width(percentText) - 2;
            guiGraphics.drawString(minecraft.font, percentText, percentX, y + 1, 0xFFFFFF);
        };
    }
}
