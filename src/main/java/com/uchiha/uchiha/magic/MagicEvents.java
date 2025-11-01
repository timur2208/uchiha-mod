package com.uchiha.uchiha.magic;

import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import com.timur208.uchiha.uchiha;

/**
 * Обработчик событий, связанных с магией и маной
 *
 * Этот класс обрабатывает:
 * - Инициализацию маны при входе игрока
 * - Синхронизацию маны между сервером и клиентом
 * - Восстановление маны каждый тик на сервере
 */
@Mod.EventBusSubscriber(modid = uchiha.MODID)
public class MagicEvents {

    // Регистрируем тип вложения (Attachment) для хранения данных маны
    // Это глобальная переменная, инициализируется при загрузке реестров
    public static AttachmentType<MagicData> MAGIC_DATA_ATTACHMENTS;

    /**
     * Событие входа игрока на сервер
     * Инициализируем данные маны для нового игрока
     */
    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();

        // Работаем только на сервере
        if (!player.level().isClientSide) {
            MagicData magicData = MagicData.get(player);

            // Если данных маны нет, создаём их
            if (magicData == null) {
                magicData = new MagicData();
                player.setData(MAGIC_DATA_ATTACHMENTS.get(), magicData);
            }
        }
    }

    /**
     * Событие смерти игрока
     * Сбрасываем ману при смерти
     */
    @SubscribeEvent
    public static void onPlayerDeath(PlayerEvent.Clone event) {
        Player oldPlayer = event.getOriginal();
        Player newPlayer = event.getEntity();

        if (!newPlayer.level().isClientSide) {
            MagicData oldMagicData = MagicData.get(oldPlayer);

            if (oldMagicData != null) {
                // Копируем данные маны на новый спавн игрока
                MagicData newMagicData = new MagicData();
                // Или сбрасываем её:
                // newMagicData.reset();
                newPlayer.setData(MAGIC_DATA_ATTACHMENTS.get(), newMagicData);
            }
        }
    }

    /**
     * Событие смены измерения (Dimension change)
     */
    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        Player player = event.getEntity();

        // Гарантируем, что данные маны существуют в новом измерении
        if (!player.level().isClientSide) {
            MagicData magicData = MagicData.get(player);
            if (magicData == null) {
                magicData = new MagicData();
                player.setData(MAGIC_DATA_ATTACHMENTS.get(), magicData);
            }
        }
    }

    /**
     * Событие сервера - тик сервера
     * Восстанавливаем ману каждый тик для всех игроков
     */
    @SubscribeEvent
    public static void onServerTick(ServerTickEvent event) {
        if (event.getServer() != null) {
            // Обновляем ману для всех игроков на сервере
            event.getServer().getPlayerList().getPlayers().forEach(player -> {
                MagicData magicData = MagicData.get(player);
                if (magicData != null) {
                    magicData.tick();
                }
            });
        }
    }

    /**
     * Вложенный класс для клиентских событий
     * Обновляем ману на клиенте каждый тик (для плавной анимации)
     */
    @Mod.EventBusSubscriber(modid = uchiha.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public static class ClientMagicEvents {

        @SubscribeEvent
        public static void onClientTick(net.neoforged.neoforge.event.tick.TickEvent.ClientTickEvent event) {
            // Обновляем в конце каждого тика
            if (event.phase == net.neoforged.neoforge.event.tick.TickEvent.Phase.END) {
                net.minecraft.client.Minecraft minecraft = net.minecraft.client.Minecraft.getInstance();
                if (minecraft.player != null && minecraft.level != null) {
                    MagicData magicData = MagicData.get(minecraft.player);
                    if (magicData != null) {
                        // На клиенте только отображаем анимацию
                        // Основной tick() данных происходит на сервере
                    }
                }
            }
        }
    }
}