package com.uchiha.uchiha.magic;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.util.INBTSerializable;

public class MagicData implements INBTSerializable<CompoundTag> {
    private float currentMana = 100.0f;
    private float maxMana = 200.0f;
    private static final float MANA_REGEN_PER_TICK = 0.5f;

    public static MagicData get(Player player) {
        return player != null ? player.getData(MagicEvents.MAGIC_DATA_ATTACHMENTS.get()) : null;
    }

    public float getCurrentMana() { return currentMana; }
    public void setCurrentMana(float mana) { this.currentMana = Math.max(0, Math.min(mana, maxMana)); }
    public float getMaxMana() { return maxMana; }
    public void setMaxMana(float maxMana) { this.maxMana = Math.max(1, maxMana); if (this.currentMana > this.maxMana) this.currentMana = this.maxMana; }
    public void addMana(float amount) { setCurrentMana(currentMana + amount); }
    public boolean spendMana(float amount) { if (currentMana >= amount) { setCurrentMana(currentMana - amount); return true; } return false; }
    public void tick() { if (currentMana < maxMana) setCurrentMana(currentMana + MANA_REGEN_PER_TICK); }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putFloat("CurrentMana", currentMana);
        tag.putFloat("MaxMana", maxMana);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (nbt.contains("CurrentMana", Tag.TAG_FLOAT)) currentMana = nbt.getFloat("CurrentMana");
        if (nbt.contains("MaxMana", Tag.TAG_FLOAT)) maxMana = nbt.getFloat("MaxMana");
    }
}