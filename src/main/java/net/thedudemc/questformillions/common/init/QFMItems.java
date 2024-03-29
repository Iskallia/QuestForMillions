package net.thedudemc.questformillions.common.init;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.IForgeRegistry;
import net.thedudemc.questformillions.QuestForMillions;
import net.thedudemc.questformillions.common.item.ItemDiamondStick;
import net.thedudemc.questformillions.common.item.ItemPedestal;
import net.thedudemc.questformillions.common.item.ItemTotem;
import net.thedudemc.questformillions.common.item.ItemTreasure;

@ObjectHolder(QuestForMillions.MODID)
@Mod.EventBusSubscriber
public class QFMItems {

	public static final Item PEDESTAL = null;
	public static final Item TREASURE = null;
	public static final Item TOTEM_OF_RAIN = null;
	public static final Item DIAMOND_STICK = null;

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> registry = event.getRegistry();
		// register items
		registerItem(registry, "totem_of_rain", new ItemTotem());
		registerItem(registry, "diamond_stick", new ItemDiamondStick());

		// register block items
		registerItem(registry, "pedestal", new ItemPedestal(QFMBlocks.PEDESTAL));
		registerItem(registry, "treasure", new ItemTreasure(QFMBlocks.TREASURE));

	}

	private static void registerItem(final IForgeRegistry<Item> registry, final String name, final Item item) {
		item.setRegistryName(new ResourceLocation(QuestForMillions.MODID, name));
		item.setTranslationKey(QuestForMillions.getTranslationKey(name));

		registry.register(item);
	}

}
