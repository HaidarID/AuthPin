package com.haidarid.authpin.Listener;

import com.haidarid.authpin.Utils;
import com.haidarid.authpin.Pin.Pin;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Pin pin = Utils.loadPlayerPin(e.getPlayer());
		if (pin != null) {
			pin.setLogin(false);
		}
		e.getPlayer().performCommand("pin");
	}
}
	

