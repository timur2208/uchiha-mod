package com.uchiha.uchiha.client;

import com.uchiha.uchiha.magic.MagicData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

// Пример пути: "uchiha:textures/gui/mana_bar.png"
public class ManaHud {
    private static final ResourceLocation BAR_BG = new ResourceLocation("uchiha", "textures/gui/mana_bar.png");
    private static final ResourceLocation BAR_FG = new ResourceLocation("uchiha", "textures/gui/mana_bar_fg.png");

    public static void render(GuiGraphics guiGraphics, MagicData data, int x, int y) {
        Minecraft mc = Minecraft.getInstance();
        int barWidth = 120;
        int barHeight = 16;

        // Нарисовать фон
        guiGraphics.blit(BAR_BG, x, y, 0, 0, barWidth, barHeight, barWidth, barHeight);

        // Рассчитать ширину заполнения
        float percent = (float) data.getMana() / (float) data.getMaxMana();
        int fgWidth = (int) (barWidth * percent);

        // Нарисовать foreground (заполнение)
        guiGraphics.blit(BAR_FG, x, y, 0, 0, fgWidth, barHeight, barWidth, barHeight);

        // Нарисовать текст
        guiGraphics.drawString(mc.font, data.getMana() + " / " + data.getMaxMana(), x + barWidth / 2 - 15, y + barHeight / 2 - 4, 0xFFFFFF, true);
    }
}
