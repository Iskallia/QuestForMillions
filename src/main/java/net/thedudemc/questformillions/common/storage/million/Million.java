package net.thedudemc.questformillions.common.storage.million;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.BlockPos;

public class Million implements IMillion {

	private List<MillionPlayer> players = new ArrayList<MillionPlayer>();

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		NBTTagList list = new NBTTagList();
		for (MillionPlayer player : players) {
			NBTTagCompound compound = new NBTTagCompound();
			compound.setString("name", player.getName());
			compound.setUniqueId("uuid", player.getUuid());
			compound.setInteger("amount", player.getAmount());
			compound.setTag("pedestal", NBTUtil.createPosTag(player.getPedestal()));
			list.appendTag(compound);
		}
		nbt.setTag("players", list);
		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		if (nbt.hasKey("players")) {
			NBTTagList list = (NBTTagList) nbt.getTag("players");
			for (NBTBase base : list) {
				NBTTagCompound player = (NBTTagCompound) base;
				String name = player.getString("name");
				UUID uuid = player.getUniqueId("uuid");
				int amount = player.getInteger("amount");
				BlockPos pedestal = NBTUtil.getPosFromTag(player.getCompoundTag("pedestal"));
				MillionPlayer mPlayer = new MillionPlayer(name, uuid, amount, pedestal);
				players.add(mPlayer);
			}
		}

	}

	@Override
	public void addPlayer(MillionPlayer player) {
		players.add(player);
	}

	@Override
	public void removePlayer(MillionPlayer player) {
		if (players.contains(player)) {
			System.out.println("Player exits");
			players.remove(player);
		}
	}

	@Override
	public void addAmount(MillionPlayer player, int amount) {
		players.forEach(p -> {
			if (p.equals(player)) {
				p.addAmount(amount);
			}
		});

	}

	@Override
	public void removeAmount(MillionPlayer player, int amount) {
		players.forEach(p -> {
			if (p.equals(player)) {
				p.removeAmount(amount);
			}
		});

	}

	@Override
	public void setAmount(MillionPlayer player, int amount) {
		players.forEach(p -> {
			if (p.equals(player)) {
				p.setAmount(amount);
			}
		});

	}

	@Override
	public List<MillionPlayer> getPlayers() {
		return this.players;
	}

	@Override
	public MillionPlayer getPlayer(String name) {
		for (MillionPlayer player : players) {
			if (player.getName().equalsIgnoreCase(name)) {
				return player;
			}
		}
		return null;
	}

	@Override
	public MillionPlayer getPlayer(UUID uuid) {
		for (MillionPlayer player : players) {
			if (player.getUuid().equals(uuid)) {
				return player;
			}
		}
		return null;
	}

	@Override
	public MillionPlayer getPlayer(BlockPos pedestal) {
		for (MillionPlayer player : players) {
			if (player.getPedestal().equals(pedestal)) {
				return player;
			}
		}
		return null;
	}

	public int getTotalCount() {
		int amount = 0;
		for (MillionPlayer player : players) {
			amount += player.getAmount();
		}
		return amount;
	}
}
