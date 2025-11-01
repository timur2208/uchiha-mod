package com.uchiha.uchiha.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import com.timur208.uchiha.uchiha;
import com.timur208.uchiha.data.MagicData;

/**
 * Обработчик отрисовки HUD полоски маны
 * Регистрирует пользовательский слой GUI для отображения индикатора маны
 *
 * Правильное использование в NeoForge 1.21.1:
 * - Используем RegisterGuiLayersEvent (не RenderGuiOverlayEvent)
 * - Используем LayeredDraw.Layer для создания слоя
 * - Регистрируем на MOD шине событий, не FORGE
 */
@Mod.EventBusSubscriber(modid = uchiha.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class HudEventHandler {

    // ID для нашего слоя GUI (должен быть уникальным в глобальном пространстве имён)
    private static final ResourceLocation MANA_LAYER_ID =
            ResourceLocation.fromNamespaceAndPath(uchiha.MODID, "mana_bar");

    /**
     * Регистрирует слой маны в очередь отрисовки GUI
     * Вызывается один раз при загрузке клиента
     */
    @SubscribeEvent
    public static void registerGuiLayers(RegisterGuiLayersEvent event) {
        // Регистрируем слой маны поверх всех остальных слоёв
        event.registerAboveAll(MANA_LAYER_ID, createManaLayer());
    }

    /**
     * Создаёт LayeredDraw.Layer для отрисовки полоски маны
     *
     * LayeredDraw.Layer - функциональный интерфейс с методом:
     * void render(GuiGraphics guiGraphics, float partialTick)
     */
    private static LayeredDraw.Layer createManaLayer() {
        return (guiGraphics, partialTick) -> {
            Minecraft minecraft = Minecraft.getInstance();

            // Проверяем, что игра инициализирована
            if (minecraft.level == null || minecraft.player == null) {
                return;
            }

            // Получаем данные маны для игрока
            MagicData magicData = MagicData.get(minecraft.player);
            if (magicData == null) {
                return;
            }

            // Параметры экрана
            int screenWidth = minecraft.getWindow().getGuiScaledWidth();
            int screenHeight = minecraft.getWindow().getGuiScaledHeight();

            // Параметры отрисовки полоски маны
            // Позиция: под здоровьем слева (где обычно ненужное место)
            int x = 10;
            int y = screenHeight - 39;
            int width = 81;   // Ширина полоски (как у здоровья)
            int height = 9;   // Высота полоски

            // Получаем текущую и максимальную ману
            float currentMana = magicData.getCurrentMana();
            float maxMana = magicData.getMaxMana();

            // Вычисляем заполненную часть полоски (от 0 до 1)
            float manaRatio = Math.max(0, Math.min(1, currentMana / maxMana));
            int filledWidth = (int) (width * manaRatio);

            // ===== ОТРИСОВКА ПОЛОСКИ МАНЫ =====

            // 1. Рамка полоски (чёрная граница)
            guiGraphics.fill(x - 1, y - 1, x + width + 1, y + height + 1, 0xFF000000);

            // 2. Фоновая часть полоски (тёмно-серый фон)
            guiGraphics.fill(x, y, x + width, y + height, 0xFF2A2A2A);

            // 3. Заполняющая часть полоски (градиент синего цвета - Sharingan)
            // Используем градиент для визуального улучшения
            int topColor = 0xFF0099FF;     // Светлый синий
            int bottomColor = 0xFF0044AA;  // Тёмный синий

            // Отрисовка градиента заполненной части
            guiGraphics.fillGradient(
                    x, y,                      // левый верхний угол
                    x + filledWidth, y + height, // правый нижний угол
                    0,                         // z-слой
                    topColor,                  // верхний цвет
                    bottomColor                // нижний цвет
            );

            // 4. Блик на полоске (визуальный эффект объёма)
            guiGraphics.fill(x, y, x + filledWidth, y + 1, 0xFF33CCFF);

            // ===== ОТРИСОВКА ТЕКСТА =====

            // Формируем текст с маной
            String manaText = String.format("Mana: %.0f / %.0f", currentMana, maxMana);

            // Определяем цвет текста в зависимости от процента маны
            int textColor;
            if (manaRatio > 0.5f) {
                textColor = 0x00FF00;  // Зелёный - полная мана
            } else if (manaRatio > 0.25f) {
                textColor = 0xFFFF00;  // Жёлтый - половина маны
            } else {
                textColor = 0xFF0000;  // Красный - низко маны
            }

            // Отрисовываем текст над полоской
            guiGraphics.drawString(minecraft.font, manaText, x, y - 10, textColor);

            // ===== ДОПОЛНИТЕЛЬНЫЕ ВИЗУАЛЬНЫЕ ЭФФЕКТЫ =====

            // Показываем процент маны (опционально)
            String percentText = String.format("%.0f%%", manaRatio * 100);
            guiGraphics.drawString(
                    minecraft.font,
                    percentText,
                    x + width - minecraft.font.width(percentText),
                    y + height + 2,
                    0xAAAAAA  // Серый цвет
            );
        };
    }
}