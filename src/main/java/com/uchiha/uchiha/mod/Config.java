package com.uchiha.uchiha.mod;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.fml.config.ConfigSpec;

public class Config {
    public static final ConfigSpec SPEC;
    public static final ConfigSpec.BooleanValue LOG_DIRT_BLOCK;
    public static final ConfigSpec.IntValue MAGIC_NUMBER;
    public static final ConfigSpec.ConfigValue<String> MAGIC_NUMBER_INTRODUCTION;
    public static final ConfigSpec.ConfigValue<java.util.List<String>> ITEM_STRINGS;

    static {
        var builder = new ConfigSpec.Builder();

        LOG_DIRT_BLOCK = builder
                .comment("Log dirt block creation")
                .define("log_dirt_block", true);

        MAGIC_NUMBER = builder
                .comment("The magic number")
                .defineInRange("magic_number", 42, 0, Integer.MAX_VALUE);

        MAGIC_NUMBER_INTRODUCTION = builder
                .comment("Magic number intro")
                .define("magic_number_introduction", "Magic Number:");

        ITEM_STRINGS = builder
                .comment("List of item strings")
                .define("item_strings", java.util.List.of("ItemA", "ItemB", "ItemC"));

        SPEC = builder.build();
    }
}
