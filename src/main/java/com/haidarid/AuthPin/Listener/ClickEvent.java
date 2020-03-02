package com.haidarid.authpin.Listener;

import com.haidarid.authpin.AuthPin;
import com.haidarid.authpin.Utils;
import com.haidarid.authpin.Pin.Pin;
import com.haidarid.authpin.Pin.PinManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class ClickEvent implements Listener {
	
	private HashMap<Player, List<Integer>> pin = new HashMap<>();
	private HashMap<Player, Integer> kesempatan = new HashMap<>();
	
	@EventHandler
	public void onInventoryCLick(InventoryClickEvent e) {
		Inventory inventory = e.getClickedInventory();
		Player p = (Player) e.getWhoClicked();
		if (e.getView().getTitle() != (ChatColor.translateAlternateColorCodes('&', AuthPin.getInstance().getConfig().getString("log-title"))) || 
				e.getView().getTitle() !=(ChatColor.translateAlternateColorCodes('&', AuthPin.getInstance().getConfig().getString("reg-title")))  ||
				e.getView().getTitle() != (ChatColor.translateAlternateColorCodes('&', AuthPin.getInstance().getConfig().getString("change-title")))) {
			Pin playerPin = PinManager.getPin(p);
			e.setCancelled(true);
			if (inventory != e.getView().getTopInventory() || inventory == null) return;
			if (pin.get(p) == null) pin.put(p, new ArrayList<>());
			if (kesempatan.get(p) == null) kesempatan.put(p, AuthPin.getInstance().getConfig().getInt("chances"));
			pin.get(p).add(e.getCurrentItem().getAmount());
			p.playSound(p.getLocation(), Sound.valueOf(AuthPin.getInstance().getConfig().getString("click-sound")), 3, 1);
			Utils.setLore(inventory, pin.get(p).get(pin.get(p).size() - 1).toString() + " " );
			if (pin.get(p).size() == 4 && playerPin == null) {
				Pin pin2 = PinManager.createPin(p, pin.get(p));
				pin2.setLogin(true);
				String pinString = "[ " + pin.get(p).get(0) + " " + pin.get(p).get(1) + " " + pin.get(p).get(2) + " " + pin.get(p).get(3) + " ]";
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', AuthPin.getInstance().getConfig().getString("reg-complete").replace("[Pin]", pinString)));
				p.closeInventory();
				p.playSound(p.getLocation(), Sound.valueOf(AuthPin.getInstance().getConfig().getString("pin-correct-sound")), 3, 1);
				pin.put(p, null);
			} else if (pin.get(p).size() == 4 && playerPin != null) {
				if (playerPin.getPin().equals(pin.get(p))) {
					playerPin.setLogin(true);
					p.playSound(p.getLocation(), Sound.valueOf(AuthPin.getInstance().getConfig().getString("pin-correct-sound")), 3, 1);
					pin.put(p, null);
					p.closeInventory();
					if (e.getView().getTitle().equals(ChatColor.translateAlternateColorCodes('&', AuthPin.getInstance().getConfig().getString("change-title")))) {
						PinManager.del(p);
						p.performCommand("pin");
						return;
					}
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', AuthPin.getInstance().getConfig().getString("log-complete")));
				} else {
					p.playSound(p.getLocation(), Sound.valueOf(AuthPin.getInstance().getConfig().getString("pin-wrong-sound")), 3, 1);
					Utils.pinGui();
					pin.put(p, null);
					kesempatan.put(p, kesempatan.get(p) - 1);
					if (kesempatan.get(p) == 0) {
						p.kickPlayer(ChatColor.translateAlternateColorCodes('&', AuthPin.getInstance().getConfig().getString("kick")));
					}
				}
			}
		}
	}
}

