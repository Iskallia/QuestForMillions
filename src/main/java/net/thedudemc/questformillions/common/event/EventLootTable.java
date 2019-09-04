package net.thedudemc.questformillions.common.event;

import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.thedudemc.questformillions.QuestForMillions;
import net.thedudemc.questformillions.common.init.QFMItems;
import net.thedudemc.questformillions.common.util.Config;

@Mod.EventBusSubscriber(modid = QuestForMillions.MODID)
public class EventLootTable {

	private static int weight = Config.totemOfRain_rarity;

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onLootTableLoad(LootTableLoadEvent event) {
		if (Config.totemOfRain_enabled == true) {
			if (event.getName().equals(LootTableList.CHESTS_SIMPLE_DUNGEON)) {
				final LootPool pool2 = event.getTable().getPool("pool2");
				if (pool2 != null) {
					pool2.addEntry(new LootEntryItem(QFMItems.TOTEM_OF_RAIN, weight, 0, new LootFunction[0], new LootCondition[0], "questformillions:totem_of_rain"));
				}
			}
			if (event.getName().equals(LootTableList.CHESTS_ABANDONED_MINESHAFT)) {
				final LootPool pool2 = event.getTable().getPool("pool2");
				if (pool2 != null) {
					pool2.addEntry(new LootEntryItem(QFMItems.TOTEM_OF_RAIN, weight, 0, new LootFunction[0], new LootCondition[0], "questformillions:totem_of_rain"));
				}
			}
			if (event.getName().equals(LootTableList.CHESTS_DESERT_PYRAMID)) {
				final LootPool pool1 = event.getTable().getPool("pool1");
				if (pool1 != null) {
					pool1.addEntry(new LootEntryItem(QFMItems.TOTEM_OF_RAIN, weight, 0, new LootFunction[0], new LootCondition[0], "questformillions:totem_of_rain"));
				}
			}
			if (event.getName().equals(LootTableList.CHESTS_NETHER_BRIDGE)) {
				final LootPool main = event.getTable().getPool("main");
				if (main != null) {
					main.addEntry(new LootEntryItem(QFMItems.TOTEM_OF_RAIN, weight, 0, new LootFunction[0], new LootCondition[0], "questformillions:totem_of_rain"));
				}
			}
			if (event.getName().equals(LootTableList.CHESTS_STRONGHOLD_LIBRARY)) {
				final LootPool main = event.getTable().getPool("main");
				if (main != null) {
					main.addEntry(new LootEntryItem(QFMItems.TOTEM_OF_RAIN, weight, 0, new LootFunction[0], new LootCondition[0], "questformillions:totem_of_rain"));
				}
			}
		}
	}

}
