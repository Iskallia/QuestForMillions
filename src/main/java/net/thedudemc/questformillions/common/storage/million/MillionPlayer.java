package net.thedudemc.questformillions.common.storage.million;

import java.util.UUID;

import net.minecraft.util.math.BlockPos;

public class MillionPlayer {

	private String name;
	private UUID uuid;
	private int amount;
	private BlockPos pedestal;

	public MillionPlayer(String name, UUID uuid, int amount, BlockPos pedestal) {
		this.name = name;
		this.uuid = uuid;
		this.amount = amount;
		this.pedestal = pedestal;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public void addAmount(int amount) {
		if (getAmount() + amount <= 1000000) {
			this.amount += amount;
		} else {
			this.amount = 1000000;
		}
	}

	public void removeAmount(int amount) {
		if (getAmount() - amount >= 0) {
			this.amount -= amount;
		} else {
			this.amount = 0;
		}
	}

	public BlockPos getPedestal() {
		return pedestal;
	}

	public void setPedestal(BlockPos pedestal) {
		this.pedestal = pedestal;
	}

}
