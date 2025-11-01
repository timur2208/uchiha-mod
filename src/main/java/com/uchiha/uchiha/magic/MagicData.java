package com.uchiha.uchiha.magic;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.util.INBTSerializable;

/**
 * Хранит данные маны для каждого игрока
 * Синхронизируется между клиентом и сервером через Attachment API
 */
public class MagicData implements INBTSerializable<CompoundTag> {

    // Параметры маны
    private float currentMana;
    private float maxMana;
    private float manaRegenRate;

    // Параметры способностей
    private boolean sharinganActive = false;
    private int sharinganCooldown = 0;

    // Констаны по умолчанию (можно перенести в конфиг)
    private static final float DEFAULT_CURRENT_MANA = 100.0f;
    private static final float DEFAULT_MAX_MANA = 200.0f;
    private static final float DEFAULT_MANA_REGEN_PER_TICK = 0.5f;

    public MagicData() {
        this.currentMana = DEFAULT_CURRENT_MANA;
        this.maxMana = DEFAULT_MAX_MANA;
        this.manaRegenRate = DEFAULT_MANA_REGEN_PER_TICK;
    }

    /**
     * Получает данные маны для конкретного игрока
     */
    public static MagicData get(Player player) {
        if (player == null) {
            return null;
        }
        return player.getData(MagicEvents.MAGIC_DATA_ATTACHMENTS.get());
    }

    // ===== УПРАВЛЕНИЕ МАНОЙ =====

    /**
     * Получает текущее количество маны
     */
    public float getCurrentMana() {
        return currentMana;
    }

    /**
     * Устанавливает текущее количество маны (с проверкой лимитов)
     */
    public void setCurrentMana(float mana) {
        this.currentMana = Math.max(0, Math.min(mana, maxMana));
    }

    /**
     * Получает максимальное количество маны
     */
    public float getMaxMana() {
        return maxMana;
    }

    /**
     * Устанавливает максимальное количество маны
     */
    public void setMaxMana(float maxMana) {
        this.maxMana = Math.max(1, maxMana);
        // Если текущая мана превышает новый максимум, уменьшаем её
        if (this.currentMana > this.maxMana) {
            this.currentMana = this.maxMana;
        }
    }

    /**
     * Получает скорость восстановления маны (единиц в тик)
     */
    public float getManaRegenRate() {
        return manaRegenRate;
    }

    /**
     * Устанавливает скорость восстановления маны
     */
    public void setManaRegenRate(float rate) {
        this.manaRegenRate = Math.max(0, rate);
    }

    /**
     * Добавляет ману
     */
    public void addMana(float amount) {
        setCurrentMana(currentMana + amount);
    }

    /**
     * Тратит ману (для использования способностей)
     * Возвращает true если было достаточно маны
     */
    public boolean spendMana(float amount) {
        if (currentMana >= amount) {
            setCurrentMana(currentMana - amount);
            return true;
        }
        return false;
    }

    /**
     * Проверяет есть ли достаточно маны (без траты)
     */
    public boolean hasMana(float amount) {
        return currentMana >= amount;
    }

    // ===== УПРАВЛЕНИЕ СПОСОБНОСТЯМИ =====

    /**
     * Получает активен ли Sharingan
     */
    public boolean isSharinganActive() {
        return sharinganActive;
    }

    /**
     * Активирует Sharingan
     */
    public void activateSharingan() {
        // Требует 50 единиц маны (параметр для конфига)
        float sharinganCost = 50.0f;
        if (spendMana(sharinganCost)) {
            sharinganActive = true;
            sharinganCooldown = 0;
        }
    }

    /**
     * Деактивирует Sharingan
     */
    public void deactivateSharingan() {
        sharinganActive = false;
    }

    /**
     * Получает кулдаун Sharingan (в тиках)
     */
    public int getSharinganCooldown() {
        return sharinganCooldown;
    }

    /**
     * Обновляется каждый тик
     */
    public void tick() {
        // Восстанавливаем ману если она не максимальная
        if (currentMana < maxMana) {
            setCurrentMana(currentMana + manaRegenRate);
        }

        // Обновляем кулдауны
        if (sharinganCooldown > 0) {
            sharinganCooldown--;
        }

        // Деактивируем Sharingan после определённого времени (20 * 60 тиков = 60 секунд)
        if (sharinganActive && sharinganCooldown > 1200) {
            deactivateSharingan();
        }
    }

    // ===== СОХРАНЕНИЕ И ЗАГРУЗКА =====

    /**
     * Сохраняет данные маны в NBT (для сохранений и синхронизации)
     */
    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putFloat("CurrentMana", currentMana);
        tag.putFloat("MaxMana", maxMana);
        tag.putFloat("ManaRegenRate", manaRegenRate);
        tag.putBoolean("SharinganActive", sharinganActive);
        tag.putInt("SharinganCooldown", sharinganCooldown);
        return tag;
    }

    /**
     * Загружает данные маны из NBT
     */
    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (nbt.contains("CurrentMana", Tag.TAG_FLOAT)) {
            currentMana = nbt.getFloat("CurrentMana");
        }
        if (nbt.contains("MaxMana", Tag.TAG_FLOAT)) {
            maxMana = nbt.getFloat("MaxMana");
        }
        if (nbt.contains("ManaRegenRate", Tag.TAG_FLOAT)) {
            manaRegenRate = nbt.getFloat("ManaRegenRate");
        }
        if (nbt.contains("SharinganActive", Tag.TAG_BYTE)) {
            sharinganActive = nbt.getBoolean("SharinganActive");
        }
        if (nbt.contains("SharinganCooldown", Tag.TAG_INT)) {
            sharinganCooldown = nbt.getInt("SharinganCooldown");
        }
    }

    /**
     * Сбрасывает все данные на начальные значения (при смерти)
     */
    public void reset() {
        this.currentMana = DEFAULT_CURRENT_MANA;
        this.sharinganActive = false;
        this.sharinganCooldown = 0;
    }
}