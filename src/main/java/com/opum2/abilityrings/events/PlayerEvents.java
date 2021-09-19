package com.opum2.abilityrings.events;

import com.opum2.abilityrings.ModInfo;
import com.opum2.abilityrings.init.ModItems;
import com.opum2.abilityrings.items.rings.SwiftnessRingItem;

import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
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
					if (!player.swinging || !player.isSprinting()) {
						player.lerpMotion(player.getDeltaMovement().x, player.getDeltaMovement().y + 0.35D, player.getDeltaMovement().z);
					}
					
					if (player.isSprinting()) {
					    Direction direction = Direction.UP;
						float f = player.getRandom().nextFloat() * 0.2F + 0.1F;
						float f1 = (float) direction.getAxisDirection().getStep();
						Vec3 vec31 = player.getDeltaMovement().scale(1D);
						if (direction.getAxis() == Direction.Axis.X) {
							player.setDeltaMovement((double) (f1 * f), vec31.y + 0.35D, vec31.z);
						} else if (direction.getAxis() == Direction.Axis.Y) {
							player.setDeltaMovement(vec31.x, (double) (f1 * f) + 0.35D, vec31.z);
						} else if (direction.getAxis() == Direction.Axis.Z) {
							player.setDeltaMovement(vec31.x, vec31.y + 0.35D, (double) (f1 * f));
						}
						// player.lerpMotion(player.getDeltaMovement().x(), player.getDeltaMovement().y + 0.35D, player.getZ(0.75D));
					}
				}
			}
		}
	}
}
