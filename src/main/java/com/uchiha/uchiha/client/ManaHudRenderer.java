package com.uchiha.uchiha.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import com.uchiha.uchiha.mana.ManaAttachment;

@Mod.EventBusSubscriber(modid = "uchiha", value = Dist.CLIENT)
public class ManaHudRenderer {
    private static final ResourceLocation MANA_BAR = ResourceLocation.fromNamespaceAndPath("uchiha", "mana_bar");

    @SubscribeEvent
    public static void registerLayers(RegisterGuiLayersEvent event) {
        event.registerAboveAll(MANA_BAR, ManaHudRenderer::renderManaBar);
    }

    public static void renderManaBar(GuiGraphics gui, float tick) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return;

        var manaData = ManaAttachment.getMana(mc.player);
        float mana = manaData.getMana();
        float maxMana = manaData.getMaxMana();
        float ratio = mana / maxMana;

        int screenHeight = mc.getWindow().getGuiScaledHeight();
        int x = 10, y = screenHeight - 39, w = 81, h = 9;
        int filled = (int)(w * ratio);

        // Рамка
        gui.fill(x - 1, y - 1, x + w + 1, y + h + 1, 0xFF000000);
        // Фон
        gui.fill(x, y, x + w, y + h, 0xFF1a1a1a);
        // Заполнение
        int color = ratio > 0.7f ? 0xFF0099FF : ratio > 0.4f ? 0xFF0066FF : 0xFF6600FF;
        if (filled > 0) {
            gui.fill(x, y, x + filled, y + h, color);
            gui.fill(x, y, x + filled, y + 1, 0xFF33CCFF);
        }

        // Текст
        gui.drawString(mc.font, String.format("Mana: %.0f/%.0f", mana, maxMana), x, y - 10, 0x33CCFF);
        String pct = String.format("%.0f%%", ratio * 100);
        gui.drawString(mc.font, pct, x + w - mc.font.width(pct) - 2, y + 1, 0xFFFFFF);
    }
}
