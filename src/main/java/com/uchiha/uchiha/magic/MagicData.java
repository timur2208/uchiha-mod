package com.uchiha.uchiha.magic;

public class MagicData {
    private int mana;
    private int maxMana;

    public MagicData(int maxMana) {
        this.maxMana = maxMana;
        this.mana = maxMana;
    }

    public int getMana() { return mana; }
    public int getMaxMana() { return maxMana; }
    public void setMana(int mana) { this.mana = Math.max(0, Math.min(mana, maxMana)); }
    public void regenMana(int amount) { setMana(this.mana + amount); }
    public boolean spendMana(int amount) {
        if (mana >= amount) {
            mana -= amount;
            return true;
        }
        return false;
    }
}
