package net.thedudemc.questformillions.common.storage;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class Loot {

	NonNullList<ItemStack> items = NonNullList.withSize(27, ItemStack.EMPTY);

	public Loot(NonNullList<ItemStack> list) {
		this.items = list;

	}

	public void add(int slot, ItemStack stack) {
		this.items.set(slot, stack);
	}

	public void set(NonNullList<ItemStack> list) {
		this.items = list;
	}
}
