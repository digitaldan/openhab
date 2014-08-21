package org.openhab.binding.russound.internal;

import java.util.HashMap;
import java.util.Map;


public enum RussoundItemType {
	ZONE_POWER("zone_power"),
	ZONE_SOURCE("zone_source"),
	ZONE_VOLUME("zone_volume"),
	ZONE_BASS("zone_bass"),
	ZONE_TREBLE("zone_treble"),
	ZONE_LOUDNESS("zone_loudness"),
	ZONE_BALANCE("zone_balance"),
	ZONE_SYSTEMON("zone_systemon"),
	ZONE_SHAREDSOURCE("zone_sharedsource"),
	ZONE_PARTYMODE("zone_partymode"),
	ZONE_DND("zone_dnd"),
	ZONE_KEY("zone_key"),
	ZONE_TURN_ON_VOLUME("zone_turn_on_volume"),
	ZONE_BACKGROUND_COLOR("zone_background_color"),
	ALL_ZONES_POWER("all_zones_power"),
	KEYPAD_DISPLAY_STRING("keypad_display_string"),
	ALL_KEYPADS_DISPLAY_STRING("all_keypads_display_string");
	
	private String label;
	private static Map<String, RussoundItemType> labelToRussoundItemType;
	
	RussoundItemType(String label){
		this.label = label;
	}
	
	/**
	 * Lookup function based on the binding type label.
	 * Returns null if the binding type is not found.
	 * @param label the label to lookup
	 * @return enumeration value of the binding type.
	 */
	public static RussoundItemType getRussoundItemType(String label) {
		if (labelToRussoundItemType == null) {
			initMapping();
		}
		return labelToRussoundItemType.get(label);
	}
	
	private static void initMapping() {
		labelToRussoundItemType = new HashMap<String, RussoundItemType>();
		for (RussoundItemType s : values()) {
			labelToRussoundItemType.put(s.label, s);
		}
	}
}
