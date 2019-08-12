package net.thedudemc.questformillions.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class TotalItemsPacket implements IMessage {
	public TotalItemsPacket() {
	}

	private int toSend;

	public TotalItemsPacket(int toSend) {
		this.toSend = toSend;
	}

	public int getInt() {
		return this.toSend;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(toSend);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.toSend = buf.readInt();
	}
}