package org.openhab.binding.russound.internal;

import java.io.UnsupportedEncodingException;

public class RussoundDevice extends Thread{

	private RussoundConnection connection;
	private boolean running;

	public RussoundDevice (RussoundConnection connection){
		this.connection = connection;
	}

	@Override
	public void run() {
		while (running) {

		}
	}

	private void send(byte[] bytes) {

	}


	public void sendKeypadEvent(int zone, RussoundKeyEvent event) {

		String cmd = "F0 00 00 7F 00 zz 70 05 02 02 00 ii ## 00 00 00 00 00 01 7B F7";

		cmd.replaceFirst("zz", Integer.toString(zone));
		cmd.replaceFirst("##", Integer.toString(event.invert() ? event.getValueInverted() : event.getValue()));
		cmd.replaceFirst("ii", Integer.toString(event.invert() ? 0xf1 : 0x00));
		send(formatCommand(cmd));
	}

	public void sendZoneOnOff(int controller, int zone, boolean on) {
		/**
		 * 7.1.1 Set State
        Turn a specific Zone ON or OFF using a discrete message.
        Byte # 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22
        Value F0 cc 00 7F 00 00 70 05 02 02 00 00 F1 23 00 ## 00 zz 00 01 xx F7
        cc = controller number -1
        zz = zone number -1
        xx = checksum
        Byte #16 = 0x00 (off) or 0x01(on)
		 */

		String cmd = "F0 cc 00 7F 00 00 70 05 02 02 00 00 F1 23 00 ## 00 zz 00 01 00 F7";

		cmd.replaceFirst("cc", Integer.toString(controller));
		cmd.replaceFirst("zz", Integer.toString(zone));
		cmd.replaceFirst("##", Integer.toString(on ? 0x01 : 0x00));
		send(formatCommand(cmd));

	}

	public void sendAllZonesOnOff(boolean on) {
		/**
		 * 7.1.3 Set All Zones On/Off State
        The RNET™ system can be sent a single message to issue an “All On” or “All Off” Event. The
        Controllers have the ability to enable or disable the All On/All Off state control per zone. See20
        the Product Manual for instructions on programming each zone for “System On Enable” or
        “System On Disable”.
        Turn All Zones ON or OFF using a discrete message (all zones enabled in programming).
        Byte # 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22
        Value F0 7E 00 7F 00 00 70 05 02 02 00 00 F1 22 00 00 ## 00 00 01 xx F7
        xx = checksum
        Byte #17 = 0x00 (all off) or 0x01(all on)
		 */

		String cmd = "F0 7E 00 7F 00 00 70 05 02 02 00 00 F1 22 00 00 ## 00 00 01 xx F7";

		cmd.replaceFirst("##", Integer.toString(on ? 0x01 : 0x00));
		send(formatCommand(cmd));
	}

	public void sendZoneSource(int controller, int zone, int source) {
		/**
		 * 7.2.1 Set Source
        Select the Source for a particular zone using a discrete message.
        Byte # 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22
        Value F0 cc 00 7F 00 zz 70 05 02 00 00 00 F1 3E 00 00 00 ## 00 01 xx F7
        cc = controller number -1
        zz = zone number -1
        xx = checksum
        Byte #18 = selected source number -1
		 */

		String cmd = "F0 cc 00 7F 00 zz 70 05 02 00 00 00 F1 3E 00 00 00 ## 00 01 xx F7";
		cmd.replaceFirst("cc", Integer.toString(controller));
		cmd.replaceFirst("zz", Integer.toString(zone));
		cmd.replaceFirst("##", Integer.toString(source));
		send(formatCommand(cmd));
	}

	public void sendZoneVolume(int controller, int zone, int volume) {
		/**
		 * 7.3.1 Set Volume
      Select the Volume for a particular zone using a discrete message.
      Byte # 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22
      Value F0 cc 00 7F 00 00 70 05 02 02 00 00 F1 21 00 ## 00 zz 00 01 xx F7
      cc = controller number -1
      zz = zone number -1
      xx = checksum
      Byte #16 = volume level (0x00 - 0x32, 0x00 = 0 Displayed … 0x32 = 100 Displayed)
		 */

		String cmd = "F0 cc 00 7F 00 00 70 05 02 02 00 00 F1 21 00 ## 00 zz 00 01 xx F7";

		cmd.replaceFirst("cc", Integer.toString(controller));
		cmd.replaceFirst("zz", Integer.toString(zone));
		cmd.replaceFirst("##", Integer.toString(volume/2));
		send(formatCommand(cmd));

	}

	public void sendZoneBassUpDown(int controller, int zone, boolean up) {
		/**
		 * 7.4 Bass
      Bass levels displayed on keypads range in value from -10 through +10. These Bess levels are
      represented in Decimal when using RS-232 messages ranging in value from 0 - 20. In the actual
      Data message the HEX value is used which would range in value from 0x00 – 0x14. This means
      (0x00 = 0 = -10, 0x01 = 1 = -9, 0x02 = 2 = -8 … 0x14 = 20 = +10).
      NOTE: The keypad displays will not automatically update for this change.
      7.4.1 Bass Up/Bass Down
      The following example shows the Bass level being increased or decreased.
      Byte # 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24
      Value F0 cc 00 7F 00 00 70 05 05 02 00 zz 00 00 00 ## 00 00 00 00 00 01 xx F7
      cc = controller number -1
      zz = zone number -1
      xx = checksum
      Byte #16 = button event (Plus = 0x69 = Increase, Minus = 0x6A = Decrease)
		 */
		String cmd = "F0 cc 00 7F 00 00 70 05 05 02 00 zz 00 00 00 ## 00 00 00 00 00 01 xx F7";
		cmd.replaceFirst("cc", Integer.toString(controller));
		cmd.replaceFirst("zz", Integer.toString(zone));
		cmd.replaceFirst("##", Integer.toString(up ? 0x69 : 0x6A));
		send(formatCommand(cmd));
	}


	public void sendZoneTrebbleUpDown(int controller, int zone, boolean up) {

		/**
		 * 7.5 Treble
      Treble levels displayed on keypads range in value from -10 through +10. These Treble levels are
      represented in Decimal when using RS-232 messages ranging in value from 0 - 20. In the actual
      Data message the HEX value is used which would range in value from 0x00 – 0x14. This means
      (0x00 = 0 = -10, 0x01 = 1 = -9, 0x02 = 2 = -8 … 0x14 = 20 = +10).
      NOTE: The keypad displays will not automatically update for this change.
      7.5.1 Treble Up/Treble Down
      The following example shows the Treble level being increased or decreased.
      Byte # 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24
      Value F0 cc 00 7F 00 00 70 05 05 02 00 zz 00 01 00 ## 00 00 00 00 00 01 xx F7
      cc = controller number -1
      zz = zone number -1
      xx = checksum
      Byte #16 = button event (Plus = 0x69 = Increase, Minus = 0x6A = Decrease)
		 */

		String cmd = "F0 cc 00 7F 00 00 70 05 05 02 00 zz 00 01 00 ## 00 00 00 00 00 01 xx F7";

		cmd.replaceFirst("cc", Integer.toString(controller));
		cmd.replaceFirst("zz", Integer.toString(zone));
		cmd.replaceFirst("##", Integer.toString(up ? 0x69 : 0x6A));
		send(formatCommand(cmd));
	}

	public void sendZoneLoudness(int controller, int zone, boolean on) {

		/**
		 * 7.6 Loudness
      Loudness is displayed on keypads as “On” or “Off”. Loudness can be toggled On or Off with a
      Plus or Minus command. There can also be a discrete On or Off command selected.
      NOTE: The keypad displays will not automatically update for this change.
      7.6.1 Loudness Toggle On/Off
      The following example shows how to toggle the Loudness “On” and “Off” for a particular Zone.
      Byte # 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24
      Value F0 cc 00 7F 00 00 70 05 05 02 00 zz 00 02 00 ## 00 00 00 00 00 01 xx F7
      cc = controller number -1
      zz = zone number -1
      xx = checksum
      Byte #16 = button event (PLUS = 0x69 = On/Off toggle) or (MINUS = 0x6A = On/Off toggle)

		 */
		String cmd = "F0 cc 00 7F 00 00 70 05 05 02 00 zz 00 02 00 ## 00 00 00 00 00 01 xx F7";

		cmd.replaceFirst("cc", Integer.toString(controller));
		cmd.replaceFirst("zz", Integer.toString(zone));
		cmd.replaceFirst("##", Integer.toString(on ? 0x69 : 0x6A));
		send(formatCommand(cmd));

	}

	public void sendZoneBalance(int controller, int zone, boolean left) {

		/**
		 * 7.7 Balance
      Balance levels displayed on keypads range in value from “Left 10” to “Center” to “Right 10”.
      These Balance levels are represented in Decimal when using RS-232 messages ranging in value
      from 0 - 20. In the actual Data message the HEX value is used which would range in value from
      0x00 – 0x14. This means (0x00 = 0 = Left 10 … 0x0A = 10 = Center … 0x14 = 20 = Right
      10).
      NOTE: The keypad displays will not automatically update for this change.
      7.7.1 Balance Left or Balance Right
      The following example shows the Balance level being drawn More Left or More Right.
      Byte # 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24
      Value F0 cc 00 7F 00 00 70 05 05 02 00 zz 00 03 00 ## 00 00 00 00 00 01 xx F7
      cc = controller number -1
      zz = zone number -1
      xx = checksum
      Byte #16 = button event (Plus = 0x69 = More Left, Minus = 0x6A = More Right)
		 */

		String cmd = "F0 cc 00 7F 00 00 70 05 05 02 00 zz 00 03 00 ## 00 00 00 00 00 01 xx F7";

		cmd.replaceFirst("cc", Integer.toString(controller));
		cmd.replaceFirst("zz", Integer.toString(zone));
		cmd.replaceFirst("##", Integer.toString(left ? 0x69 : 0x6A));
		send(formatCommand(cmd));
	}

	public void sendZoneTurnOnVolume(int controller, int zone, int volume) {
		/**
		 * 7.8.2 Set Turn On Volume
        Select the Turn On Volume level for a particular zone using a discrete message.
        Byte # 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24
        Value F0 cc 00 7F 00 00 70 00 05 02 00 zz 00 04 00 00 00 01 00 01 00 ## xx F7
        cc = controller number -1
        zz = zone number -1
        xx = checksum
        Byte #22 = Turn On Volume level (0x00 - 0x32, 0x00 = 0 … 0x32 = 100)
		 */

		String cmd = "F0 cc 00 7F 00 00 70 00 05 02 00 zz 00 04 00 00 00 01 00 01 00 ## xx F7";

		cmd.replaceFirst("cc", Integer.toString(controller));
		cmd.replaceFirst("zz", Integer.toString(zone));
		cmd.replaceFirst("##", Integer.toString(volume/2));
		send(formatCommand(cmd));
	}

	public void sendZoneTurnOnVolumeUpDown(int controller, int zone, boolean up) {
		/**
		 * 7.8 Turn On Volume
        Turn On Volume levels displayed on keypads range in value from 0 – 100 in steps of 2 (i.e. 0, 2,
        4 … 100). These Turn On Volume levels are represented in Decimal when using RS-232
        messages ranging in value from 0 - 50. In the actual Data message the HEX value is used which
        would range in value from 0x00 – 0x32. This means (0x00 = 0 = 0, 0x01 = 1 = 2, 0x02 = 2 = 4
        … 0x32 = 50 = 100)
        NOTE: The keypad displays will not automatically update for this change.
        7.8.1 Increase or Decrease Turn On Volume
        The following example shows the Turn On Volume level being increased or decreased.
        Byte # 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24
        Value F0 cc 00 7F 00 00 70 05 05 02 00 zz 00 04 00 bb 00 00 00 00 00 01 xx F7
        cc = controller number -1
        zz = zone number -1
        xx = checksum
		 */

		String cmd = "F0 cc 00 7F 00 00 70 05 05 02 00 zz 00 04 00 bb 00 00 00 00 00 01 xx F7";

		cmd.replaceFirst("cc", Integer.toString(controller));
		cmd.replaceFirst("zz", Integer.toString(zone));
		cmd.replaceFirst("bb", Integer.toString(up ? 0x69 : 0x6A));
		send(formatCommand(cmd));
	}

	public void sendZoneBackgroundColor(int controller, int zone, Color color) {
		/**
		 * 7.9 Background Color
      Background Color is displayed on keypads as “Amber”, “Green”, or “Off”. The Background
      Color can be toggled through these selections with a Plus or Minus command. There can also be
      a discrete command selected for each choice.
      NOTE: The keypad displays WILL automatically update for this selection.
      7.9.1 Background Color Off/Amber/Green toggle
      The following example shows how to toggle the Background Color for a particular Zone.
      Byte # 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24
      Value F0 00 00 7F 00 00 70 05 05 02 00 zz 00 05 00 ## 00 00 00 00 00 01 xx F7
      cc = controller number -1
      zz = zone number -1
      xx = checksum
      Byte #16 = button event (Plus = 0x69 = Off/Color toggle) or (Minus = 0x6A = Off/Color toggle)
		 */

		String cmd = "F0 00 00 7F 00 00 70 05 05 02 00 zz 00 05 00 ## 00 00 00 00 00 01 xx F7";

		cmd.replaceFirst("cc", Integer.toString(controller));
		cmd.replaceFirst("zz", Integer.toString(zone));
		cmd.replaceFirst("##", Integer.toString(color.getValue()));
		send(formatCommand(cmd));
	}

	public void sendZoneDoNotDisturb(int controller, int zone, boolean on) {
		/**
		 * 7.10.2 Set Do Not Disturb
      Turn DND On or Off for a particular zone using a discrete message.
      Byte # 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24
      Value F0 00 00 7F 00 00 70 00 05 02 00 zz 00 06 00 00 00 01 00 01 00 ## xx F7
      cc = controller number -1
      zz = zone number -1
      xx = checksum
      Byte #22 = Do Not Disturb setting (0x00 = OFF, 0x01 = ON )
		 */


		String cmd = "F0 00 00 7F 00 00 70 00 05 02 00 zz 00 06 00 00 00 01 00 01 00 ## xx F7";

		cmd.replaceFirst("cc", Integer.toString(controller));
		cmd.replaceFirst("zz", Integer.toString(zone));
		cmd.replaceFirst("##", Integer.toString(on ? 0x01 : 0x00));
		send(formatCommand(cmd));
	}

	public void sendPartyMode(int controller, int zone, PartyMode mode) {
		/**
		 * 7.11.2 Set Party Mode
        Select Party Mode “Master”, “On”, or “Off” for a particular zone using a discrete message.
        Byte # 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24
        Value F0 00 00 7F 00 00 70 00 05 02 00 zz 00 07 00 00 00 01 00 01 00 ## xx F7
        cc = controller number -1
        zz = zone number -1
        xx = checksum
        Byte #22 = Party Mode setting (0x00 = OFF, 0x01 = ON, 0x02 = Master)
		 */

		String cmd = "F0 00 00 7F 00 00 70 00 05 02 00 zz 00 07 00 00 00 01 00 01 00 ## xx F7";

		cmd.replaceFirst("cc", Integer.toString(controller));
		cmd.replaceFirst("zz", Integer.toString(zone));
		cmd.replaceFirst("##", Integer.toString(mode.getValue()));
		send(formatCommand(cmd));
	}

	public void displayString(int controller, int zone, int keypad, String string, boolean centered, short flashTimeMills) {
		/**
		 * 8.2 On A Specific Keypad
        The following example shows how to display a text message on a Specific Keypad in the system.
        This is accomplished by setting the Target Device ID for the particular keypad in question.
        Byte # 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24
        Value F0 cc zz kk 00 00 70 00 02 01 01 00 00 00 01 00 10 00 ## ## ## ## ## ##
        Byte # 25 26 27 28 29 30 31 32 33 34 35 36
        Value ## ## ## ## ## ## ## ## ## ## xx F7
        cc = Controller Number -1
        zz = Zone Number -1
        kk = Keypad Number -1
        xx = Checksum
		 */
		String cmd = "F0 cc zz kk 00 00 70 00 02 01 01 00 00 00 01 00 10 00 al tl th ## xx F7";

		byte [] time = new byte[2];
		time[0] = (byte)(flashTimeMills & 0xff);
		time[1] = (byte)((flashTimeMills >> 8) & 0xff);

		byte[] chars = new byte[13];
		byte[] tmpChars = null;

		try {
			tmpChars = string.substring(0, 12).getBytes("US-ASCII");
		} catch (UnsupportedEncodingException e) {
		}

		System.arraycopy(tmpChars, 0, chars, 0, tmpChars.length);    

		StringBuilder sb = new StringBuilder();
		for(byte b : chars)
			sb.append(String.format("%02x", b));

		cmd.replaceFirst("cc", Integer.toString(controller));
		cmd.replaceFirst("zz", Integer.toString(zone));
		cmd.replaceFirst("kk", Integer.toString(keypad));
		cmd.replaceFirst("al", Integer.toString(centered ? 0x00 : 0x01));
		cmd.replaceFirst("tl", Integer.toString(time[0]));
		cmd.replaceFirst("th", Integer.toString(time[1]));
		cmd.replaceFirst("##", sb.toString());
		send(formatCommand(cmd));

	}

	public void displayStringAll(String string, boolean centered, short flashTimeMills) {
		displayString(0x7F, 0,0,string,centered,flashTimeMills);
	}

	public void requestState(int controller, int zone) {
		/**
		 * 7.12 Get All Zone info
        As stated previously, a message can be used to request all of a particular Zone's parameter values
        at once. This can be very useful for updating panel displays. The following is an example of
        how to request Zone information for a particular Zone and what the return message would look
        like.
        7.12.1 Get State
        This is the Request message for the parameter values of the selected Zone.
        Byte # 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17
        Value F0 cc 00 7F 00 00 70 01 04 02 00 zz 07 00 00 xx F7
        cc = controller number -1
        zz = zone number -1
        xx = checksum
		 */

		String cmd = "0 cc 00 7F 00 00 70 01 04 02 00 zz 07 00 00 xx F7";

		cmd.replaceFirst("cc", Integer.toString(controller));
		cmd.replaceFirst("zz", Integer.toString(zone));
		send(formatCommand(cmd));
	}

	public RussoundZoneState processState(byte [] message) {
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

		return new RussoundZoneState(
				message[21] > 0x00, 
				message[22],
				message[23] * 2, 
				message[24], 
				message[25], 
				message[26] > 0, 
				message[27], 
				message[28] > 0,
				message[29] > 0,
				RussoundPartyMode.fromState(message[21]),
				message[30] > 0);

	}

	public void requestBackgroundColor(int controller, int zone){
		/**
		 * 7.9.3 GetBackgroundColor
		The current Background Color for a particular zone can be obtained using the following message.
		Byte# 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 
		Value F0 cc 00 7F 00 00 70 01 05 02 00 zz 00 05 00 00 xx F7
		cc = controller number -1 zz = zone number -1
		xx = checksum
		 */


		String cmd = "F0 cc 00 7F 00 00 70 01 05 02 00 zz 00 05 00 00 xx F7";

		cmd.replaceFirst("cc", Integer.toString(controller));
		cmd.replaceFirst("zz", Integer.toString(zone));
		send(formatCommand(cmd));
	}

	public RussoundBackgroundColor processBackgroundColor(byte[] message){
		/**
		 * 7.9.3 GetBackgroundColor
			The return message would look like the following.
			Byte# 1 2 3 4 5 6 7 8 9 1011121314151617181920212223 24
			Value F0 00 00 70 cc 00 7F 00 00 05 02 00 zz 00 05 00 00 01 00 01 00 ## xx F7
			cc = controller number -1
			zz = zone number -1
			xx = checksum
		Byte #22 = Background Color (0x00 = Off, 0x01 = Amber, 0x02 = Green)

		 */
		return RussoundBackgroundColor.fromState(message[22]);
	}

	public RussoundDirectDisplayFeedbackMessage  processDirectDisplayFeedbackMessage(byte[] message){
		/**
		 * 9.1 Reading Direct Display Feedback
			This section describes how to read Direct Display Feedback Messages. These Feedback messages are usually sent in direct response to a received command (e.g., If the current frequency of a Russound tuner is “102.7 MHz FM”, sending the “Frequency Up” command will trigger the Tuner to send a Display message back to the sender to update the frequency Display to "102.9 MHz FM"). The Direct Display Feedback message is sent directly to the Target Device ID of the message sender. The message can be displayed for a constant amount of time, or a "Flash" display with a specified length of time (Flash Time is in increments of 10ms).
			NOTE: It is possible that some display messages will include the special "Invert" control character (0xF1).
			NOTE: Some of the other bytes within this message may vary. Only the ones necessary to interpret the message are highlighted.
			NOTE: This message shows a text payload of 16 characters. Some devices may have a text payload of 12 characters.
			This is what the Direct Display Feedback message would look like using the above example. 
			Byte# 1  2  3  4  5  6  7  8  9  10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 
			Value F0 00 00 70 00 7D 00 00 02 01 01 02 01 01 00 00 01 00 14 00 01 00 00 31
			Byte  #25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 
			Value  30 32 2E 39 20 4D 48 7A 20 46 4D 00 00 00 00 00 7F F7

			Byte #2 – #4 = Target Device ID (This message should be displayed if the target Device ID matches the Device ID of your device.)
			Byte #19 = Overall Payload Size
			Byte #22 = Flash Time low byte (Flash time is in 10ms increments, 0x00 = Constant)
			Byte #23 = Flash Time high byte
			Byte #24 – #40 = Text (“102.9 MHz FM” used in above example) Byte #41 = Calculated Checksum
		 */

		byte[] tmpText = new byte[16];
		System.arraycopy(message, 24, tmpText, 0, message.length - 24);
		String text = null;
		try {
			text = new String(tmpText, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return new RussoundDirectDisplayFeedbackMessage(
				message[2],
				message[3],
				message[4],
				((message[22]&0xFF)<<8) | (message[22]&0xFF),
				text);
	}


	private byte[] formatCommand(String cmd) {
		//convert the checksum placeholder to something we can parse
		cmd.replaceFirst("zz", "00");

		//convert to bytes
		byte[] bytes = hexStringToByteArray(cmd);

		//modify the checksum
		calculateChecksum(bytes);

		return bytes;
	}

	/**
	 * http://stackoverflow.com/questions/140131/convert-a-string-representation-of-a-hex-dump-to-a-byte-array-using-java
	 * @param hex string
	 * @return byte array
	 */
	public static byte[] hexStringToByteArray(String s) {
		s.replaceAll(" ", "");
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
					+ Character.digit(s.charAt(i+1), 16));
		}
		return data;
	}

	/**
	 * Calculates and replaces the checksum byte with the other values in the array
	 * 
	 * @param full russound message 
	 */
	public void calculateChecksum(byte[] bytes) {
		/**
		 * Step #1 - Add the HEX value of every byte in the message that precedes the Checksum:
        Example - 0xF0 + 0x00 + 0x67 + 0x7C + 0xF1 + 0x0F = 0x02D3

        Step #2 - Count the number of bytes which precede the Checksum and convert that value from
        DEC to HEX (byte count). Add the byte count in HEX to the previously calculated sum of bytes:
        Example - 0x02D3 + 6 (6 = Decimal value byte count) = 0x02D9

        Step #3 - This value is then AND-ed with the HEX value 0x007F (7F is the highest BIN value
        for 7 bits = 1111111). The Checksum itself and the End of Message Character are not
        included in the calculation. Only the low 7 bits are used so overflow is discarded:
        Example - 0x02D9 AND 0x007F = 0x59 = Checksum
		 */

		int len = bytes.length;
		int total = 0;

		//step 1
		for(int i=0; i< len -2; i++)
			total += bytes[i];

		total += len -2;

		//setp 2
		int checksum = total & 0x007F;

		//step 3
		bytes[len -2] = (byte)checksum;

	}



	public enum Color {
		OFF(0x00),
		AMBER(0x01),
		GREEN(0x02);

		private int value = 0;

		Color(int value){
			this.value = value;
		}
		public int getValue() {
			return value;
		}

	};

	public enum PartyMode {
		OFF(0x00),
		ON(0x01),
		MASTER(0x03);

		private int value = 0;

		PartyMode(int value){
			this.value = value;
		}

		public int getValue() {
			return value;
		}

	};
}
