package net.thedudemc.questformillions.common.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemDiamondStick extends Item {

	public ItemDiamondStick() {
		this.setMaxStackSize(1);
		this.setMaxDamage(1);

	}

	@Override
	public boolean isDamageable() {
		return true;
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {

		World world = attacker.world;
		if (world.isRemote)
			return true;
		stack.damageItem(2, attacker);
		BlockPos pos = target.getPosition();
		if (world.rand.nextInt(100) < 5) {
			attacker.world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY() + 50, pos.getZ(), new ItemStack(Item.getItemFromBlock(Blocks.DIAMOND_BLOCK))));
		} else {
			int amount = world.rand.nextInt(14) + 1;
			if (amount < 5) {
				amount += 5;
			}
			attacker.world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY() + 50, pos.getZ(), new ItemStack(Items.DIAMOND, amount)));
		}
		return true;
	}

	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		ItemStack stack = new ItemStack(this);
		stack.addEnchantment(Enchantments.KNOCKBACK, 3);
		if (tab == CreativeTabs.MISC) {
			items.add(stack);
		}
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return stack.isItemDamaged();
	}

}
