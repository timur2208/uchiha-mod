package com.uchiha.uchiha.mod;

import top.theillusivec4.curios.api.CuriosDataProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.minecraft.core.HolderLookup;
import java.util.concurrent.CompletableFuture;

public class UchihaCuriosDataProvider extends CuriosDataProvider {
    public UchihaCuriosDataProvider(PackOutput output, ExistingFileHelper fileHelper, CompletableFuture<HolderLookup.Provider> registries) {
        super("uchiha", output, fileHelper, registries);
    }

    @Override
    public void generate(HolderLookup.Provider registries, ExistingFileHelper fileHelper) {
        this.createSlot("eye_book")
                .size(1);
        this.createEntities("uchiha_entities")
                .addPlayer()
                .addSlots("eye_book");
    }
}
