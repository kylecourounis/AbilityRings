package com.opum2.abilityrings;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.opum2.abilityrings.config.ConfigHandler;
import com.opum2.abilityrings.events.PlayerEvents;
import com.opum2.abilityrings.init.ModItems;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.theillusivec4.curios.api.SlotTypeMessage;

@Mod(ModInfo.MOD_ID)
public class AbilityRings {
    public static final Logger LOGGER = LogManager.getLogger();

    public AbilityRings() {
        ConfigHandler.register(ModLoadingContext.get());

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.init(bus);

        bus.addListener(this::commonSetup);
        bus.addListener(this::clientSetup);
        
        bus.addListener(this::interModEnqueue);        
    }

    @OnlyIn(Dist.CLIENT)
    private void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(new Runnable() {
            public void run() {
                ModItems.registerItemProperties();
            }
        });
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    	MinecraftForge.EVENT_BUS.register(new PlayerEvents());
    }
    
    private void interModEnqueue(final InterModEnqueueEvent event) {
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("ring").size(2).build());
    }
}
