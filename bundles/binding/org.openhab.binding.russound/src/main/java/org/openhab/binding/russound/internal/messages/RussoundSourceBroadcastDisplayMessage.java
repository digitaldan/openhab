package org.openhab.binding.russound.internal.messages;

public class RussoundSourceBroadcastDisplayMessage{
	private int source;
	private int flashTime;
	private String text;
	
	
	public RussoundSourceBroadcastDisplayMessage(int source, int flashTime, String text) {
		this.source = source;
		this.flashTime = flashTime;
		this.text = text;
	}
	public int getSource() {
		return source;
	}
	public void setSource(int source) {
		this.source = source;
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
