package org.openhab.binding.russound.internal.messages;

public class RussoundDirectDisplayFeedbackMessage {
	private int controller;
	private int zone;
	private int keypad;
	private int flashTime;
	private String text;
	
	
	public RussoundDirectDisplayFeedbackMessage(int controller, int zone,
			int keypad, int flashTime, String text) {
		super();
		this.controller = controller;
		this.zone = zone;
		this.keypad = keypad;
		this.flashTime = flashTime;
		this.text = text;
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
	public int getFlashTime() {
		return flashTime;
	}
	public void setFlashTime(int flashTime) {
		this.flashTime = flashTime;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	
	
}
