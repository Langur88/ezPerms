package me.Langur.ezPermissions;

import java.util.ArrayList;
import java.util.Arrays;

import me.Langur.ezPermissions.commands.GroupCmd;
import me.Langur.ezPermissions.commands.PermissionsCommand;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandManager implements CommandExecutor {
	
	private ArrayList<PermissionsCommand> cmds = new ArrayList<PermissionsCommand>();
	
	public CommandManager() {
		cmds.add(new GroupCmd());
		cmds.add(new UserCmd());
		cmds.add(new TestCmd());
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(cmd.getName().equalsIgnoreCase("permissions")) {
			if(args.length == 0) {
				for(PermissionsCommand c : cmds) {
					sender.sendMessage(ChatColor.YELLOW + "/permissions " + c.getName() + " " + c.getArgs());
				}
				
				return true;
			}
			
			ArrayList<String> a = new ArrayList<String>(Arrays.asList(args));
			a.remove(0);
			
			for(PermissionsCommand c : cmds) {
				if(c.getName().equalsIgnoreCase(args[0])) {
					try { c.run(sender, a.toArray(new String[a.size()])); }
					catch (Exception e) { sender.sendMessage(ChatColor.RED + "An error has occured."); e.printStackTrace(); }
				}
			}
		}
		return false;
	}
}
