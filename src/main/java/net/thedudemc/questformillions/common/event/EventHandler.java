package net.thedudemc.questformillions.common.event;

import java.util.HashMap;

import com.feed_the_beast.ftblib.lib.data.FTBLibAPI;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;
import net.thedudemc.questformillions.QuestForMillions;
import net.thedudemc.questformillions.common.network.TotalItemsPacket;
import net.thedudemc.questformillions.common.storage.million.IMillion;
import net.thedudemc.questformillions.common.storage.million.MillionPlayer;
import net.thedudemc.questformillions.common.storage.million.MillionProvider;

@Mod.EventBusSubscriber(modid = QuestForMillions.MODID)
public class EventHandler {

	static MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
	static HashMap<String, Integer> players = new HashMap<String, Integer>();

	@SubscribeEvent
	public static void onWorldTick(WorldTickEvent event) {
		if (event.world.isRemote)
			return;
		if (event.world.getWorldTime() % 20 == 0) {
			IMillion cap = server.getWorld(0).getCapability(MillionProvider.MILLION_CAP, null);
			for (MillionPlayer player : cap.getPlayers()) {
				String team = FTBLibAPI.getTeam(player.getUuid());
				if (!team.isEmpty()) {
					int amount = 0;
					for (MillionPlayer m : cap.getPlayers()) {
						if (FTBLibAPI.arePlayersInSameTeam(player.getUuid(), m.getUuid())) {
							amount += m.getAmount();
						}
					}
					players.put(player.getName(), amount);
				} else {
					players.put(player.getName(), player.getAmount());
				}
				String playerEntries = "";
				EntityPlayer onlinePlayer = server.getPlayerList().getPlayerByUsername(player.getName());

				for (String p : players.keySet()) {
					playerEntries += p + ":" + players.get(p) + ",";
				}
				if (onlinePlayer != null)
					QuestForMillions.PACKET.sendTo(new TotalItemsPacket(playerEntries), (EntityPlayerMP) onlinePlayer);

			}
		}
	}
	//
	// @SubscribeEvent
	// public static void onPlayerTick(PlayerTickEvent event) {
	// if (event.side == Side.SERVER) {
	// if (event.player.world.getTotalWorldTime() % 20 == 0) {
	// EntityPlayer player = event.player;
	// IMillion cap = server.getWorld(0).getCapability(MillionProvider.MILLION_CAP,
	// null);
	//
	// MillionPlayer mPlayer = cap.getPlayer(player.getUniqueID());
	// if (mPlayer == null) {
	// return;
	// }
	// for (MillionPlayer mp : cap.getPlayers()) {
	// String team = FTBLibAPI.getTeam(player.getUniqueID());
	// if (!team.isEmpty()) {
	// int amount = 0;
	// for (MillionPlayer m : cap.getPlayers()) {
	// if (FTBLibAPI.arePlayersInSameTeam(player.getUniqueID(), m.getUuid())) {
	// amount += m.getAmount();
	// }
	// }
	// players.put(player.getName(), amount);
	// } else {
	// players.put(player.getName(), mPlayer.getAmount());
	// }
	// for (String name : server.getOnlinePlayerNames()) {
	// String playerEntries = "";
	// EntityPlayer onlinePlayer = server.getPlayerList().getPlayerByUsername(name);
	//
	// for (String p : players.keySet()) {
	// playerEntries += p + ":" + players.get(p) + ",";
	// }
	// QuestForMillions.PACKET.sendTo(new TotalItemsPacket(playerEntries),
	// (EntityPlayerMP) onlinePlayer);
	// }
	// }
	// }
	// }
	// }

}
