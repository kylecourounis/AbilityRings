package com.opum2.abilityrings.util;

import com.opum2.abilityrings.ModInfo;

import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;

public enum AbilityRingsLang {
    ITEM_TOOLTIP_HOLD_SHIFT("item", "hold_shift.tooltip"),
    ITEM_EXTENDED_TOOLTIP_FLIGHT_RING("item", "flight_ring.tooltip"),
    ITEM_TOOLTIP_SPEED_RING("item", "speed_ring.tooltip"), 
    ITEM_TOOLTIP_FOOD_RING("item", "food_ring.tooltip"),
    ITEM_TOOLTIP_HEALING_RING("item", "healing_ring.tooltip");

    private final String key;

    AbilityRingsLang(final String type, final String path) {
        this(Util.makeDescriptionId(type, new ResourceLocation(ModInfo.MOD_ID, path)));
    }

    AbilityRingsLang(final String key) {
        this.key = key;
    }

    public String getTranslationKey() {
        return key;
    }
}
