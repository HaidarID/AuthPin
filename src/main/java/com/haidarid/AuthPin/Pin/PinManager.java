package com.haidarid.authpin.Pin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class PinManager{

	private static File file;
	private static FileConfiguration customFile;
	private static HashMap<Player, Pin> playerPin = new HashMap<>();
	public static Pin createPin(Player player, List<Integer> pinFinal) {
		Pin pin = new Pin(player, pinFinal, false);
		createFile();
		String pinString = pinFinal.get(0).toString() + pinFinal.get(1).toString() + pinFinal.get(2).toString() + pinFinal.get(3).toString();
		customFile.set("Player." + player.getName() + ".Pin", pinString);
		add(player, pin);
		save();
		return pin;
	}
	
	public static void add(Player player, Pin pin) {
		playerPin.put(player, pin);
	}
	
	public static void del(Player player) {
		playerPin.put(player, null);
	}
	
	public static Pin getPin(Player player) {
		return playerPin.get(player);
	}
	
	public static FileConfiguration getConfig() {
		return customFile;
	}
	
	public static void reload() {
		customFile = YamlConfiguration.loadConfiguration(file);
	}
	
	public static void save() {
    try{
        customFile.save(file);
    }catch (IOException e){
        System.out.println("Couldn't save file");
    }
	}

	public static void createFile(){
	file = new File(Bukkit.getServer().getPluginManager().getPlugin("AuthPin").getDataFolder() + File.separator + "Pin.yml");
  if (!file.exists()){
      try {
      	file.getParentFile().mkdirs();
				file.createNewFile();
			}
			catch (IOException exc) {
				System.out.println(file.getAbsolutePath());
				exc.printStackTrace();
			}
  }
  customFile = YamlConfiguration.loadConfiguration(file);
}
	
}
