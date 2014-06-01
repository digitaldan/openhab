package org.openhab.binding.russound.internal;

public class RussoundZoneState {

	/**
     * The return message would look like the following.
        Byte # 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23
        Value F0 00 00 70 cc 00 7F 00 00 04 02 00 zz 07 00 00 01 00 0C 00 ## ## ##
        Byte # 24 25 26 27 28 29 30 31 32 33 34
        Value ## ## ## ## ## ## ## ## 00 xx F7
        cc = controller number -1
        zz = zone number -1
        xx = checksum
        The parameter values are depicted in bytes 21 – 31. These values will change depending on the
        state of the selected Zone. The above example shows the parameter values for a Zone
        configured and being used as follows:
        Byte #21 = Current Zone On/Off state (0x00 = OFF or 0x01 = ON)
        Byte #22 = Current Source selected -1
        Byte #23 = Current Volume level (0x00 - 0x32, 0x00 = 0 Displayed … 0x32 = 100 Displayed)
        Byte #24 = Current Bass level (0x00 = -10 … 0x0A = Flat … 0x14 = +10)
        Byte #25 = Current Treble level (0x00 = -10 … 0x0A = Flat … 0x14 = +10)
        Byte #26 = Current Loudness (0x00 = OFF, 0x01 = ON )
        Byte #27 = Current Balance level (0x00 = More Left … 0x0A = Center … 0x14 = More Right)
        Byte #28 = Current System On state (0x00 = All Zones Off, 0x01 = Any Zone is On)
        Byte #29 = Current Shared Source (0x00 = Not Shared 0x01 = Shared with another Zone)
        Byte #30 = Current Party Mode state (0x00 = OFF, 0x01 = ON, 0x02 = Master)*
        Byte #31 = Current Do Not Disturb state (0x00 = OFF, 0x01 = ON )*
     */
	
	private boolean power = false;
	private int source = 0;
	private int volume = 0;
	private int bass = 0;
	private int treble = 0;
	private boolean loudness = false;
	private int balance = 0;
	private boolean systemOn = false;
	private boolean sharedSource = false;
	private RussoundPartyMode partyMode  = RussoundPartyMode.OFF;
	private boolean doNotDisturb = false;
	
	
	public RussoundZoneState(boolean power, int source, int volume, int bass,
			int treble, boolean loudness, int balance, boolean systemOn,
			boolean sharedSource, RussoundPartyMode partyMode,
			boolean doNotDisturb) {
		super();
		this.power = power;
		this.source = source;
		this.volume = volume;
		this.bass = bass;
		this.treble = treble;
		this.loudness = loudness;
		this.balance = balance;
		this.systemOn = systemOn;
		this.sharedSource = sharedSource;
		this.partyMode = partyMode;
		this.doNotDisturb = doNotDisturb;
	}
	
	public boolean isPower() {
		return power;
	}
	public void setPower(boolean power) {
		this.power = power;
	}
	public int getSource() {
		return source;
	}
	public void setSource(int source) {
		this.source = source;
	}
	public int getVolume() {
		return volume;
	}
	public void setVolume(int volume) {
		this.volume = volume;
	}
	public int getBass() {
		return bass;
	}
	public void setBass(int bass) {
		this.bass = bass;
	}
	public int getTreble() {
		return treble;
	}
	public void setTreble(int treble) {
		this.treble = treble;
	}
	public boolean isLoudness() {
		return loudness;
	}
	public void setLoudness(boolean loudness) {
		this.loudness = loudness;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public boolean isSystemOn() {
		return systemOn;
	}
	public void setSystemOn(boolean systemOn) {
		this.systemOn = systemOn;
	}
	public boolean isSharedSource() {
		return sharedSource;
	}
	public void setSharedSource(boolean sharedSource) {
		this.sharedSource = sharedSource;
	}
	public RussoundPartyMode getPartyMode() {
		return partyMode;
	}
	public void setPartyMode(RussoundPartyMode partyMode) {
		this.partyMode = partyMode;
	}
	public boolean isDoNotDisturb() {
		return doNotDisturb;
	}
	public void setDoNotDisturb(boolean doNotDisturb) {
		this.doNotDisturb = doNotDisturb;
	}
	
	
}
