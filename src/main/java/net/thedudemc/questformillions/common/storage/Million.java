package net.thedudemc.questformillions.common.storage;

import net.minecraft.nbt.NBTTagCompound;

public class Million implements IMillion {

	private int amount = 0;

	@Override
	public NBTTagCompound serializeNBT() {
		return null;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {

	}

	@Override
	public void addItems(int amount) {
		this.amount += amount;

	}

	@Override
	public void removeItems(int amount) {
		this.amount -= amount;

	}

	@Override
	public void setItems(int amount) {
		this.amount = amount;

	}

	@Override
	public int getAmount() {
		return this.amount;
	}
}
