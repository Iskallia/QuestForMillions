package net.thedudemc.questformillions.common.storage.million;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class MillionsStorage implements IStorage<IMillion> {

	@Override
	public NBTBase writeNBT(Capability<IMillion> capability, IMillion instance, EnumFacing side) {
		return instance.serializeNBT();
	}

	@Override
	public void readNBT(Capability<IMillion> capability, IMillion instance, EnumFacing side, NBTBase nbt) {
		instance.deserializeNBT((NBTTagCompound) nbt);
	}

}
