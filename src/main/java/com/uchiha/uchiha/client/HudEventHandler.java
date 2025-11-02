package com.uchiha.uchiha.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import com.uchiha.uchiha.magic.PlayerManaData;

@Mod.EventBusSubscriber(modid = "uchiha", value = Dist.CLIENT)
public class HudEventHandler {
    private static final ResourceLocation LAYER_ID = ResourceLocation.fromNamespaceAndPath("uchiha", "mana_bar");

    @SubscribeEvent
    public static void registerLayers(RegisterGuiLayersEvent event) {
        event.registerAboveAll(LAYER_ID, (gui, tick) -> {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player == null || mc.level == null) return;

            PlayerManaData.initMana(mc.player);
            float mana = PlayerManaData.getMana(mc.player);
            float maxMana = PlayerManaData.getMaxMana(mc.player);

            int h = mc.getWindow().getGuiScaledHeight();
            int x = 10, y = h - 39, w = 81, hh = 9;

            float ratio = mana / maxMana;
            int filled = (int)(w * ratio);

            gui.fill(x - 1, y - 1, x + w + 1, y + hh + 1, 0xFF000000);
            gui.fill(x, y, x + w, y + hh, 0xFF1a1a1a);

            int color = ratio > 0.7f ? 0xFF0099FF : ratio > 0.4f ? 0xFF0066FF : ratio > 0.2f ? 0xFF0044AA : 0xFF6600FF;
            if (filled > 0) {
                gui.fill(x, y, x + filled, y + hh, color);
                gui.fill(x, y, x + filled, y + 1, 0xFF33CCFF);
            }

            gui.drawString(mc.font, String.format("Mana: %.0f/%.0f", mana, maxMana), x, y - 10, 0x33CCFF);
            String pct = String.format("%.0f%%", ratio * 100);
            gui.drawString(mc.font, pct, x + w - mc.font.width(pct) - 2, y + 1, 0xFFFFFF);
        });
    }
}
