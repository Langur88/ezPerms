package me.Langur.ezPermissions.commands;

import me.Langur.ezPermissions.SettingsManager;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class UserCmd extends PermissionsCommand {

	
	public void run(CommandSender sender, String[] args) {
		if(args.length == 0) {
			sender.sendMessage(ChatColor.RED + "You didn't enter a user.");
			return;
		}
		
		String p = args[0];
		
		if(args.length < 2) {
			if(SettingsManager.getInstance().getPermissions(p).size() == 0) {
				sender.sendMessage(ChatColor.YELLOW + "No permissions for " + p + ".");
			}
			
			for(String permission : SettingsManager.getInstance().getPermissions(p)) {
				sender.sendMessage(ChatColor.YELLOW + permission);
				return;
			}
		} else {
			if(args[1].equalsIgnoreCase("addperm")) {
				SettingsManager.getInstance().addPermission(p, args[2]);
				sender.sendMessage(ChatColor.GREEN + "Added " + args[2] + " to " + p + ".");
				return;
			}else if(args[1].equalsIgnoreCase("removeperm")) {
				SettingsManager.getInstance().removePermission(p, args[2]);
				sender.sendMessage(ChatColor.GREEN + "Removed " + args[2] + " from " + p + ".");
				return;
			}
		}
	}
	
	public UserCmd() {
		super("user", "<name> [<addperm | removeperm> <permission>]");
	}
}
