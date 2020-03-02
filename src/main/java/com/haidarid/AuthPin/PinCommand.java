package com.haidarid.authpin;

import com.haidarid.authpin.Pin.Pin;
import com.haidarid.authpin.Pin.PinManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public class PinCommand implements CommandExecutor, TabCompleter{
	
	
	
	@Override
  public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> completions = new ArrayList<>();
		StringUtil.copyPartialMatches(args[0], Arrays.asList("Reload", "Change", "Help"), completions);
		if (args.length == 1) {
			Collections.sort(completions);
			return completions;
		}
		return null;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		if (sender instanceof Player) {
			if (args.length == 0) {
				Pin pin = PinManager.getPin(player);
				if (pin == null) {
					Utils.openGui(player, ChatColor.translateAlternateColorCodes('&', AuthPin.getInstance().getConfig().getString("reg-title")));
					Utils.pinGui();
					return true;
				} if (pin.isLogin() != true) {
					Utils.openGui(player, ChatColor.translateAlternateColorCodes('&', AuthPin.getInstance().getConfig().getString("log-title")));
					Utils.pinGui();
				return true;
				}
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', AuthPin.getInstance().getConfig().getString("log-already")));
			} else if (args.length >= 1) {
				if (args[0].equalsIgnoreCase("change")) {
					Utils.openGui(player, ChatColor.translateAlternateColorCodes('&', AuthPin.getInstance().getConfig().getString("change-title")));
					Utils.pinGui();
				} else if (args[0].equalsIgnoreCase("reload") && player.hasPermission("Pin.Reload.Command")) {
					AuthPin.getInstance().reloadConfig();
					player.sendMessage(ChatColor.GREEN + "Reload Complete");
				} else {
					player.sendMessage("");
					player.sendMessage(ChatColor.GRAY+ "------"+ ChatColor.AQUA +">" + ChatColor.GRAY +" Help " + ChatColor.AQUA +"<" +ChatColor.GRAY + "------");
					player.sendMessage(ChatColor.AQUA + "/Pin change"+ ChatColor.GRAY + " to change your pin");
					player.sendMessage(ChatColor.AQUA + "/Pin" + ChatColor.GRAY +" to register or login");
					if (player.hasPermission("Pin.Reload.Command")) {
						player.sendMessage(ChatColor.AQUA + "/Pin reload");
					}
					player.sendMessage("");
				}
			}
		}
		return false;
	}
}
