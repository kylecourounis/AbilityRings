package com.opum2.abilityrings.init;

import com.opum2.abilityrings.ModInfo;
import com.opum2.abilityrings.data.AbilityRingsItemModelProvider;
import com.opum2.abilityrings.data.AbilityRingsLangProvider;
import com.opum2.abilityrings.data.AbilityRingsRecipeProvider;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = ModInfo.MOD_ID, bus = Bus.MOD)
public class ModDataProviders {
    @SubscribeEvent
    public static void registerDataProviders(final GatherDataEvent event) {
        final DataGenerator dataGenerator = event.getGenerator();
        final ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        if (event.includeClient()) {
            dataGenerator.addProvider(new AbilityRingsLangProvider(dataGenerator));

            final AbilityRingsItemModelProvider itemModelProvider = new AbilityRingsItemModelProvider(dataGenerator, existingFileHelper);
            dataGenerator.addProvider(itemModelProvider);
        }
        
        if (event.includeServer()) {
            dataGenerator.addProvider(new AbilityRingsRecipeProvider(dataGenerator));
        }
    }
}