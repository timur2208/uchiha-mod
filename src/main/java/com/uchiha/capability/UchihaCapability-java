package com.uchiha.capability;

import net.minecraft.nbt.CompoundTag;

public class UchihaCapability {
    private int mana = 0;
    private int maxMana = 1000;
    private int sharinganLevel = 0; // 0 = none, 1-4 = levels
    private int blindnessCounter = 0;
    private boolean hasUchiha = false;

    // Mana management
    public void addMana(int amount) {
        this.mana = Math.min(this.mana + amount, this.maxMana);
    }

    public void consumeMana(int amount) {
        this.mana = Math.max(this.mana - amount, 0);
    }

    public int getMana() {
        return this.mana;
    }

    public int getMaxMana() {
        return this.maxMana;
    }

    public void setMana(int amount) {
        this.mana = Math.min(Math.max(amount, 0), this.maxMana);
    }

    public void resetMana() {
        this.mana = 0;
    }

    // Sharingan level management
    public int getSharinganLevel() {
        return this.sharinganLevel;
    }

    public void setSharinganLevel(int level) {
        if (level >= 0 && level <= 4) {
            this.sharinganLevel = level;
        }
    }

    // Uchiha status
    public boolean hasUchiha() {
        return this.hasUchiha;
    }

    public void setUchiha(boolean value) {
        this.hasUchiha = value;
        if (!value) {
            this.sharinganLevel = 0;
            this.mana = 0;
        }
    }

    // Blindness counter for Tsukuyomi penalties
    public int getBlindnessCounter() {
        return this.blindnessCounter;
    }

    public void addBlindnessCounter() {
        this.blindnessCounter++;
    }

    public void resetBlindnessCounter() {
        this.blindnessCounter = 0;
    }

    // NBT serialization
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("Mana", this.mana);
        tag.putInt("MaxMana", this.maxMana);
        tag.putInt("SharinganLevel", this.sharinganLevel);
        tag.putInt("BlindnessCounter", this.blindnessCounter);
        tag.putBoolean("HasUchiha", this.hasUchiha);
        return tag;
    }

    public void deserializeNBT(CompoundTag tag) {
        this.mana = tag.getInt("Mana");
        this.maxMana = tag.getInt("MaxMana");
        this.sharinganLevel = tag.getInt("SharinganLevel");
        this.blindnessCounter = tag.getInt("BlindnessCounter");
        this.hasUchiha = tag.getBoolean("HasUchiha");
    }
}
