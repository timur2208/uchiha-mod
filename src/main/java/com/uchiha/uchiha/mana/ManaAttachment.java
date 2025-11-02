package com.uchiha.uchiha.mana;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ManaAttachment {
    public static final AttachmentType<ManaData> MANA = NeoForgeRegistries.ATTACHMENT_TYPES.register(
            "mana", () -> new AttachmentType<>(() -> new ManaData())
    );

    public static ManaData getMana(Player player) {
        return player.getData(MANA);
    }
}