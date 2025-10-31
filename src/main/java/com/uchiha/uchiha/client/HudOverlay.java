@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class HudOverlay {
    @SubscribeEvent
    public static void renderOverlay(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            Minecraft mc = Minecraft.getInstance();
            int width = mc.getWindow().getGuiScaledWidth();
            int height = mc.getWindow().getGuiScaledHeight();

            // Параметры второй маны
            int maxMana = CustomMana.maxMana;
            int currentMana = CustomMana.currentMana;
            int barWidth = 80;
            int barHeight = 8;

            // Координаты рисования (например, чуть выше EXP)
            int x = width / 2 - barWidth / 2;
            int y = height - 45;

            // Ваш цвет полоски
            int manaColor = 0x4040FF;

            // Фон
            GuiComponent.fill(event.getMatrixStack(), x, y, x + barWidth, y + barHeight, 0x70000055);
            // Сам прогресс
            int fill = (int)((double)currentMana / (double)maxMana * barWidth);
            GuiComponent.fill(event.getMatrixStack(), x, y, x + fill, y + barHeight, manaColor);

            // Текст (опционально)
            mc.font.draw(event.getMatrixStack(), currentMana + "/" + maxMana, x + barWidth + 4, y, manaColor);
        }
    }
}
