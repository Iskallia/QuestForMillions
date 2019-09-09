package net.thedudemc.questformillions.common.network;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.thedudemc.questformillions.client.gui.GuiRenderer;

public class TotalItemsPacketHandler implements IMessageHandler<TotalItemsPacket, IMessage> {
	// Do note that the default constructor is required, but implicitly defined in
	// this case
	HashMap<String, Integer> players = new HashMap<String, Integer>();

	@Override
	public IMessage onMessage(TotalItemsPacket message, MessageContext ctx) {
		players.clear();
		String playerName = message.getPlayers();
		String[] splitAll = playerName.split(",");
		for (String player : splitAll) {
			if (!player.isEmpty()) {
				String[] split = player.split(":");
				String name = split[0];
				int amount = Integer.parseInt(split[1]);
				players.put(name, amount);
			}
		}
		LinkedHashMap<String, Integer> sorted = new LinkedHashMap<>();
		players.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEachOrdered(x -> sorted.put(x.getKey(), x.getValue()));
		GuiRenderer.instance.setPlayers(sorted);
		return null;
	}

}