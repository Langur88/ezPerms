package me.Langur.ezPermissions.commands;

import org.bukkit.command.CommandSender;

public abstract class PermissionsCommand {
	private String name, args;
	
	public PermissionsCommand(String name, String args) {
		this.name = name;
		this.args = args;
		}
	
	public String getName() {
		return name;
	}
	
	public String getArgs() {
		return args;
	}
	
	public abstract void run(CommandSender sender, String[] args);
}
