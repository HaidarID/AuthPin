package com.haidarid.authpin.Pin;

import java.util.List;

import org.bukkit.entity.Player;

public class Pin {

	
	private final Player player;
	private List<Integer> pin;
	private boolean login;

	public Pin(Player player, List<Integer> pin, boolean login) {
		this.player = player;
		this.pin = pin;
		this.login = login;
	}
	
	

	public Player getPlayer() {
		return player;
	}

	public List<Integer> getPin() {
		return pin;
	}

	public void setPin(List<Integer> pin) {
		this.pin = pin;
	}

	public boolean isLogin() {
		return login;
	}

	public void setLogin(boolean login) {
		this.login = login;
	}
	
}
