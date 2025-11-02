package com.uchiha.skill.impl;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import com.uchiha.capability.UchihaCapabilities;
import com.uchiha.config.UchihaConfig;
import com.uchiha.skill.Ability;

public class Genjutsu implements Ability {
    private final Player player;
    private static final int INVISIBILITY_DURATION = 300; // 15 seconds

    public Genjutsu(Player player) {
        this.player = player;
    }

    @Override
    public void execute() {
        if (!canExecute()) return;

        var capability = UchihaCapabilities.getCapability(player);
        
        if (capability.getMana() < getManaCost()) {
            return;
        }

        capability.consumeMana(getManaCost());

        // Apply invisibility effect
        player.addEffect(new MobEffectInstance(
            MobEffects.INVISIBILITY,
            UchihaConfig.INVISIBILITY_DURATION.get(),
            0,
            false,
            false
        ));
    }

    @Override
    public int getManaCost() {
        return UchihaConfig.GENJUTSU_MANA_COST.get();
    }

    @Override
    public String getName() {
        return "Genjutsu";
    }

    @Override
    public boolean canExecute() {
        return !player.isSpectator() && player.isAlive();
    }
}
