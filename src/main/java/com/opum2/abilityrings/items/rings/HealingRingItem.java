package com.opum2.abilityrings.items.rings;

import java.util.List;

import javax.annotation.Nullable;

import com.opum2.abilityrings.items.ModRingItem;
import com.opum2.abilityrings.util.AbilityRingsLang;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class HealingRingItem extends ModRingItem {
    public HealingRingItem() {
        super();

        this.setLocalizedName("Ring of Healing");
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean isHeldItem) {
        if (entity instanceof Player) {
            Player player = (Player)entity;

            if (player.getInventory().contains(stack)) {
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100));
                player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100));
            } else {
                player.removeEffect(MobEffects.SATURATION);
                player.removeEffect(MobEffects.ABSORPTION);
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
        if (Screen.hasShiftDown()) {
            list.add(new TranslatableComponent(AbilityRingsLang.ITEM_TOOLTIP_HEALING_RING.getTranslationKey()));
        } else {
            list.add(new TranslatableComponent(AbilityRingsLang.ITEM_TOOLTIP_HOLD_SHIFT.getTranslationKey()));
        }
    }
}
