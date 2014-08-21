package org.openhab.binding.russound.internal;

import org.openhab.core.binding.BindingConfig;

public class RussoundBindingConfig implements BindingConfig{

	private String deviceName;
	private int controller;
	private int zone;
	private int keypad;
	
	private RussoundItemType type;
	
	/**
	 * If the param String contains a controller number return that, otherwise
	 * return -1
	 * @return the controller number from our params or -1 if none
	 */
	public RussoundBindingConfig(String deviceName, RussoundItemType type){
		super();
		this.deviceName = deviceName;
		this.type = type;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public int getController() {
		return controller;
	}

	public void setController(int controller) {
		this.controller = controller;
	}

	public int getZone() {
		return zone;
	}

	public void setZone(int zone) {
		this.zone = zone;
	}

	public int getKeypad() {
		return keypad;
	}

	public void setKeypad(int keypad) {
		this.keypad = keypad;
	}

	public RussoundItemType getType() {
		return type;
	}

	public void setType(RussoundItemType type) {
		this.type = type;
	}
	
	
}
