package com.opum2.abilityrings.init;

import com.opum2.abilityrings.ModInfo;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeTab {
    public static final CreativeModeTab MOD_TAB = new CreativeModeTab(ModInfo.MOD_ID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.RING_BASE.get());
        }
    };
}
