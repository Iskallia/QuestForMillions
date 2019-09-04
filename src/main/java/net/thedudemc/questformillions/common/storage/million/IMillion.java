package net.thedudemc.questformillions.common.storage.million;

import java.util.List;
import java.util.UUID;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.INBTSerializable;

public interface IMillion extends INBTSerializable<NBTTagCompound> {

	public void addPlayer(MillionPlayer player);

	public void removePlayer(MillionPlayer player);

	public MillionPlayer getPlayer(String name);

	public MillionPlayer getPlayer(UUID uuid);

	public MillionPlayer getPlayer(BlockPos pedestal);

	public void addAmount(MillionPlayer player, int amount);

	public void removeAmount(MillionPlayer player, int amount);

	public void setAmount(MillionPlayer player, int amount);

	public List<MillionPlayer> getPlayers();

}
