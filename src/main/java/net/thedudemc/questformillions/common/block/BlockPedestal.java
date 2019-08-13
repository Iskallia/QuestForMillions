package net.thedudemc.questformillions.common.block;

import java.util.Random;

import com.feed_the_beast.ftblib.lib.data.FTBLibAPI;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.thedudemc.questformillions.QuestForMillions;
import net.thedudemc.questformillions.common.init.QFMBlocks;
import net.thedudemc.questformillions.common.network.TotalItemsPacket;
import net.thedudemc.questformillions.common.tileentity.TilePedestal;

public class BlockPedestal extends Block {

	private AxisAlignedBB boundingBox = new AxisAlignedBB(0.0625D, 0, 0.0625D, 0.875D, 1, 0.875D);

	public BlockPedestal() {
		super(Material.IRON);
		setLightLevel(1.0f);
		setHardness(5.0F);
		setResistance(10.0F);
		setSoundType(SoundType.METAL);
		this.setCreativeTab(CreativeTabs.MISC);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		for (int i = 0; i < 10; ++i) {
			int j = rand.nextInt(2) * 2 - 1;
			int k = rand.nextInt(2) * 2 - 1;
			double d0 = (double) pos.getX() + 0.5D + 0.1D * (double) j;
			double d1 = (double) ((float) pos.getY() + 1.5D + rand.nextFloat());
			double d2 = (double) pos.getZ() + 0.5D + 0.1D * (double) k;
			double d3 = (double) (rand.nextFloat() * (float) j);
			double d4 = ((double) rand.nextFloat() - 0.5D) * 0.125D;
			double d5 = (double) (rand.nextFloat() * (float) k);
			worldIn.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, d0, d1, d2, d3, d4, d5);
		}
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return boundingBox;
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TilePedestal();
	}

	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		// super.getDrops(drops, world, pos, state, fortune);
		TilePedestal pedestal = (TilePedestal) world.getTileEntity(pos);
		if (pedestal != null) {
			ItemStack drop = new ItemStack(QFMBlocks.PEDESTAL);
			NBTTagCompound compound = new NBTTagCompound();
			compound.setInteger("totalItems", pedestal.getTotalItems());
			compound.setString("owningTeam", pedestal.getOwningTeam());
			drop.setTagCompound(compound);
			drops.add(drop);
		}
	}

	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
		if (willHarvest)
			return true;
		return super.removedByPlayer(state, world, pos, player, willHarvest);
	}

	@Override
	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack) {
		super.harvestBlock(worldIn, player, pos, state, te, stack);
		worldIn.setBlockToAir(pos);
		QuestForMillions.PACKET.sendToAll(new TotalItemsPacket(0));
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		TilePedestal pedestal = (TilePedestal) worldIn.getTileEntity(pos);
		if (pedestal == null) {
			return;
		}
		if (stack.hasTagCompound()) {
			NBTTagCompound nbt = stack.getTagCompound();
			if (nbt.hasKey("totalItems")) {
				pedestal.setTotalItems(nbt.getInteger("totalItems"));
			}
			if (nbt.hasKey("owningTeam")) {
				pedestal.setOwningTeam(nbt.getString("owningTeam"));
			}
		} else {
			String team = FTBLibAPI.getTeam(placer.getUniqueID());
			if (!team.isEmpty()) {
				pedestal.setOwningTeam(team);
			} else {
				placer.sendMessage(new TextComponentString("You may not place a pedestal until you have joined a team!"));
				return;
			}
		}
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
	}

	@Override
	public BlockRenderLayer getRenderLayer() {
		// TODO Auto-generated method stub
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isTranslucent(IBlockState state) {
		return false;
	}

	@Override
	public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		if (entityIn instanceof EntityItem) {
			EntityItem item = (EntityItem) entityIn;
			if (item.getItem().isItemEqual(new ItemStack(Items.DIAMOND))) {
				item.collided = false;
				item.entityCollisionReduction = 0;
				return;
			}
		}
		super.onEntityCollision(worldIn, pos, state, entityIn);
	}

}
