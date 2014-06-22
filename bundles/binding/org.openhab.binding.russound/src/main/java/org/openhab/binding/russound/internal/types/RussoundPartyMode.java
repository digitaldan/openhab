package org.openhab.binding.russound.internal.types;

public enum RussoundPartyMode {
	OFF(0x00),
	ON(0x01),
	MASTER(0x03);

	private int value = 0;

	RussoundPartyMode(int value){
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
	public static RussoundPartyMode fromState(byte state){
		switch (state){
		case 0:
			return OFF;
		case 1:
			return ON;
		case 2:
			return MASTER;
		default:
			return null;
		}
	}
}
