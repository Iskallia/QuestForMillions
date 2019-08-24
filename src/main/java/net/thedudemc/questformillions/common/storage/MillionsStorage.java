package net.thedudemc.questformillions.common.storage;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class MillionsStorage implements IStorage<IMillion> {

	@Override
	public NBTBase writeNBT(Capability<IMillion> capability, IMillion instance, EnumFacing side) {
		return new NBTTagInt(instance.getAmount());
	}

	@Override
	public void readNBT(Capability<IMillion> capability, IMillion instance, EnumFacing side, NBTBase nbt) {
		instance.setItems(((NBTPrimitive) nbt).getInt());
	}

}
