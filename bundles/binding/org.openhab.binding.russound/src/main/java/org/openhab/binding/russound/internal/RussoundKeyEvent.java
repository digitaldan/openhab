package org.openhab.binding.russound.internal;

public enum RussoundKeyEvent {

  Button1(0x01),
  Button2(0x02),
  Button3(0x03),
  Button4(0x04),
  Button5(0x05),
  Button6(0x06),
  Button7(0x07),
  Button8(0x08),
  Button9(0x09),
  Button0(0x0a),
  VolumeUp(0x0b),
  VolumeDown(0x0c),
  MuteZone(0x0d),
  ChannelUp(0x0e),
  ChannelDown(0x0f),
  Power(0x10),
  Enter(0x01),
  PreviousChannel(0x12),
  TVVideo(0x13),
  TVVCR(0x14),
  AB(0x15),
  TVDVD(0x16),
  TVLD(0x17),
  Input(0x18),
  TVDSS(0x19),
  Play(0x1a),
  Stop(0x1b),
  SearchForward(0x1c),
  SearchRewind(0x1d),
  Pause(0x1e),
  Record(0x1f),
  Menu(0x20),
  MenuUp(0x21),
  MenuDown(0x22),
  MenuLeft(0x23),
  MenuRight(0x24),
  Select(0x25),
  Exit(0x26),
  Display(0x27),
  Guide(0x28),
  PageUp(0x29),
  PageDown(0x2a),
  Disk(0x2b),
  Plus(0x2c),
  OpenClose(0x2d),
  Random(0x2e),
  TrackForward(0x2f),
  TrackReverse(0x30),
  SurroundOnOff(0x31),
  SurroundMode(0x32),
  SurroundUp(0x33),
  SurroundDown(0x34),
  PIP(0x35),
  PIPMove(0x36),
  PIPSwap(0x37),
  Program(0x38),
  Sleep(0x39),
  On(0x3a),
  Off(0x3b),
  Eleven(0x3c),
  Twelve(0x3d),
  Thirteen(0x3e),
  Fourteen(0x3f),
  Fithteen(0x40),
  Sixteen(0x41),
  Bright(0x42),
  Dim(0x43),
  Close(0x44),
  Open(0x45),
  Stop2(0x46),
  AMFM(0x47),
  Cue(0x48),
  DiskUp(0x49),
  DiskDown(0x4a),
  Info(0x4b),

  KeypadSetupButton(0x64,true),
  KeypadPrevious(0x67,true),
  KeypadNext(0x68,true),
  KeypadPlus(0x69,true),
  KeypadMinus(0x6a,true),
  KeypadSourceToggle(0x6b,true),
  KeypadPower(0x6c,true),
  KeypadStop(0x6d,true),
  KeypadPause(0x6e,true),
  KeypadFavorite1(0x6f,true),
  KeypadFavorite2(0x70,true),
  KeypadPlay(0x73,true),
  KeypadVolumeUp(0x7f,true),
  KeypadVolumeDown (0x08,true);

  private int value = 0;
  private boolean keypad = false;

  RussoundKeyEvent(int value){
    this.value = value;
  }
  RussoundKeyEvent(int value,boolean keypad){
    this.value = value;
    this.keypad = keypad;
  }

  public int getValue() {
    return value;
  }
  
  public int getValueInverted() {
    return ~value & 0xff; 
  }

  public boolean isKeypad() {
    return keypad;
  }

  public boolean invert() {
    return getValue() > 0x7f;
  }
}
