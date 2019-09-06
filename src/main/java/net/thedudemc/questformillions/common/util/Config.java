package net.thedudemc.questformillions.common.util;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class Config {

	public static Configuration config;

	// pedestal
	public static int pedestal_range;
	public static boolean pedestal_enableCustomItem;
	public static String pedestal_customItem;
	public static float pedestal_treasureRate;

	// totem of rain
	public static boolean totemOfRain_enabled;
	public static int totemOfRain_rarity;
	public static int totemOfRain_duration;
	public static int totemOfRain_range;
	public static int totemOfRain_chanceForBlock;
	public static int totemOfRain_chanceForTNT;

	// bonus
	public static int bonus_chanceForBonus;

	public static void init(File file) {

		config = new Configuration(new File(file, "QuestForMillions.cfg"));
		config.load();

		pedestal_range = config.getInt("range", "pedestal", 8, 1, 16, "Sets the radius which the pedestal will pull the items.");
		pedestal_enableCustomItem = config.getBoolean("enableCustomItem", "pedestal", false, "You may define whether you will collect a custom item or default which is a diamond.");
		pedestal_customItem = config.getString("customItem", "pedestal", "minecraft:diamond", "Set the item which you would like the pedestal to collect. Enabled must be true.");
		pedestal_treasureRate = config.getFloat("treasureRate", "pedestal", 1, 0, 1, "The rate which treasure will be divided amongst items collected by the pedestal. ie. 64 diamonds * 0.5 rate = 32 treasure ");

		totemOfRain_enabled = config.getBoolean("enabled", "totem_of_rain", true, "When true, the Totem of Rain will spawn in chests generated with the world.");
		totemOfRain_rarity = config.getInt("rarity", "totem_of_rain", 5, 1, 50, "Set the chance that the Totem of Rain will spawn in chests! For reference, a Enchanted Golden Apple is 2 by default and a saddle is 20.");
		totemOfRain_duration = config.getInt("duration", "totem_of_rain", 60, 60, 300, "The amount of seconds the rain will last.");
		totemOfRain_range = config.getInt("range", "totem_of_rain", 16, 8, 64, "The effective range from the summoning player to rain. This is calculated as a box at this distance each cardinal direction centered on the summoning player.");
		totemOfRain_chanceForBlock = config.getInt("chanceForBlock", "totem_of_rain", 1, 1, 100, "Set the chance that a diamond rained during the Rain event will be a diamond block.");
		totemOfRain_chanceForTNT = config.getInt("chanceForTNT", "totem_of_rain", 20, 1, 100, "Set the chance that every 5 seconds, a primed TNT may rain down above the player.");

		bonus_chanceForBonus = config.getInt("chanceForBonus", "bonus", 5, 1, 100, "Set the chance that a bonus will occur on each Sunday morning in-game.");

		config.save();
	}

}
