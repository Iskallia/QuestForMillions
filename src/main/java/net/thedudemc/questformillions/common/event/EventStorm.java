package net.thedudemc.questformillions.common.event;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.thedudemc.questformillions.common.util.Config;
import net.thedudemc.questformillions.common.world.DiamondStorm;

public class EventStorm {

	public static List<DiamondStorm> storms = new ArrayList<DiamondStorm>();

	int duration = Config.totemOfRain_duration * 20;

	@SubscribeEvent
	public void onWorldTick(WorldTickEvent event) {
		if (event.side != Side.SERVER)
			return;
		if (event.phase == Phase.END) {
			for (int i = storms.size() - 1; i >= 0; i--) {
				DiamondStorm storm = storms.get(i);
				EntityPlayer summoner = storm.getSummoner();
				BlockPos location = summoner.getPosition();
				int age = storm.getAge();
				if (age < duration) {
					if (age % 2 != 0) {
						storm.spawnRainItem();
					}
					if (age % 100 == 0) {
						storm.setLava(storm.getRandomForLava());
						if (event.world.rand.nextInt(100) < 20) {
							EntityTNTPrimed tnt = new EntityTNTPrimed(event.world, location.getX(), location.getY() + 5, location.getZ(), summoner);
							tnt.setFuse(50);
							event.world.spawnEntity(tnt);
						}
					}
					storm.setAge(age + 1);
				} else {
					storms.remove(i);
				}
			}
		}
	}

	@SubscribeEvent
	public void onDeath(LivingDeathEvent event) {
		if (event.getEntity() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntity();
			cancelStorm(player);
		}
	}

	@SubscribeEvent
	public void onDimensionChange(PlayerChangedDimensionEvent event) {
		cancelStorm(event.player);
	}

	@SubscribeEvent
	public void onLeave(PlayerLoggedOutEvent event) {
		cancelStorm(event.player);
	}

	private boolean hasStorm(EntityPlayer player) {
		for (DiamondStorm storm : storms) {
			if (storm.getSummoner().getUniqueID().equals(player.getUniqueID())) {
				return true;
			}
		}
		return false;
	}

	private DiamondStorm getPlayerStorm(EntityPlayer player) {
		for (DiamondStorm storm : storms) {
			if (storm.getSummoner().getUniqueID().equals(player.getUniqueID())) {
				return storm;
			}
		}
		return null;
	}

	private void cancelStorm(EntityPlayer player) {
		System.out.println("Cancelling storm: " + player.getName());
		if (hasStorm(player)) {
			getPlayerStorm(player).cancel();
		}
	}

}
