package com.haidarid.authpin.Listener;

import com.haidarid.authpin.AuthPin;
import com.haidarid.authpin.Pin.PinManager;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

public class GuiUncloseable implements Listener{

	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		try {
			if (PinManager.getPin((Player) e.getPlayer()).isLogin() != true && (e.getView().getTitle().equals(ChatColor.translateAlternateColorCodes('&', AuthPin.getInstance().getConfig().getString("log-title"))) ||
					e.getView().getTitle().equals(ChatColor.translateAlternateColorCodes('&', AuthPin.getInstance().getConfig().getString("reg-title"))))) {
				Inventory oldInventory = e.getInventory();
				new BukkitRunnable() {
		      @Override
		      public void run() {
		      	e.getPlayer().openInventory(oldInventory);
		      }
				}.runTaskLater(AuthPin.getInstance(), 1L);
			}	
		} catch (Exception exc) {
			Player player = (Player) e.getPlayer();
			new BukkitRunnable() {
	      @Override
	      public void run() {
	  			player.openInventory(e.getInventory());
	      }
			}.runTaskLater(AuthPin.getInstance(), 1L);

		}
	}
	
	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent e) {
		if (e.getPlayer().getOpenInventory().getTitle().equals(ChatColor.translateAlternateColorCodes('&', AuthPin.getInstance().getConfig().getString("log-title"))) ||
				e.getPlayer().getOpenInventory().getTitle().equals(ChatColor.translateAlternateColorCodes('&', AuthPin.getInstance().getConfig().getString("reg-title")))) {

			e.setCancelled(true);
		}
	}
}

