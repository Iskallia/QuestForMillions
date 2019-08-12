package net.thedudemc.template;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {

	private Template plugin;

	public Config(Template dudeUtils) {
		this.plugin = dudeUtils;
	}

	public void initConfig() {

		FileConfiguration config = plugin.getConfig();

		plugin.saveDefaultConfig();
		config.addDefault("placeholder", true);

		config.options().copyDefaults(true);
		plugin.saveConfig();
	}
}
