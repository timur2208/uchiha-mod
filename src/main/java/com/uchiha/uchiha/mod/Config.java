package com.uchiha.uchiha.mod;

import java.util.List;

public class Config {
    public static final IConfigSpec SPEC = new DummyConfigSpec();
    public static final DummyConfigValue LOG_DIRT_BLOCK = new DummyConfigValue();
    public static final DummyConfigValue MAGIC_NUMBER_INTRODUCTION = new DummyConfigValue();
    public static final DummyConfigValue MAGIC_NUMBER = new DummyConfigValue();
    public static final DummyConfigValue ITEM_STRINGS = new DummyConfigValue();

    public static class DummyConfigSpec implements IConfigSpec {}

    public interface IConfigSpec {} // Это просто "заглушка" интерфейса

    public static class DummyConfigValue {
        public boolean getAsBoolean() { return false; }
        public String get() { return ""; }
        public int getAsInt() { return 0; }
        public void forEach(java.util.function.Consumer<String> action) {
            for (String s : List.of("ItemA", "ItemB")) action.accept(s);
        }
    }
}
