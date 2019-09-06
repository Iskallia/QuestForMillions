package net.thedudemc.questformillions.common.event;

import com.feed_the_beast.ftblib.lib.data.FTBLibAPI;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.thedudemc.questformillions.QuestForMillions;
import net.thedudemc.questformillions.common.network.TotalItemsPacket;
import net.thedudemc.questformillions.common.storage.LootHandler;
import net.thedudemc.questformillions.common.storage.million.IMillion;
import net.thedudemc.questformillions.common.storage.million.MillionPlayer;
import net.thedudemc.questformillions.common.storage.million.MillionProvider;

@Mod.EventBusSubscriber(modid = QuestForMillions.MODID)
public class EventHandler {

	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent event) {
		if (event.side == Side.SERVER) {
			if (event.player.world.getTotalWorldTime() % 20 == 0) {
				EntityPlayer player = event.player;
				IMillion cap = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(0).getCapability(MillionProvider.MILLION_CAP, null);

				MillionPlayer mPlayer = cap.getPlayer(player.getUniqueID());
				if (mPlayer == null) {
					return;
				}

				String team = FTBLibAPI.getTeam(player.getUniqueID());
				if (!team.isEmpty()) {
					int amount = 0;
					for (MillionPlayer m : cap.getPlayers()) {
						if (FTBLibAPI.arePlayersInSameTeam(player.getUniqueID(), m.getUuid())) {
							amount += m.getAmount();
						}
					}
					QuestForMillions.PACKET.sendTo(new TotalItemsPacket(amount), (EntityPlayerMP) player);
				} else {
					QuestForMillions.PACKET.sendTo(new TotalItemsPacket(mPlayer.getAmount()), (EntityPlayerMP) player);
				}
			}
		}
	}

	// TODO: Remove this method
	@SubscribeEvent
	public static void onJoin(PlayerLoggedInEvent event) {

		event.player.inventory.addItemStackToInventory(LootHandler.getRandomLoot());

	}

}
