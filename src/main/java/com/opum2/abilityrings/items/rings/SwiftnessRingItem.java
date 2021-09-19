package com.opum2.abilityrings.items.rings;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.opum2.abilityrings.items.ModRingItem;
import com.opum2.abilityrings.util.AbilityRingsLang;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class SwiftnessRingItem extends ModRingItem {
    private static final Random RANDOM = new Random();

    public final AttributeModifier movementSpeedModifier;

    public SwiftnessRingItem() {
        super();

        this.setLocalizedName("Ring of Swiftness");
        this.movementSpeedModifier = new AttributeModifier("SwiftnessRingSpeedModifier", 0.35D, Operation.MULTIPLY_TOTAL);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean isHeldItem) {
        if (entity instanceof Player) {
            Player player = (Player)entity;

            AttributeInstance movementSpeed = player.getAttribute(Attributes.MOVEMENT_SPEED);

            if (!Inventory.isHotbarSlot(slot)) {
                movementSpeed.removeModifier(this.movementSpeedModifier);
            } else {
                if (!movementSpeed.hasModifier(this.movementSpeedModifier)) {
                    movementSpeed.addTransientModifier(this.movementSpeedModifier);
                }

                if (player.fallDistance >= player.getMaxFallDistance()) {
                    player.fallDistance = 0.0F;

                    if (world.isClientSide()) {
                        for (int i = 0; i < 3; i++) {
                            world.addParticle(ParticleTypes.CLOUD, entity.getX(), entity.getY() - 2.0D, entity.getZ(), (double)((RANDOM.nextFloat() - 0.5F) / 2.0F), -0.5D, (double)((RANDOM.nextFloat() - 0.5F) / 2.0F));
                        }
                    }
                }

                if (entity.isSprinting() && world.isClientSide()) {
                    world.addParticle(ParticleTypes.CLOUD, entity.getX(), entity.getY() - 1.5D, entity.getZ(), (double)((RANDOM.nextFloat() - 0.5F) / 2.0F), 0.1D, (double)((RANDOM.nextFloat() - 0.5F) / 2.0F));
                }

                if (!player.isOnGround()) {
                    player.yo += 0.028F;
                }

                if (player.horizontalCollision) {
                    player.maxUpStep = 1.0F;
                } else {
                    player.maxUpStep = 0.5F;
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
        if (Screen.hasShiftDown()) {
            list.add(new TranslatableComponent(AbilityRingsLang.ITEM_TOOLTIP_SPEED_RING.getTranslationKey()));
        } else {
            list.add(new TranslatableComponent(AbilityRingsLang.ITEM_TOOLTIP_HOLD_SHIFT.getTranslationKey()));
        }
    }
}
