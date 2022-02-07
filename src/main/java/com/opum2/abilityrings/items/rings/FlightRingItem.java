package com.opum2.abilityrings.items.rings;

import java.util.List;

import javax.annotation.Nullable;

import com.opum2.abilityrings.config.ConfigHandler;
import com.opum2.abilityrings.items.ModRingItem;
import com.opum2.abilityrings.util.AbilityRingsLang;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

public class FlightRingItem extends ModRingItem {
    private static final String CONSUMPTION_ITEM_TAG = "ConsumptionItem";

    private boolean flying;
    private boolean consuming;

    private int tick;
    private int consumptionItem; // Used as fuel - consumes one every 100 ticks (five seconds)

    public FlightRingItem() {
        super();

        this.setLocalizedName("Ring of Flight");
        this.consuming = false;
    }

    public boolean isFlying() {
        return this.flying;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean isHeldItem) {        
        if (entity instanceof Player) {
            Player player = (Player)entity;
            
            if (!player.isCreative()) {
                if (player.getInventory().contains(stack)) {
                    CompoundTag tag = stack.getOrCreateTag();
                    this.consumptionItem = tag.getInt(CONSUMPTION_ITEM_TAG);
                    
                    if (!Inventory.isHotbarSlot(slot)) {
                        this.stopFlying(player);
                    } else {
                        this.flying = player.getAbilities().flying;

                        Item item = ForgeRegistries.ITEMS.getValue(ConfigHandler.COMMON.flightRingProperties.getFlightRingItem());

                        if (this.consuming) {
                            if (player.getInventory().contains(item.getDefaultInstance()) && this.consumptionItem < ConfigHandler.COMMON.flightRingProperties.maxStorage.get()) {
                                player.getInventory().removeItem(player.getInventory().findSlotMatchingItem(item.getDefaultInstance()), 1);
                                this.consumptionItem++;
                            }
                        }

                        if (this.consumptionItem > 0) {
                            if (!player.getAbilities().mayfly) {
                                player.getAbilities().mayfly = true;
                            }

                            if (this.isFlying()) {
                                if (!world.isClientSide()) {
                                    if (this.tick == ConfigHandler.COMMON.flightRingProperties.consumeTicks.get()) {
                                        this.tick = 0;
                                        this.consumptionItem--;
                                    }

                                    this.tick++;
                                }
                            } else {
                                if (!world.isClientSide()) {
                                    this.tick = 0;
                                }
                            }
                        } else {
                            this.stopFlying(player);
                        }
                    }

                    if (!world.isClientSide()) {
                        tag.putInt(CONSUMPTION_ITEM_TAG, this.consumptionItem);
                    }
                }
            } else {
                player.getAbilities().mayfly = true;
            }
        }
    }

    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (world.isClientSide()) {
            if (player.isCrouching()) {
                if (this.consuming) {
                    this.consuming = false;
                } else {
                    this.consuming = true;
                }

                return InteractionResultHolder.pass(stack);
            }
        }

        return InteractionResultHolder.pass(stack);
    }

    public void stopFlying(Player player) {
        if (player.getAbilities().flying) {
            player.getAbilities().flying = false;
        }

        if (this.flying) {
            this.flying = false;
        }

        if (!player.isCreative()) {
            player.getAbilities().mayfly = false;
        }
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return this.consuming;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
        CompoundTag tag = stack.getOrCreateTag();

        String consumptionItemName = ForgeRegistries.ITEMS.getValue(ConfigHandler.COMMON.flightRingProperties.getFlightRingItem()).getDefaultInstance().getDisplayName().getString().replace("[", "").replace("]", "").trim();

        if (Screen.hasShiftDown()) {
            list.add(new TranslatableComponent(AbilityRingsLang.ITEM_EXTENDED_TOOLTIP_FLIGHT_RING.getTranslationKey()));
        } else {
            list.add(new TextComponent(consumptionItemName + ": " + ChatFormatting.YELLOW + tag.getInt(CONSUMPTION_ITEM_TAG)));
            list.add(new TranslatableComponent(AbilityRingsLang.ITEM_TOOLTIP_HOLD_SHIFT.getTranslationKey()));
        }
    }
}
