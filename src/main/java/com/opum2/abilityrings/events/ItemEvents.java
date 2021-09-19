package com.opum2.abilityrings.events;

import com.opum2.abilityrings.AbilityRings;
import com.opum2.abilityrings.ModInfo;
import com.opum2.abilityrings.items.rings.FlightRingItem;
import com.opum2.abilityrings.items.rings.SwiftnessRingItem;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ModInfo.MOD_ID)
public class ItemEvents {
    @SubscribeEvent
    public static void onItemToss(ItemTossEvent event) {
        Player player = event.getPlayer();
        ItemEntity entityItem = event.getEntityItem();

        if (entityItem != null) {
            ItemStack stack = event.getEntityItem().getItem();
            Item item = stack.getItem();

            if (stack.hasTag() && item instanceof FlightRingItem) {
                ((FlightRingItem) item).stopFlying(player);

                if (!player.level.isClientSide()) {
                    ServerPlayer playerMP = (ServerPlayer) player;

                    if (playerMP.getAbilities().flying) {
                        playerMP.getAbilities().flying = false;
                    }

                    if (playerMP.getAbilities().mayfly) {
                        playerMP.getAbilities().mayfly = false;
                    }
                }

                AbilityRings.LOGGER.info("ItemTossEvent - getAbilities()::flying = " + player.getAbilities().flying);
                AbilityRings.LOGGER.info("ItemTossEvent - getAbilities()::mayfly = " + player.getAbilities().mayfly);
            } else if (item instanceof SwiftnessRingItem) {
                SwiftnessRingItem ring = (SwiftnessRingItem)item;
                player.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(ring.movementSpeedModifier);
            }
        }
    }
}
