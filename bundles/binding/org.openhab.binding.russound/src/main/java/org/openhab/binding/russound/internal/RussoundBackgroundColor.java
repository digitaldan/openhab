package org.openhab.binding.russound.internal;

public enum RussoundBackgroundColor {
	
	OFF(0x00),
	AMBER(0x01),
	GREEN(0x02);

	private int value = 0;

	RussoundBackgroundColor(int value){
		this.value = value;
	}
	public int getValue() {
		return value;
	}
	public static RussoundBackgroundColor fromState(byte state){
		switch (state){
		case 0:
			return OFF;
		case 1:
			return AMBER;
		case 2:
			return GREEN;
		default:
			return null;
		}
	}
}
