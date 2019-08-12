package net.thedudemc.template;

import org.bukkit.plugin.java.JavaPlugin;

public class Template extends JavaPlugin {

	public Commands commands = new Commands(this);
	public Config config = new Config(this);
	// test

	@Override
	public void onEnable() {
		commands.initCommands();
		config.initConfig();

		getServer().getPluginManager().registerEvents(new Events(this), this);
	}

	@Override
	public void onDisable() {

	}

}
