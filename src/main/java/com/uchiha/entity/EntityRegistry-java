package com.uchiha.entity;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import com.uchiha.UchihaModMain;

public class EntityRegistry {
    private static final DeferredRegister<EntityType<?>> ENTITIES = 
        DeferredRegister.create(NeoForgeRegistries.ENTITY_TYPES, UchihaModMain.MOD_ID);

    public static final net.neoforged.neoforge.registries.DeferredHolder<EntityType<?>, EntityType<SharinganClone>> SHARINGAN_CLONE =
        ENTITIES.register("sharingan_clone", () ->
            EntityType.Builder.of(SharinganClone::new, MobCategory.CREATURE)
                .sized(0.6f, 1.8f)
                .build(new net.minecraft.resources.ResourceLocation(UchihaModMain.MOD_ID, "sharingan_clone").toString())
        );

    public static void register(IEventBus modEventBus) {
        ENTITIES.register(modEventBus);
    }
}
