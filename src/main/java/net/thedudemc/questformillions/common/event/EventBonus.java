package net.thedudemc.questformillions.common.event;

import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;
import net.thedudemc.questformillions.QuestForMillions;
import net.thedudemc.questformillions.common.util.Config;

@Mod.EventBusSubscriber(modid = QuestForMillions.MODID)
public class EventBonus {

	@SubscribeEvent
	public static void onWorldTick(WorldTickEvent event) {
		if (event.phase == Phase.END) {
			if (event.world.getWorldTime() % 20 == 0) {
				long worldTime = event.world.getWorldTime();
				int currentTime = (int) worldTime % 24000;
				int dayOfWeek = ((int) worldTime / 24000) % 7;
				if (dayOfWeek == 0 && currentTime == 2000) {
					event.world.playerEntities.forEach(p -> p.sendMessage(new TextComponentString("Triggering Bonus! Chance for payout: " + Config.bonus_chanceForBonus + "%")));
					if (event.world.rand.nextInt(100) < Config.bonus_chanceForBonus) {
						event.world.playerEntities.forEach(p -> p.sendMessage(new TextComponentString("Payout Occurred!")));
					} else {
						event.world.playerEntities.forEach(p -> p.sendMessage(new TextComponentString("Payout did not occur!")));
					}
				}
			}
		}
	}

}
