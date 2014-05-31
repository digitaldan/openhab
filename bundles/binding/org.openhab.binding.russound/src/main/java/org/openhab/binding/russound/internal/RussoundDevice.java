package org.openhab.binding.russound.internal;

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
  
  public void sendKeypadEvent() {
    
  }
  
  public void sendSourceControlEvents() {
    
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
  
  public void displayString() {
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
  }
  
  public void displayMessages() {
    
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
