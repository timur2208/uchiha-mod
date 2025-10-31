package com.uchiha.uchiha.client;

import com.uchiha.uchiha.magic.MagicData;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class UchihaHudScreen extends Screen {
    public UchihaHudScreen() {
        super(Component.literal("Uchiha HUD"));
    }
    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);
        ManaHud.render(graphics, new MagicData(100), 10, 10);
    }
}
