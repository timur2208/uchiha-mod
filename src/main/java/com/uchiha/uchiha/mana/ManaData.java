package com.uchiha.uchiha.mana;

public class ManaData {
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
}
