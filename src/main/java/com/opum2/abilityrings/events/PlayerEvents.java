package com.opum2.abilityrings.events;

import com.opum2.abilityrings.ModInfo;
import com.opum2.abilityrings.init.ModItems;
import com.opum2.abilityrings.items.rings.SwiftnessRingItem;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ModInfo.MOD_ID)
public class PlayerEvents {
	@SubscribeEvent
	public static void onLivingJump(LivingEvent.LivingJumpEvent event) {
		LivingEntity entity = event.getEntityLiving();

		if (entity instanceof Player) {
			Player player = (Player)entity;
			SwiftnessRingItem item = (SwiftnessRingItem)ModItems.SWIFTNESS_RING.get();
			
			if (Inventory.isHotbarSlot(player.getInventory().findSlotMatchingItem(item.getDefaultInstance()))) {
				if (!player.isCrouching()) {
					player.lerpMotion(player.getDeltaMovement().x, player.getDeltaMovement().y + 0.35D, player.getDeltaMovement().z);
				}
			}
		}
	}
}
