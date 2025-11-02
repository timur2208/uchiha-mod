package com.uchiha.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.capabilities.Capability;
import net.neoforged.neoforge.common.capabilities.ICapabilityProvider;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class UchihaCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    private final UchihaCapability capability = new UchihaCapability();

    @Override
    public @NotNull <T> net.neoforged.neoforge.common.capabilities.LazyOptional<T> getCapability(
            @NotNull Capability<T> cap, @Nullable Direction side) {
        // Return the capability if it matches
        if (cap == UchihaCapabilities.UCHIHA_CAPABILITY) {
            return net.neoforged.neoforge.common.capabilities.LazyOptional.of(() -> (T) capability);
        }
        return net.neoforged.neoforge.common.capabilities.LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        return capability.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        capability.deserializeNBT(nbt);
    }

    public UchihaCapability getCapability() {
        return capability;
    }
}
