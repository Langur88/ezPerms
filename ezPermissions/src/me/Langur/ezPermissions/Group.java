package me.Langur.ezPermissions;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

public class Group {
	private String name;
	private ArrayList<String> permissions;
	
	public Group(String name) {
		this.name = name;
		ConfigurationSection s = SettingsManager.getInstance().getGroupSection(name);
		if(!s.contains("permissions")) s.set("permissions", new ArrayList<String>());
		this.permissions = new ArrayList<String>(s.getStringList("permissions"));
	}
	
	public String getName() {
		return name;
	}
	
	public boolean hasPermission(String permission) {
		return permissions.contains(permission);
	}
	
	public void addPermission(String permission) {
		permissions.add(permission);
		SettingsManager.getInstance().getGroupSection(name).set("permissions", permissions);
		SettingsManager.getInstance().save();
		SettingsManager.getInstance().injectPlayer(Bukkit.getServer().getOnlinePlayers());
	}
	
	public void removePermission(String permission) {
		permissions.remove(permission);
		SettingsManager.getInstance().getGroupSection(name).set("permissions", permissions);
		SettingsManager.getInstance().save();
		SettingsManager.getInstance().injectPlayer(Bukkit.getServer().getOnlinePlayers());
	}
	
	public ArrayList<String> getPermissions() {
		return permissions;
	}
}