package com.haidarid.authpin;

import com.haidarid.authpin.Pin.Pin;
import com.haidarid.authpin.Pin.PinManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Utils {
	 
	private static Inventory gui;
	
	public static void pinGui() {
		Integer slot = 1;
		for (int i = 0; i < 9; i++) {			
				Material material = Material.getMaterial(AuthPin.getInstance().getConfig().getString("item"));
				gui.setItem(i, insertItem(material, ChatColor.GREEN + slot.toString(), slot));
				slot++;
		}
	}
	
	private static ItemStack insertItem(Material material, String name, int value) {
		ItemStack pin = new ItemStack(material, value);
		ItemMeta pinMeta = pin.getItemMeta();
		pinMeta.setDisplayName(name);
		pin.setItemMeta(pinMeta);
		
		return pin;
	}

	public static void openGui(Player p, String titlegui) {
		gui = Bukkit.createInventory(p, InventoryType.DISPENSER, titlegui);
		p.openInventory(gui);
	}
	
	public static Inventory getGui() {
		return gui;
	}

	public static void setLore(Inventory inv, String lore) {
		for (int i = 0; i < 9 ; i++) {
			ItemStack item = inv.getItem(i);
			ItemMeta itemMeta = item.getItemMeta();
			if (itemMeta.getLore() == null) {
				itemMeta.setLore(Arrays.asList("", ChatColor.GREEN + "Pin : " + lore, ""));
			} else {
				itemMeta.setLore((Arrays.asList("", itemMeta.getLore().get(1) + lore, "")));
			}
			item.setItemMeta(itemMeta);
		}
	}
	
	public static Pin loadPlayerPin(Player player) {
		try {
			List<Integer> pinFinal  = new ArrayList<>();
			Integer pin = Integer.parseInt(PinManager.getConfig().getString("Player." + player.getName() + ".Pin"));
			pinFinal.add((int) (pin * 0.1 * 0.1 * 0.1));
			pinFinal.add((int) (pin * 0.1 * 0.1  % 10));
			pinFinal.add((int) (pin * 0.1 % 10));
			pinFinal.add((pin % 10));
			Pin pin2 = PinManager.createPin(player, pinFinal);
			return pin2;
		} catch (Exception exc) {
			// TODO: handle exception
		}
		return null;
	}
	
	public static void loadPin() {
			for (Player p : getPlayerRegistered()) {
				loadPlayerPin(p);
				p.performCommand("pin");
			}
	}
	
	public static List<Player> getPlayerRegistered() {
		List<Player> player = new ArrayList<>();
		for (String key : PinManager.getConfig().getConfigurationSection("Player").getKeys(false)) {
			player.add(Bukkit.getPlayerExact(key));
		}
		return player;
	}
	
	public static void fixConfig() {
		try {
			Sound.valueOf(AuthPin.getInstance().getConfig().getString("click-sound"));
		}
		catch (Exception exc) {
			AuthPin.getInstance().getConfig().set("click-sound", Sound.ENTITY_EXPERIENCE_ORB_PICKUP.toString());
		}
		try {
			Sound.valueOf(AuthPin.getInstance().getConfig().getString("pin-correct-sound"));
		}
		catch (Exception exc) {
			AuthPin.getInstance().getConfig().set("pin-correct-sound", Sound.ENTITY_PLAYER_LEVELUP.toString());
		}
		try {
			Sound.valueOf(AuthPin.getInstance().getConfig().getString("pin-wrong-sound"));
		}
		catch (Exception exc) {
			AuthPin.getInstance().getConfig().set("pin-wrong-sound", Sound.ENTITY_PLAYER_HURT.toString());
		}
		try {
			Material.getMaterial(AuthPin.getInstance().getConfig().getString("item"));
		}
		catch (Exception exc) {
			AuthPin.getInstance().getConfig().set("item", Material.SUNFLOWER.toString());
		}
		AuthPin.getInstance().saveConfig();
	}
	
}

