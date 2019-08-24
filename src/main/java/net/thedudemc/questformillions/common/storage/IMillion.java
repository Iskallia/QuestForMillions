package net.thedudemc.questformillions.common.storage;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public interface IMillion extends INBTSerializable<NBTTagCompound> {

	public void addItems(int amount);

	public void removeItems(int amount);

	public void setItems(int amount);

	public int getAmount();

}
