package net.thedudemc.questformillions.common.tileentity;

import java.util.List;

import com.feed_the_beast.ftblib.lib.data.FTBLibAPI;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.thedudemc.questformillions.QuestForMillions;
import net.thedudemc.questformillions.common.network.TotalItemsPacket;
import net.thedudemc.questformillions.common.util.Config;

public class TilePedestal extends TileEntity implements ITickable {

	int totalItems = 0;
	String owningTeam = "";

	static final int SIZE = 1;
	static final int MAX = 1000000;

	@Override
	public boolean hasFastRenderer() {
		return true;
	}

	@Override
	public void update() {
		// if we are on the client, do nothing.
		if (getWorld().isRemote)
			return;

		// once per second, update the necessary clients of their total diamonds.
		if (getWorld().getTotalWorldTime() % 20 == 0) {
			for (EntityPlayer p : this.getWorld().playerEntities) {
				EntityPlayerMP player = (EntityPlayerMP) p;
				if (FTBLibAPI.isPlayerInTeam(p.getUniqueID(), this.getOwningTeam())) {
					QuestForMillions.PACKET.sendTo(new TotalItemsPacket(getTotalItems()), player);
				}
			}
			// TODO: send only to specific team, dependant on who owns the pedestal.
		}

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
		collectItems();
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("items", itemHandler.serializeNBT());
		compound.setInteger("totalItems", getTotalItems());
		compound.setString("owningTeam", getOwningTeam());
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		if (compound.hasKey("items")) {
			itemHandler.deserializeNBT((NBTTagCompound) compound.getTag("items"));
		}
		if (compound.hasKey("totalItems")) {
			this.setTotalItems(compound.getInteger("totalItems"));
		}
		if (compound.hasKey("owningTeam")) {
			this.setOwningTeam(compound.getString("owningTeam"));
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
				if (this.getStackInSlot(slot).getCount() <= 0) {
					this.setStackInSlot(slot, stack);
				}
				pedestal.addToTotalItems(stack.getCount());
				pedestal.markDirty();
				return stack;
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
			if (Config.enableCustomItem) {
				ItemStack customItem = new ItemStack(Item.getByNameOrId(Config.customItem));
				if (customItem == null || customItem.isEmpty()) {
					return false;
				} else if (customItem.isItemEqual(stack) && getTotalItems() <= MAX) {
					return true;
				}
			} else {
				if (stackInSlot.isEmpty()) {
					return true;
				} else if (stackInSlot.isItemEqual(stack) && getTotalItems() <= MAX) {
					return true;
				}
			}
			return false;
		}
	};

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && facing == EnumFacing.DOWN) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && facing == EnumFacing.DOWN) {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemHandler);
		}
		return super.getCapability(capability, facing);
	}

	public boolean collectItems() {
		boolean itemsCollected = false;
		List<EntityItem> items = TilePedestal.this.getWorld().<EntityItem>getEntitiesWithinAABB(EntityItem.class, getAABB(), EntitySelectors.IS_ALIVE);
		for (EntityItem item : items) {
			ItemStack stack = item.getItem().copy();
			if (!itemHandler.insertItem(0, stack, true).isEmpty()) {
				itemHandler.insertItem(0, stack, false);
				itemsCollected = true;
				item.setDead();
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

	public int getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}

	public String getOwningTeam() {
		return owningTeam;
	}

	public void setOwningTeam(String owningTeam) {
		this.owningTeam = owningTeam;
	}

	public void addToTotalItems(int amount) {
		this.totalItems += amount;
	}

	public void subtractFromTotalItems(int amount) {
		this.totalItems -= amount;
	}

}
