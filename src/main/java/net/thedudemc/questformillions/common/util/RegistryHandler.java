package net.thedudemc.questformillions.common.util;

import com.feed_the_beast.ftblib.lib.data.FTBLibAPI;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.thedudemc.questformillions.common.init.QFMBlocks;

@EventBusSubscriber
public class RegistryHandler {

	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event) {
	}

	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> event) {
	}

	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event) {

	}

	@SubscribeEvent
	public static void onBlockPlaced(BlockEvent.PlaceEvent event) {
		IBlockState blockState = event.getPlacedBlock();
		if (Block.isEqualTo(blockState.getBlock(), QFMBlocks.PEDESTAL)) {
			EntityPlayer player = event.getPlayer();
			if (FTBLibAPI.getTeam(player.getUniqueID()).isEmpty()) {
				event.setCanceled(true);
			}
		}
	}

}
