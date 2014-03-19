package me.Langur.ezPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.Plugin;

public class SettingsManager {
	private SettingsManager() {  }
	private static SettingsManager instance = new SettingsManager();
	
	public static SettingsManager getInstance() {
		return instance;
	}
	
	private Plugin p;
	private FileConfiguration config;
	private File cfile;
	
	private HashMap<String, PermissionAttachment> attachments = new HashMap<String, PermissionAttachment>();
	private ArrayList<Group> groups = new ArrayList<Group>();
	
	public void setup(Plugin p) {
		this.p = p;
		
		if(!p.getDataFolder().exists()) p.getDataFolder().mkdir();
		
		cfile = new File(p.getDataFolder(), "ezPermissions.yml");
		
		if(!cfile.exists()) {
			try { cfile.createNewFile(); }
			catch (Exception e) { e.printStackTrace(); }
		}
		
		config = YamlConfiguration.loadConfiguration(cfile);
		if(!config.contains("groups")) config.createSection("groups");
		setupGroups();
	}
	
	/*
	 * permissions:
	 *   Langur:
	 *     - l.ol
	 *     - ezPermissions.test
	 */
	
	public void addPermission(String player, String permission) {
		player = player.toLowerCase();
		Player p = Bukkit.getServer().getPlayer(player);
		if(p != null) {
			injectPlayer(p);
			attachments.get(p.getName()).setPermission(permission, true);
		}
		List<String> permissions = getPermissions(player);
		permissions.add(permission);
		config.set("user." + player + ".permissions", permission);
		save();
	}
	
	public void removePermission(String player, String permission) {
		player = player.toLowerCase();
		Player p = Bukkit.getServer().getPlayer(player);
		if(p != null) {
			injectPlayer(p);
			attachments.get(player).setPermission(permission, false);
		}
		List<String> permissions = getPermissions(player);
		permissions.remove(permission);
		config.set("user." + player + ".permissions", permission);
		save();
	}
	
	public void addGroup(String player, String g) {
		player = player.toLowerCase();
		List<String> groups = getGroups(player);
		groups.add(g);
		config.set("user." + player + ".groups", groups);
		save();
		Player p = Bukkit.getServer().getPlayer(player);
		if(p != null) {
			injectPlayer(p);
		}
	}
	
	public void removeGroup(String player, String g) {
		player = player.toLowerCase();
		List<String> groups = getGroups(player);
		groups.remove(g);
		config.set("user." + player + ".groups", groups);
		save();
		Player p = Bukkit.getServer().getPlayer(player);
		if(p != null) {
			injectPlayer(p);
		}
	}
	
	public List<String> getPermissions(String player) {
		if(!config.contains("user." + player + ".permissions")) config.set("user." + player + ".permissions", new ArrayList<String>());
		return config.getStringList("user." + player + ".permissions");
	}
	
	public List<String> getGroups(String player) {
		player = player.toLowerCase();
		if(!config.contains("user." + player + ".groups")) config.set("user." + player + ".groups", new ArrayList<String>());
		return config.getStringList("permissions." + player);
	}
	
	public void createGroup(String group) {
		config.getConfigurationSection("groups").set(group + ".perms", new ArrayList<String>());
		save();
		setupGroups();
	}
	
	private void setupGroups() {
		groups.clear();
		for(String groupName : config.getConfigurationSection("groups").getKeys(false)) {
			groups.add(new Group(groupName));
		}
	}
	
	public void injectPlayer(Player... pl) {
		for(Player p : pl) {
			if(attachments.get(p.getName()) != null) attachments.put(p.getName(), p.addAttachment(getPlugin()));
			for(Entry<String, Boolean> permission : attachments.get(p.getName()).getPermissions().entrySet()) {
				if(getPermissions(p.getName()).contains(permission.getKey())) attachments.get(p.getName()).setPermission(permission.getKey(), true); else attachments.get(p.getName()).setPermission(permission.getKey(), false);
			}
			if(!config.contains("user." + p.getName().toLowerCase() + ".groups")) config.set("user." + p.getName().toLowerCase() + ".groups", new ArrayList<String>());
			for(String groupName : config.getStringList("user." + p.getName().toLowerCase() + ".groups")) {
				for(Group g : groups) {
					if(g.getName().equals(groupName)) {
						for(String permission : g.getPermissions()) {
							System.out.println("Injecting " + permission + " from " + groupName + ".");
							attachments.get(p.getName()).setPermission(permission, true);
						}
					}
				}
			}
		}
	}
	
	public void uninjectPlayer(Player pl) {
		if(attachments.get(pl.getName()) == null) return;
		pl.removeAttachment(attachments.get(pl.getName()));
		attachments.remove(pl.getName());
	}
	
	public Group getGroup(String name) {
		for(Group g : groups) {
			if(g.getName().equals(name)) return g;
		}
		return null;
	}
	
	public ConfigurationSection getGroupSection(String name) {
		return config.getConfigurationSection("groups." + name);
	}
	
	public void save() {
		try { config.save(cfile); }
		catch (Exception e) { e.printStackTrace(); }
	}
	
	public Plugin getPlugin() {
		return p;
	}
}
