package me.Langur.ezPermissions;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class ezPermissions extends JavaPlugin {
	
	public void onEnable() {
		SettingsManager.getInstance().setup(this);
		
		getCommand("permissions").setExecutor(new CommandManager());
		
		Bukkit.getServer().getPluginManager().registerEvents(new InjectEvents(), this);
	}
}

