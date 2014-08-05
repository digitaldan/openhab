package org.openhab.binding.russound.internal;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.openhab.core.binding.BindingConfig;

public class RussoundBindingConfig implements BindingConfig{

	private String device;
	private RussoundItemType type;
	private String params;
	
	private final Pattern ZONE_PARAM_PATTERN = Pattern.compile("^(.*):(number)$");
	/**
	 * If the param String contains a controller number return that, otherwise
	 * return -1
	 * @return the controller number from our params or -1 if none
	 */
	public int getController(){
		if(StringUtils.isNotBlank(params)){
			//command:controller:keypad
		}
		return -1;
	}
	
	public int getZone(){
		return -1;
	}
	
	public int getKeypad(){
		return -1;
	}
	
	public RussoundItemType getRussoundItemType(){
		return type;
	}
	
	public String getParams(){
		return params;
	}
	
}
