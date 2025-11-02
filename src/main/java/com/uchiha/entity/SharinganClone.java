package com.uchiha.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.sync.EntityDataAccessor;
import net.minecraft.network.sync.EntityDataSerializers;

import java.util.UUID;

public class SharinganClone extends LivingEntity {
    private static final EntityDataAccessor<String> OWNER_UUID = 
        EntityDataManager.defineId(SharinganClone.class, EntityDataSerializers.STRING);

    private int lifetime = 0;
    private int maxLifetime = 200;

    public SharinganClone(EntityType<?> type, Level level) {
        super(type, level);
    }

    public SharinganClone(Level level, Player owner, int lifetime) {
        super(EntityRegistry.SHARINGAN_CLONE.get(), level);
        this.setPos(owner.position());
        this.entityData.set(OWNER_UUID, owner.getStringUUID());
        this.maxLifetime = lifetime;
        this.lifetime = 0;
    }

    @Override
    public void tick() {
        super.tick();
        lifetime++;
        
        if (lifetime >= maxLifetime) {
            this.discard();
        }

        // Clone movement logic - follow owner
        UUID ownerUUID = UUID.fromString(this.entityData.get(OWNER_UUID));
        Player owner = this.level().getPlayerByUUID(ownerUUID);
        
        if (owner != null) {
            this.setPos(owner.position());
            this.setDeltaMovement(owner.getDeltaMovement());
        }
    }

    @Override
    protected void defineSyncedData() {
        super.defineSyncedData();
        this.entityData.define(OWNER_UUID, "");
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putString("OwnerUUID", this.entityData.get(OWNER_UUID));
        tag.putInt("Lifetime", this.lifetime);
        tag.putInt("MaxLifetime", this.maxLifetime);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("OwnerUUID")) {
            this.entityData.set(OWNER_UUID, tag.getString("OwnerUUID"));
        }
        this.lifetime = tag.getInt("Lifetime");
        this.maxLifetime = tag.getInt("MaxLifetime");
    }
}
