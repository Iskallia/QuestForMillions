package net.thedudemc.questformillions.common.network;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.thedudemc.questformillions.client.gui.GuiRenderer;

public class TotalItemsPacketHandler implements IMessageHandler<TotalItemsPacket, IMessage> {
	// Do note that the default constructor is required, but implicitly defined in
	// this case

	@Override
	public IMessage onMessage(TotalItemsPacket message, MessageContext ctx) {
		int totalItems = message.getInt();
		GuiRenderer.instance.setTotalItems(totalItems);
		return null;
	}

}