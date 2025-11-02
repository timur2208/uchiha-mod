package com.uchiha.capability;

import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.capabilities.Capability;
import net.neoforged.neoforge.common.capabilities.CapabilityManager;
import net.neoforged.neoforge.common.capabilities.CapabilityToken;
import net.neoforged.neoforge.event.AttachCapabilitiesEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.minecraft.resources.ResourceLocation;
import com.uchiha.UchihaModMain;

@Mod.EventBusSubscriber(modid = UchihaModMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class UchihaCapabilities {
    public static final Capability<UchihaCapability> UCHIHA_CAPABILITY = 
        CapabilityManager.get(new CapabilityToken<UchihaCapability>() {});

    public static final ResourceLocation UCHIHA_CAP_ID = 
        new ResourceLocation(UchihaModMain.MOD_ID, "uchiha_cap");

    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<Player> event) {
        if (!event.getObject().getCapability(UCHIHA_CAPABILITY).isPresent()) {
            event.addCapability(UCHIHA_CAP_ID, new UchihaCapabilityProvider());
        }
    }

    public static UchihaCapability getCapability(Player player) {
        return player.getCapability(UCHIHA_CAPABILITY)
            .orElseThrow(() -> new RuntimeException("Uchiha capability not found!"));
    }
}
