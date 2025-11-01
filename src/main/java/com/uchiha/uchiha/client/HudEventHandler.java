package com.uchiha.uchiha.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.bus.api.SubscribeEvent;
import com.uchiha.uchiha.magic.PlayerManaData;

public class HudEventHandler {
    private static final ResourceLocation MANA_LAYER_ID = ResourceLocation.fromNamespaceAndPath("uchiha", "mana_bar");
    private static boolean initialized = false;

    @SubscribeEvent
    public static void registerGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAboveAll(MANA_LAYER_ID, (guiGraphics, partialTick) -> {
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.level == null || minecraft.player == null) return;

            // Гарантированная инициализация при первом отображении
            if (!initialized) {
                PlayerManaData.initializePlayer(minecraft.player);
                initialized = true;
            }

            float currentMana = PlayerManaData.getCurrentMana(minecraft.player);
            float maxMana = PlayerManaData.getMaxMana(minecraft.player);

            // Если ещё 0 - инициализируем
            if (currentMana == 0) {
                currentMana = 100f;
                PlayerManaData.setCurrentMana(minecraft.player, currentMana);
            }
            if (maxMana == 0) {
                maxMana = 200f;
                PlayerManaData.setMaxMana(minecraft.player, maxMana);
            }

            int screenHeight = minecraft.getWindow().getGuiScaledHeight();
            int x = 10, y = screenHeight - 39, width = 81, height = 9;

            float manaRatio = Math.max(0, Math.min(1, currentMana / maxMana));
            int filledWidth = (int) (width * manaRatio);

            // Рамка (чёрная)
            guiGraphics.fill(x - 1, y - 1, x + width + 1, y + height + 1, 0xFF000000);

            // Фон (тёмно-серый)
            guiGraphics.fill(x, y, x + width, y + height, 0xFF1a1a1a);

            // Цвет в зависимости от % маны
            int fillColor;
            if (manaRatio > 0.7f) {
                fillColor = 0xFF0099FF;  // Светлый синий
            } else if (manaRatio > 0.4f) {
                fillColor = 0xFF0066FF;  // Средний синий
            } else if (manaRatio > 0.2f) {
                fillColor = 0xFF0044AA;  // Тёмный синий
            } else if (manaRatio > 0f) {
                fillColor = 0xFF6600FF;  // Фиолетовый
            } else {
                fillColor = 0xFF330033;  // Очень тёмный
            }

            // Заполнение
            if (filledWidth > 0) {
                guiGraphics.fill(x, y, x + filledWidth, y + height, fillColor);
                // Блик
                guiGraphics.fill(x, y, x + filledWidth, y + 1, 0xFF33CCFF);
            }

            // Текст
            String manaText = String.format("Mana: %.0f / %.0f", currentMana, maxMana);
            guiGraphics.drawString(minecraft.font, manaText, x, y - 10, 0x33CCFF);

            // Процент
            String percentText = String.format("%.0f%%", manaRatio * 100);
            int percentX = x + width - minecraft.font.width(percentText) - 2;
            guiGraphics.drawString(minecraft.font, percentText, percentX, y + 1, 0xFFFFFF);
        });
    }
}
