package net.thedudemc.questformillions.common.tileentity;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.thedudemc.questformillions.common.init.QFMItems;
import net.thedudemc.questformillions.common.storage.LootHandler;
import net.thedudemc.questformillions.common.storage.million.IMillion;
import net.thedudemc.questformillions.common.storage.million.MillionPlayer;
import net.thedudemc.questformillions.common.storage.million.MillionProvider;
import net.thedudemc.questformillions.common.util.Config;

public class TilePedestal extends TileEntity implements ITickable {

	String owningPlayer = "";
	static final int SIZE = 1;
	static final int MAX = 1000000;
	static final float treasureRate = Config.pedestal_treasureRate;

	int lootboxRate = Config.pedestal_lootboxRate;
	int currentIncrement = 0;;

	@Override
	public boolean hasFastRenderer() {
		return true;
	}

	@Override
	public void update() {
		// if we are on the client, do nothing.
		if (getWorld().isRemote)
			return;

		double x = this.getPos().getX() + 0.5D;
		double y = this.getPos().getY() + 2D;
		double z = this.getPos().getZ() + 0.5D;

		int range = 8;

		List<EntityItem> items = this.world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(x - range, y - range, z - range, x + range, y + range, z + range));
		for (EntityItem e : items) {
			if (!e.getItem().isItemEqual(new ItemStack(Items.DIAMOND))) {
				continue;
			}

			double factor = .0175D;
			e.entityCollisionReduction = 0;
			e.collided = false;
			e.addVelocity((x - e.posX) * factor, (y - e.posY) * factor, (z - e.posZ) * factor);
		}
		collectItems(getOwner(this.getOwningPlayer()));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("items", itemHandler.serializeNBT());
		compound.setString("owningPlayer", getOwningPlayer());
		compound.setInteger("currentIncrement", getCurrentIncrement());
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		if (compound.hasKey("items")) {
			itemHandler.deserializeNBT((NBTTagCompound) compound.getTag("items"));
		}
		if (compound.hasKey("owningPlayer")) {
			this.setOwningPlayer(compound.getString("owningPlayer"));
		}
		if (compound.hasKey("currentIncrement")) {
			this.setCurrentIncrement(compound.getInteger("currentIncrement"));
		}
	}

	private ItemStackHandler itemHandler = new ItemStackHandler(SIZE) {

		TilePedestal pedestal = TilePedestal.this;

		@Override
		public ItemStack extractItem(int slot, int amount, boolean simulate) {
			return ItemStack.EMPTY;
		}

		@Override
		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
			if (simulate) {
				if (!this.isItemValid(slot, stack)) {
					return ItemStack.EMPTY;
				} else {
					return stack;
				}
			} else {
				ItemStack toReturn = stack.copy();
				String player = pedestal.getOwningPlayer();
				if (getOwner(player).getAmount() < 1000000) {
					if (this.getStackInSlot(slot).getCount() <= 0) {
						this.setStackInSlot(slot, stack);
						toReturn.setCount(toReturn.getCount() - 1);
					}
					getOwner(player).addAmount(stack.getCount());
					World world = pedestal.world;
					double d0 = (double) (world.rand.nextFloat() * 0.5F) + 0.25D;
					double d1 = (double) (world.rand.nextFloat() * 0.5F) + 0.25D;
					double d2 = (double) (world.rand.nextFloat() * 0.5F) + 0.25D;
					int treasureAmount = (int) Math.round((double) stack.getCount() * treasureRate);
					EntityItem treasure = new EntityItem(pedestal.world, (double) pedestal.getPos().getX() + d0, (double) pedestal.getPos().getY() + 1 + d1, (double) pedestal.getPos().getZ() + d2,
							new ItemStack(QFMItems.TREASURE, treasureAmount));
					pedestal.world.spawnEntity(treasure);

					currentIncrement += stack.getCount();
					if (currentIncrement >= lootboxRate) {
						currentIncrement = currentIncrement % lootboxRate;
						EntityItem loot = new EntityItem(pedestal.world, (double) pedestal.getPos().getX() + d0, (double) pedestal.getPos().getY() + 1 + d1, (double) pedestal.getPos().getZ() + d2,
								LootHandler.getRandomLoot());
						pedestal.world.spawnEntity(loot);
					}
					pedestal.markDirty();
				}

				return toReturn;
			}
		}

		@Override
		protected void onContentsChanged(int slot) {
			if (pedestal != null) {
				IBlockState state = pedestal.world.getBlockState(pos);
				world.notifyBlockUpdate(pos, state, state, 3);
				pedestal.markDirty();
			}
		}

		@Override
		public boolean isItemValid(int slot, ItemStack stack) {
			ItemStack stackInSlot = this.getStackInSlot(slot);
			String player = pedestal.getOwningPlayer();
			if (Config.pedestal_enableCustomItem) {
				ItemStack customItem = new ItemStack(Item.getByNameOrId(Config.pedestal_customItem));
				if (customItem == null || customItem.isEmpty()) {
					return false;
				} else if (customItem.isItemEqual(stack) && getOwner(player).getAmount() <= MAX) {
					return true;
				}
			} else {
				if (stackInSlot.isEmpty()) {
					return true;
				} else if (stackInSlot.isItemEqual(stack) && getOwner(player).getAmount() <= MAX) {
					return true;
				}
			}
			return false;
		}
	};

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && facing == EnumFacing.UP) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && facing == EnumFacing.UP) {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemHandler);
		}
		return super.getCapability(capability, facing);
	}

	public boolean collectItems(MillionPlayer player) {

		boolean itemsCollected = false;
		if (player.getAmount() < 1000000) {
			List<EntityItem> items = TilePedestal.this.getWorld().<EntityItem>getEntitiesWithinAABB(EntityItem.class, getAABB(), EntitySelectors.IS_ALIVE);
			for (EntityItem item : items) {
				ItemStack stack = item.getItem().copy();
				if (!itemHandler.insertItem(0, stack, true).isEmpty()) {
					itemHandler.insertItem(0, stack, false);
					itemsCollected = true;
					item.setDead();
				}
			}
		}
		return itemsCollected;
	}

	public AxisAlignedBB getAABB() {
		double x = getPos().getX() + 1D;
		double y = getPos().getY() + 1.5D;
		double z = getPos().getZ() + 1D;
		double offset = 2D;
		return new AxisAlignedBB(x - offset, y - offset, z - offset, x + offset, y + offset, z + offset);
	}

	public String getOwningPlayer() {
		return owningPlayer;
	}

	public void setOwningPlayer(String owningPlayer) {
		this.owningPlayer = owningPlayer;
	}

	public int getCurrentIncrement() {
		return currentIncrement;
	}

	public void setCurrentIncrement(int currentIncrement) {
		this.currentIncrement = currentIncrement;
	}

	private MillionPlayer getOwner(String name) {
		String owner = this.getOwningPlayer();
		IMillion cap = this.world.getCapability(MillionProvider.MILLION_CAP, null);
		MillionPlayer player = cap.getPlayer(owner);
		return player;
	}

}
