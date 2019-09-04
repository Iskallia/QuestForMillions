package net.thedudemc.questformillions.common.event;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
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
				int age = storm.getAge();
				if (age < duration) {
					if (age % 2 != 0) {
						storm.spawnRainItem();
					}
					if (age % 100 == 0) {
						storm.setLava(storm.getRandomForLava());
					}
					storm.setAge(age + 1);
				} else {
					storms.remove(i);
				}
			}
		}
	}

}
