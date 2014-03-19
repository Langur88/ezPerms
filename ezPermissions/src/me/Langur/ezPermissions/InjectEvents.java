package me.Langur.ezPermissions;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class InjectEvents implements Listener {
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		SettingsManager.getInstance().injectPlayer(e.getPlayer());
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		SettingsManager.getInstance().uninjectPlayer(e.getPlayer());
	}
}
