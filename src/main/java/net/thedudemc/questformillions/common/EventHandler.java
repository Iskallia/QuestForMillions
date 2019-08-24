package net.thedudemc.questformillions.common;

import com.feed_the_beast.ftblib.lib.data.FTBLibAPI;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.thedudemc.questformillions.QuestForMillions;
import net.thedudemc.questformillions.common.network.TotalItemsPacket;
import net.thedudemc.questformillions.common.storage.IMillion;
import net.thedudemc.questformillions.common.storage.MillionProvider;

@Mod.EventBusSubscriber(modid = QuestForMillions.MODID)
public class EventHandler {

	@SubscribeEvent
	public static void onPlayerClone(PlayerEvent.Clone event) {
		EntityPlayer player = event.getEntityPlayer();
		IMillion million = null;
		IMillion oldMillion = null;
		if (player.hasCapability(MillionProvider.MILLION_CAP, null)) {
			million = player.getCapability(MillionProvider.MILLION_CAP, null);
		}
		if (event.getOriginal().hasCapability(MillionProvider.MILLION_CAP, null)) {
			oldMillion = event.getOriginal().getCapability(MillionProvider.MILLION_CAP, null);
		}
		if (million != null && oldMillion != null) {
			million.setItems(oldMillion.getAmount());
		}
	}

	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent event) {
		if (event.side == Side.SERVER) {
			if (event.player.world.getTotalWorldTime() % 20 == 0) {
				EntityPlayerMP player = (EntityPlayerMP) event.player;
				String team = FTBLibAPI.getTeam(player.getUniqueID());
				if (team.isEmpty()) {
					if (player.hasCapability(MillionProvider.MILLION_CAP, null)) {
						IMillion million = player.getCapability(MillionProvider.MILLION_CAP, null);
						QuestForMillions.PACKET.sendTo(new TotalItemsPacket(million.getAmount()), player);
					}
				} else {
					int amount = 0;
					for (EntityPlayer p : FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld().playerEntities) {
						if (FTBLibAPI.arePlayersInSameTeam(player.getUniqueID(), p.getUniqueID())) {
							if (p.hasCapability(MillionProvider.MILLION_CAP, null)) {
								IMillion million = p.getCapability(MillionProvider.MILLION_CAP, null);
								amount += million.getAmount();
							}
						}
					}
					QuestForMillions.PACKET.sendTo(new TotalItemsPacket(amount), player);
				}
			}
		}
	}

}
