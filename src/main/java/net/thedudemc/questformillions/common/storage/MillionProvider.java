package net.thedudemc.questformillions.common.storage;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class MillionProvider implements ICapabilitySerializable<NBTBase> {

	@CapabilityInject(IMillion.class)
	public static final Capability<IMillion> MILLION_CAP = null;

	private IMillion instance = MILLION_CAP.getDefaultInstance();

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == MILLION_CAP;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == MILLION_CAP ? MILLION_CAP.<T>cast(this.instance) : null;
	}

	@Override
	public NBTBase serializeNBT() {
		return MILLION_CAP.getStorage().writeNBT(MILLION_CAP, this.instance, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		MILLION_CAP.getStorage().readNBT(MILLION_CAP, this.instance, null, nbt);
	}

}
