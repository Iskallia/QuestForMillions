package net.thedudemc.questformillions.common.init;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.IForgeRegistry;
import net.thedudemc.questformillions.QuestForMillions;
import net.thedudemc.questformillions.common.block.BlockPedestal;
import net.thedudemc.questformillions.common.block.BlockTreasure;
import net.thedudemc.questformillions.common.tileentity.TilePedestal;

@ObjectHolder(QuestForMillions.MODID)
@Mod.EventBusSubscriber(modid = QuestForMillions.MODID)
public class QFMBlocks {

	public static final Block PEDESTAL = null;
	public static final Block TREASURE = null;

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		IForgeRegistry<Block> registry = event.getRegistry();

		registerBlock(registry, "pedestal", new BlockPedestal());
		registerBlock(registry, "treasure", new BlockTreasure(Material.IRON));

		GameRegistry.registerTileEntity(TilePedestal.class, new ResourceLocation(QuestForMillions.MODID + ":tilepedestal"));

	}

	private static void registerBlock(final IForgeRegistry<Block> registry, final String name, final Block block) {
		block.setRegistryName(new ResourceLocation(QuestForMillions.MODID, name));
		block.setTranslationKey(QuestForMillions.getTranslationKey(name));

		registry.register(block);
	}

}
