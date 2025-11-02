package com.uchiha.uchiha.mana;

import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import com.uchiha.uchiha.mod.uchiha;
import java.util.function.Supplier;

public class ManaAttachment {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, uchiha.MODID);

    public static final Supplier<AttachmentType<ManaData>> MANA = ATTACHMENT_TYPES.register(
            "mana", () -> AttachmentType.builder(ManaData::new).copyOnDeath().build()
    );

    public static ManaData getMana(Player player) {
        return player.getData(MANA.get());
    }
}
