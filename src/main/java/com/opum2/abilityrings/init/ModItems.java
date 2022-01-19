package com.opum2.abilityrings.init;

import com.opum2.abilityrings.ModInfo;
import com.opum2.abilityrings.items.ModItem;
import com.opum2.abilityrings.items.rings.FlightRingItem;
import com.opum2.abilityrings.items.rings.FoodRingItem;
import com.opum2.abilityrings.items.rings.HealingRingItem;
import com.opum2.abilityrings.items.rings.SwiftnessRingItem;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {
    private static boolean initialized;

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ModInfo.MOD_ID);

    public static final RegistryObject<Item> RING_BASE = ITEMS.register("ring_base", () -> new ModItem("Base Ring", new Item.Properties()));

    public static final RegistryObject<Item> FLIGHT_RING = ITEMS.register("flight_ring", () -> new FlightRingItem());
    public static final RegistryObject<Item> SWIFTNESS_RING = ITEMS.register("swiftness_ring", () -> new SwiftnessRingItem());
    public static final RegistryObject<Item> FOOD_RING = ITEMS.register("food_ring", () -> new FoodRingItem());
    public static final RegistryObject<Item> HEALING_RING = ITEMS.register("healing_ring", () -> new HealingRingItem());
    
    public static final RegistryObject<Item> RED_ORB = ITEMS.register("red_orb", () -> new ModItem("Red Orb"));
    public static final RegistryObject<Item> BLUE_ORB = ITEMS.register("blue_orb", () -> new ModItem("Blue Orb"));

    public static void init(IEventBus bus) {
        if (ModItems.initialized) {
            return;
        }
        
        ModItems.ITEMS.register(bus);
        
        ModItems.initialized = true;
    }

    public static void registerItemProperties() {
        FlightRingItem flightRing = (FlightRingItem)ModItems.FLIGHT_RING.get();

        ItemProperties.register(flightRing, new ResourceLocation(ModInfo.MOD_ID, "flying"), (stack, world, livingEntity, i) -> {
            return ((FlightRingItem)stack.getItem()).isFlying() && stack.hasTag() ? 1.0F : 0.0F;
        });
    }
}
