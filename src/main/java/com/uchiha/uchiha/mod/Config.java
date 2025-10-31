package com.uchiha.uchiha.mod;

public class Config {
    public static final Object SPEC = null;
    public static final DummyConfigValue LOG_DIRT_BLOCK = new DummyConfigValue();
    public static final DummyConfigValue MAGIC_NUMBER_INTRODUCTION = new DummyConfigValue();
    public static final DummyConfigValue MAGIC_NUMBER = new DummyConfigValue();
    public static final DummyConfigValue ITEM_STRINGS = new DummyConfigValue();

    // Эмулируем методы get, getAsBoolean, getAsInt чтобы не было ошибки компиляции
    public static class DummyConfigValue {
        public boolean getAsBoolean() { return false; }
        public String get() { return ""; }
        public int getAsInt() { return 0; }
        public void forEach(java.util.function.Consumer<String> action) {}
    }
}
