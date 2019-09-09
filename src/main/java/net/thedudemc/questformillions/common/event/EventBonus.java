package net.thedudemc.questformillions.common.event;

import java.util.Collections;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;
import net.thedudemc.questformillions.QuestForMillions;
import net.thedudemc.questformillions.common.storage.million.IMillion;
import net.thedudemc.questformillions.common.storage.million.MillionPlayer;
import net.thedudemc.questformillions.common.storage.million.MillionProvider;
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
						IMillion cap = event.world.getCapability(MillionProvider.MILLION_CAP, null);
						int total = 0;
						List<MillionPlayer> players = cap.getPlayers();
						List<EntityPlayer> onlinePlayers = event.world.playerEntities;
						Collections.sort(players);
						for (MillionPlayer player : players) {
							total += player.getAmount();
						}

						double initial = Config.bonus_initialPercentage;
						double decrement = initial / (double) players.size();
						int i = 0;
						for (double percent = initial; percent > 0D; percent -= decrement) {
							if (i < players.size()) {
								MillionPlayer player = players.get(i++);
								int toPay = (int) ((double) total * ((double) percent / 100D));
								player.addAmount(toPay);
								for (EntityPlayer onlinePlayer : onlinePlayers) {
									onlinePlayer.sendMessage(new TextComponentString(
											"Paid " + TextFormatting.YELLOW + player.getName() + TextFormatting.RESET + " a bonus of " + TextFormatting.AQUA + toPay + TextFormatting.RESET + " diamonds at a rate of " + percent + "%!"));
								}
							}
						}

					} else {
						event.world.playerEntities.forEach(p -> p.sendMessage(new TextComponentString("Payout did not occur!")));
					}
				}
			}
		}
	}

}
