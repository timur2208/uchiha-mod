package com.uchiha.uchiha.client;

import com.uchiha.uchiha.mana.CustomMana;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "uchiha", value = Dist.CLIENT)
public class HudOverlay {

    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        int width = mc.getWindow().getGuiScaledWidth();
        int height = mc.getWindow().getGuiScaledHeight();

        int maxMana = CustomMana.maxMana;
        int currentMana = CustomMana.currentMana;
        int barWidth = 80;
        int barHeight = 8;
        int x = width / 2 - barWidth / 2;
        int y = height - 45;

        int manaColor = 0xFF4040FF;

        GuiGraphics gui = event.getGuiGraphics();
        // Фон
        gui.fill(x, y, x + barWidth, y + barHeight, 0x70000055);
        // Прогресс
        int fill = (int)((double)currentMana / (double)maxMana * barWidth);
        gui.fill(x, y, x + fill, y + barHeight, manaColor);
        // Текст
        mc.font.draw(gui.pose(), currentMana + "/" + maxMana, x + barWidth + 4, y, manaColor);
    } // <-- Вот тут была ошибка! НЕ хватает вот этой скобки.
}

