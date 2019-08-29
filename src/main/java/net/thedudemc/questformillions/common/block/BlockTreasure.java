package net.thedudemc.questformillions.common.block;

import java.util.Random;

import net.minecraft.block.BlockSnow;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.thedudemc.questformillions.common.init.QFMItems;

public class BlockTreasure extends BlockSnow {

	public BlockTreasure(Material materialIn) {
		super();
		this.setHardness(0.1F);
		this.setResistance(10.0F);
		this.setSoundType(SoundType.METAL);
		this.setCreativeTab(CreativeTabs.MISC);

	}

	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		// drops.add(new ItemStack(QFMItems.TREASURE, 1));
		super.getDrops(drops, world, pos, state, fortune);
	}

	@Override
	public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random) {
		return ((Integer) state.getValue(LAYERS));
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return QFMItems.TREASURE;
	}

	@Override
	public int quantityDropped(Random random) {
		return 0;
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
	}

}
