package net.thedudemc.questformillions.common.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.thedudemc.questformillions.common.init.QFMBlocks;
import net.thedudemc.questformillions.common.tileentity.TilePedestal;

public class ItemPedestal extends ItemBlock {

	public ItemPedestal(Block block) {
		super(block);
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (stack.hasTagCompound()) {
			NBTTagCompound compound = stack.getTagCompound();
			if (compound.hasKey("owningPlayer")) {
				tooltip.add(TextFormatting.AQUA + "Owning Player" + TextFormatting.WHITE + ": " + TextFormatting.YELLOW + compound.getString("owningPlayer"));
			}
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote) {
			return EnumActionResult.SUCCESS;
		}
		if (facing != EnumFacing.UP) {
			return EnumActionResult.FAIL;
		} else {
			String name = player.getName();
			ItemStack itemstack = player.getHeldItem(hand);
			IBlockState state = QFMBlocks.PEDESTAL.getDefaultState();
			worldIn.setBlockState(pos.up(), state);
			SoundType soundtype = state.getBlock().getSoundType(state, worldIn, pos, player);
			worldIn.playSound((EntityPlayer) null, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
			TilePedestal pedestal = (TilePedestal) worldIn.getTileEntity(pos.up());
			if (itemstack.hasTagCompound()) {
				NBTTagCompound nbt = itemstack.getTagCompound();
				if (nbt.hasKey("owningPlayer")) {
					pedestal.setOwningPlayer(nbt.getString("owningPlayer"));
				}
			} else {
				pedestal.setOwningPlayer(name);

			}
			worldIn.notifyNeighborsOfStateChange(pos, QFMBlocks.PEDESTAL, true);
			itemstack.shrink(1);
			return EnumActionResult.SUCCESS;
		}

	}

}
