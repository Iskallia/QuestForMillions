package net.thedudemc.questformillions.common.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.thedudemc.questformillions.common.event.EventStorm;
import net.thedudemc.questformillions.common.world.DiamondStorm;

public class ItemTotem extends Item {

	public ItemTotem() {

		this.setCreativeTab(CreativeTabs.MISC);
		this.setMaxStackSize(1);

	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		EventStorm.storms.add(new DiamondStorm(worldIn, playerIn.getPosition(), playerIn));
		ItemStack itemstack = playerIn.getHeldItem(handIn);

		if (!playerIn.capabilities.isCreativeMode) {
			itemstack.shrink(1);

		}
		BlockPos pos = playerIn.getPosition();
		worldIn.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_LIGHTNING_THUNDER, SoundCategory.AMBIENT, 0.5f, 1);
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}

}
