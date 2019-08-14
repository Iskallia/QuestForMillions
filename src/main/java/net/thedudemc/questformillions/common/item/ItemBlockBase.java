package net.thedudemc.questformillions.common.item;

import java.util.List;

import com.feed_the_beast.ftblib.lib.data.FTBLibAPI;

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
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.thedudemc.questformillions.common.init.QFMBlocks;
import net.thedudemc.questformillions.common.tileentity.TilePedestal;

public class ItemBlockBase extends ItemBlock {

	public ItemBlockBase(Block block) {
		super(block);
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (stack.hasTagCompound()) {
			NBTTagCompound compound = stack.getTagCompound();
			if (compound.hasKey("totalItems")) {
				tooltip.add(TextFormatting.AQUA + "Total Diamonds" + TextFormatting.WHITE + ": " + TextFormatting.YELLOW + compound.getInteger("totalItems"));
			}
			if (compound.hasKey("owningTeam")) {
				tooltip.add(TextFormatting.AQUA + "Owning Team" + TextFormatting.WHITE + ": " + TextFormatting.YELLOW + compound.getString("owningTeam"));
			}
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote) {
			return EnumActionResult.SUCCESS;
		} else if (facing != EnumFacing.UP) {
			return EnumActionResult.FAIL;

		} else {
			String team = FTBLibAPI.getTeam(player.getUniqueID());
			if (team.isEmpty()) {
				player.sendMessage(new TextComponentString(TextFormatting.RED + "You must be on a team to place the pedestal!"));
				return EnumActionResult.FAIL;
			} else {

				ItemStack itemstack = player.getHeldItem(hand);
				IBlockState state = QFMBlocks.PEDESTAL.getDefaultState();
				worldIn.setBlockState(pos.up(), state);
				SoundType soundtype = state.getBlock().getSoundType(state, worldIn, pos, player);
				worldIn.playSound((EntityPlayer) null, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);

				TilePedestal pedestal = (TilePedestal) worldIn.getTileEntity(pos.up());

				if (pedestal == null) {
					System.out.println("pedestal was null");
					return EnumActionResult.FAIL;
				}
				if (itemstack.hasTagCompound()) {
					NBTTagCompound nbt = itemstack.getTagCompound();
					if (nbt.hasKey("totalItems")) {
						pedestal.setTotalItems(nbt.getInteger("totalItems"));
					}
					if (nbt.hasKey("owningTeam")) {
						pedestal.setOwningTeam(nbt.getString("owningTeam"));
					}
				} else {

					pedestal.setOwningTeam(team);

				}
				itemstack.shrink(1);
				return EnumActionResult.SUCCESS;
			}
		}
	}

}
