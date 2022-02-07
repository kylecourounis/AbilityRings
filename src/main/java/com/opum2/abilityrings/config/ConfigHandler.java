package com.opum2.abilityrings.config;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class ConfigHandler {
    public static class Common {
        public final FlightRingProperties flightRingProperties;

        Common(final ForgeConfigSpec.Builder builder) {
            this.flightRingProperties = new FlightRingProperties(builder);

            builder.pop();
        }
    }

    public static class FlightRingProperties {
        public final ConfigValue<String> consumptionItem;
        public final IntValue consumeTicks;
        public final IntValue maxStorage;
        
        FlightRingProperties(final ForgeConfigSpec.Builder builder) {
            builder.push("items.flightringproperties");

            this.consumptionItem = builder.comment("The item that the Ring of Flight will consume every specified number of ticks.").define("consumptionItem", "minecraft:feather");
            this.consumeTicks = builder.comment("The number of ticks that will pass between the Ring of Flight consuming the specified item.\nThere are twenty ticks in one second.").defineInRange("consumeTicks", 60, 20, Integer.MAX_VALUE);
            this.maxStorage = builder.comment("The maximum number of the item to be consumed that can be held in the Ring of Flight.").defineInRange("maxStorage", 5000, 1, Integer.MAX_VALUE);
            
            builder.pop();
        }

        public ResourceLocation getFlightRingItem() {
            String[] name = this.consumptionItem.get().split(":");
            return new ResourceLocation(name[0], name[1]);
        }
    }

    private static final ForgeConfigSpec COMMON_SPEC;
    public static final Common COMMON;

    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }

    public static void register(final ModLoadingContext context) {
        context.registerConfig(ModConfig.Type.COMMON, COMMON_SPEC);
    }
}
