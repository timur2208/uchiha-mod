package com.uchiha.uchiha.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import com.uchiha.uchiha.magic.PlayerManaData;

public class ManaUpdatePacket implements CustomPacketPayload {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("uchiha", "mana_update");
    public static final StreamCodec<FriendlyByteBuf, ManaUpdatePacket> CODEC = StreamCodec.composite(
            FriendlyByteBuf::readFloat,
            packet -> packet.mana,
            ManaUpdatePacket::new
    );

    public final float mana;

    public ManaUpdatePacket(float mana) {
        this.mana = mana;
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    public static void handle(ManaUpdatePacket payload, PlayPayloadContext context) {
        context.workHandler().submitAsync(() -> {
            var player = context.player();
            if (player != null) {
                player.getPersistentData().putFloat(PlayerManaData.MANA_KEY, payload.mana);
            }
        });
    }
}
