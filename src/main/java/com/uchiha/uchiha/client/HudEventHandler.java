package com.uchiha.uchiha.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import com.uchiha.uchiha.magic.MagicData;

@Mod.EventBusSubscriber(modid = "uchiha", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
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
            MagicData magicData = MagicData.get(minecraft.player);
            if (magicData == null) return;
            int screenHeight = minecraft.getWindow().getGuiScaledHeight();
            int x = 10, y = screenHeight - 39, width = 81, height = 9;
            float currentMana = magicData.getCurrentMana();
            float maxMana = magicData.getMaxMana();
            float manaRatio = Math.max(0, Math.min(1, currentMana / maxMana));
            int filledWidth = (int) (width * manaRatio);
            guiGraphics.fill(x - 1, y - 1, x + width + 1, y + height + 1, 0xFF000000);
            guiGraphics.fill(x, y, x + width, y + height, 0xFF2A2A2A);
            guiGraphics.fill(x, y, x + filledWidth, y + height, 0xFF0066FF);
            guiGraphics.fill(x, y, x + filledWidth, y + 1, 0xFF3399FF);
            String manaText = String.format("Mana: %.0f / %.0f", currentMana, maxMana);
            guiGraphics.drawString(minecraft.font, manaText, x, y - 10, 0x0066FF);
        };
    }
}
