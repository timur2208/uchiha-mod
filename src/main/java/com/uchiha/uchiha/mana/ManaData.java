package com.uchiha.uchiha.mana;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.neoforged.neoforge.common.util.INBTSerializable;

public class ManaData implements INBTSerializable<CompoundTag> {
    private float mana;
    private float maxMana;

    public ManaData() {
        this.mana = 0;
        this.maxMana = 200;
    }

    public float getMana() {
        return mana;
    }

    public void setMana(float mana) {
        this.mana = Math.max(0, Math.min(mana, maxMana));
    }

    public float getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(float maxMana) {
        this.maxMana = Math.max(1, maxMana);
    }

    public void addMana(float amount) {
        setMana(mana + amount);
    }

    public boolean spendMana(float amount) {
        if (mana >= amount) {
            setMana(mana - amount);
            return true;
        }
        return false;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putFloat("mana", mana);
        tag.putFloat("maxMana", maxMana);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        mana = nbt.getFloat("mana");
        maxMana = nbt.getFloat("maxMana");
    }
}
