package com.uchiha.uchiha.mod;

import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.bus.api.SubscribeEvent;

public class GatherDataHandler {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        event.getGenerator().addProvider(
                event.includeServer(),
                new UchihaCuriosDataProvider(
                        event.getGenerator().getPackOutput(),
                        event.getExistingFileHelper(),
                        event.getLookupProvider()
                )
        );
    }
}
