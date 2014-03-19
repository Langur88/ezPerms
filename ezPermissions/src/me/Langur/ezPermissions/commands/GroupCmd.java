package me.Langur.ezPermissions.commands;

import me.Langur.ezPermissions.SettingsManager;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class GroupCmd extends PermissionsCommand {
	
	public void run(CommandSender sender, String[] args) {
		if(args.length == 0) {
			sender.sendMessage(ChatColor.RED + "You didn't enter a group.");
			return;
		}
		
		String g = args[0];
		
		if(args.length < 2) {
			if(SettingsManager.getInstance().getGroup(g) != null) {
				if(SettingsManager.getInstance().getGroup(g).getPermissions.size() == 0) {
					sender.sendMessage(ChatColor.YELLOW + "No permissions for " + g + ".");
					return;
				}
				
				for(String permission : SettingsManager.getInstance().getGroup(g).getPermissions) {
					sender.sendMessage(ChatColor.YELLOW + permission);
					return;
				}
			} else {
				SettingsManager.getInstance().createGroup(g);
				sender.sendMessage(ChatColor.GREEN + "Created group!");
				return;
			}
		} else {
			if(args[1].equalsIgnoreCase("add")) {
				SettingsManager.getInstance().addGroup(args[2], g);
				sender.sendMessage(ChatColor.GREEN + "Added " + args[2] + " to group " + g + ".");
				return;
			}else if(args[1].equalsIgnoreCase("remove")) {
				SettingsManager.getInstance().removeGroup(args[2], g);
				sender.sendMessage(ChatColor.GREEN + "Removed " + args[2] + " from group " + g + ".");
				return;
			}else if(args[1].equalsIgnoreCase("addperm")) {
				SettingsManager.getInstance().getGroup(g).addPermission(args[2]);
				sender.sendMessage(ChatColor.GREEN + "Added " + args[2] + " to group " + g + ".");
				return;
			}else if(args[1].equalsIgnoreCase("removeperm")) {
				SettingsManager.getInstance()args.getGroup(g).removePermission(args[2]);
				sender.sendMessage(ChatColor.GREEN + "Removed " + args[2] + " from group " + g + ".");
				return;
			}
		}
	}
	
	public GroupCmd() {
		super("group", "<Name> [<Add | Remove | Addperm | Removeperm> <User | Perm>]");
	}
}
