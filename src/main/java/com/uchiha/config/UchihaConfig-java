package com.uchiha.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class UchihaConfig {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;

    // Mana
    public static final ModConfigSpec.IntValue MAX_MANA;
    public static final ModConfigSpec.DoubleValue MANA_REGEN_RATE;

    // Sharingan Level 1
    public static final ModConfigSpec.IntValue LEVEL_1_MANA_DRAIN;
    public static final ModConfigSpec.IntValue LEVEL_1_DETECTION_RADIUS;
    public static final ModConfigSpec.DoubleValue LEVEL_1_EVASION_CHANCE;

    // Sharingan Level 2
    public static final ModConfigSpec.IntValue LEVEL_2_MANA_DRAIN;
    public static final ModConfigSpec.IntValue LEVEL_2_DETECTION_RADIUS;
    public static final ModConfigSpec.DoubleValue LEVEL_2_EVASION_CHANCE;

    // Sharingan Level 3
    public static final ModConfigSpec.IntValue LEVEL_3_MANA_DRAIN;
    public static final ModConfigSpec.IntValue LEVEL_3_DETECTION_RADIUS;
    public static final ModConfigSpec.DoubleValue LEVEL_3_EVASION_CHANCE;
    public static final ModConfigSpec.IntValue GENJUTSU_MANA_COST;
    public static final ModConfigSpec.IntValue CLONE_MANA_COST;

    // Sharingan Level 4 (Mangekyo)
    public static final ModConfigSpec.IntValue LEVEL_4_MANA_DRAIN;
    public static final ModConfigSpec.IntValue LEVEL_4_DETECTION_RADIUS;
    public static final ModConfigSpec.DoubleValue LEVEL_4_EVASION_CHANCE;
    public static final ModConfigSpec.IntValue KAMUI_MANA_COST;
    public static final ModConfigSpec.IntValue AMATERASU_MANA_DRAIN;
    public static final ModConfigSpec.IntValue TSUKUYOMI_MANA_COST;

    // Abilities duration
    public static final ModConfigSpec.IntValue INVISIBILITY_DURATION;
    public static final ModConfigSpec.IntValue CLONE_DURATION;
    public static final ModConfigSpec.IntValue TSUKUYOMI_REALM_DURATION;

    static {
        BUILDER.push("Mana");
        MAX_MANA = BUILDER.comment("Maximum mana for Uchiha").defineInRange("max_mana", 1000, 100, 10000);
        MANA_REGEN_RATE = BUILDER.comment("Mana regeneration rate per tick").defineInRange("mana_regen_rate", 0.5, 0.0, 10.0);
        BUILDER.pop();

        BUILDER.push("Sharingan_Level_1");
        LEVEL_1_MANA_DRAIN = BUILDER.comment("Mana drain per tick").defineInRange("mana_drain", 1, 0, 100);
        LEVEL_1_DETECTION_RADIUS = BUILDER.comment("Entity detection radius").defineInRange("detection_radius", 10, 1, 100);
        LEVEL_1_EVASION_CHANCE = BUILDER.comment("Evasion chance (0.0-1.0)").defineInRange("evasion_chance", 0.05, 0.0, 1.0);
        BUILDER.pop();

        BUILDER.push("Sharingan_Level_2");
        LEVEL_2_MANA_DRAIN = BUILDER.comment("Mana drain per tick").defineInRange("mana_drain", 2, 0, 100);
        LEVEL_2_DETECTION_RADIUS = BUILDER.comment("Entity detection radius").defineInRange("detection_radius", 15, 1, 100);
        LEVEL_2_EVASION_CHANCE = BUILDER.comment("Evasion chance (0.0-1.0)").defineInRange("evasion_chance", 0.10, 0.0, 1.0);
        BUILDER.pop();

        BUILDER.push("Sharingan_Level_3");
        LEVEL_3_MANA_DRAIN = BUILDER.comment("Mana drain per tick").defineInRange("mana_drain", 3, 0, 100);
        LEVEL_3_DETECTION_RADIUS = BUILDER.comment("Entity detection radius").defineInRange("detection_radius", 20, 1, 100);
        LEVEL_3_EVASION_CHANCE = BUILDER.comment("Evasion chance (0.0-1.0)").defineInRange("evasion_chance", 0.15, 0.0, 1.0);
        GENJUTSU_MANA_COST = BUILDER.comment("Mana cost for Genjutsu").defineInRange("genjutsu_cost", 50, 0, 1000);
        CLONE_MANA_COST = BUILDER.comment("Mana cost for Clone creation").defineInRange("clone_cost", 100, 0, 1000);
        BUILDER.pop();

        BUILDER.push("Sharingan_Level_4");
        LEVEL_4_MANA_DRAIN = BUILDER.comment("Mana drain per tick").defineInRange("mana_drain", 5, 0, 100);
        LEVEL_4_DETECTION_RADIUS = BUILDER.comment("Entity detection radius").defineInRange("detection_radius", 50, 1, 100);
        LEVEL_4_EVASION_CHANCE = BUILDER.comment("Evasion chance (0.0-1.0)").defineInRange("evasion_chance", 0.30, 0.0, 1.0);
        KAMUI_MANA_COST = BUILDER.comment("Mana cost for Kamui teleport").defineInRange("kamui_cost", 200, 0, 1000);
        AMATERASU_MANA_DRAIN = BUILDER.comment("Mana drain per tick for Amaterasu").defineInRange("amaterasu_drain", 10, 0, 100);
        TSUKUYOMI_MANA_COST = BUILDER.comment("Mana cost per target for Tsukuyomi").defineInRange("tsukuyomi_cost", 300, 0, 1000);
        BUILDER.pop();

        BUILDER.push("Abilities");
        INVISIBILITY_DURATION = BUILDER.comment("Invisibility duration in ticks").defineInRange("invisibility_duration", 300, 10, 6000);
        CLONE_DURATION = BUILDER.comment("Clone duration in ticks").defineInRange("clone_duration", 200, 10, 6000);
        TSUKUYOMI_REALM_DURATION = BUILDER.comment("Tsukuyomi Realm duration in ticks").defineInRange("tsukuyomi_realm_duration", 400, 10, 6000);
        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}
