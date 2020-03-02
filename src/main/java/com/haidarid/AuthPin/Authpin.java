package com.haidarid.authpin;

import com.haidarid.authpin.Listener.ClickEvent;
import com.haidarid.authpin.Listener.GuiUncloseable;
import com.haidarid.authpin.Listener.PlayerJoin;
import com.haidarid.authpin.Pin.PinManager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class AuthPin extends JavaPlugin {
	
	private static AuthPin instance;
	@Override
	public void onEnable() {	
		try {
		PinManager.createFile();
		instance = this;
		getCommand("pin").setExecutor(new PinCommand());
		Bukkit.getPluginManager().registerEvents(new ClickEvent(), this);
		Bukkit.getPluginManager().registerEvents(new GuiUncloseable(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
		getConfig().options().copyDefaults(false);
		saveDefaultConfig();
		Utils.loadPin();
		Utils.fixConfig();
		}
		catch (Exception exc) {
			// TODO: handle exception
		}
	}
	
	@Override
	public void onDisable() {
		try {
			for (Player p : Utils.getPlayerRegistered()) {
				if (p.getInventory() != null) {
					p.closeInventory();
				}
			}
		}
		catch (Exception exc) {
		}
	}

	public static AuthPin getInstance() {
		return instance;
	}
}
