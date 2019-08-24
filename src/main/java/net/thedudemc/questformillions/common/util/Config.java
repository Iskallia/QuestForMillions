package net.thedudemc.questformillions.common.util;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class Config {

	public static Configuration config;
	public static boolean enableCustomItem;
	public static String customItem;

	public static void init(File file) {

		config = new Configuration(new File("config/QuestForMillions.cfg"));
		config.load();

		enableCustomItem = config.getBoolean("enableCustomItem", "item", true, "You may define whether you will collect a custom item or default which is a diamond.");
		customItem = config.getString("customItem", "item", "minecraft:diamond", "Set the item which you would like the pedestal to collect. Enabled must be true.");

		config.save();
	}

}
