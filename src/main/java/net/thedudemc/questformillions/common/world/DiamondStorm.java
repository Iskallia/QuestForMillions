package net.thedudemc.questformillions.common.world;

import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.thedudemc.questformillions.QuestForMillions;
import net.thedudemc.questformillions.common.util.Config;

public class DiamondStorm {

	private World world;
	private BlockPos origin;
	private EntityPlayer summoner;
	private int age = 0;
	private static int range = Config.totemOfRain_range;
	private static int min = range * -1;
	private static int max = range;
	private static Item item = QuestForMillions.item;
	private static int chanceForBlock = Config.totemOfRain_chanceForBlock;

	public DiamondStorm(World world, BlockPos location, EntityPlayer summoner) {
		this.world = world;
		this.origin = location;
		this.summoner = summoner;

	}

	public void spawnLava(BlockPos pos) {

		world.setBlockState(pos, Blocks.FLOWING_LAVA.getDefaultState());
	}

	public void spawnDiamond() {
		Random rand = new Random();
		BlockPos pos = selectRandomForDiamond();
		if (world.isRemote)
			return;
		EntityItem e = null;
		if (rand.nextInt(100) <= chanceForBlock) {
			e = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(Item.getItemFromBlock(Blocks.DIAMOND_BLOCK), 1));
		} else {
			e = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(item, 1));
		}

		world.spawnEntity(e);
	}

	public BlockPos selectHighestBlock() {
		int[] highest = { 0, 0, 0 };
		for (int x = min; x < max; x++) {
			for (int z = min; z < max; z++) {
				int currentX = origin.getX() + x;
				int currentZ = origin.getZ() + z;
				int[] current = { currentX, world.getHeight(currentX, currentZ), currentZ };
				if (current[1] > highest[1]) {
					highest = current;
				}
			}
		}
		return new BlockPos(highest[0], highest[1], highest[2]);
	}

	public BlockPos selectRandomForDiamond() {
		Random rand = new Random();
		int minY = 250;
		int maxY = 255;
		int x = origin.getX() + (rand.nextInt(max - min) + min);
		int z = origin.getZ() + (rand.nextInt(max - min) + min);
		int y = rand.nextInt(maxY - minY) + minY;
		return new BlockPos(x, y, z);
	}

	public BlockPos selectRandomForLava() {
		Random rand = new Random();
		int x = origin.getX() + (rand.nextInt(max - min) + min);
		int z = origin.getZ() + (rand.nextInt(max - min) + min);
		int y = world.getHeight(x, z) + 1;

		BlockPos potential = new BlockPos(x, y, z);
		return potential;

	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public BlockPos getOrigin() {
		return origin;
	}

	public void setOrigin(BlockPos location) {
		this.origin = location;
	}

	public EntityPlayer getSummoner() {
		return summoner;
	}

	public void setSummoner(EntityPlayer summoner) {
		this.summoner = summoner;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}
