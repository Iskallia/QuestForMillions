package net.thedudemc.questformillions.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class TotalItemsPacket implements IMessage {
	public TotalItemsPacket() {
	}

	private String playerEntries;

	public TotalItemsPacket(String playerEntry) {
		this.playerEntries = playerEntry;
	}

	public String getPlayers() {
		return this.playerEntries;
	}

	@Override
	public void toBytes(ByteBuf buf) {

		ByteBufUtils.writeUTF8String(buf, playerEntries);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.playerEntries = ByteBufUtils.readUTF8String(buf);
	}
}