/**
 * Copyright (c) 2010-2014, openHAB.org and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.russound.internal;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.openhab.binding.russound.RussoundBindingProvider;
import org.openhab.binding.russound.internal.messages.RussoundDirectDisplayFeedbackMessage;
import org.openhab.binding.russound.internal.messages.RussoundMultiFieldSourceBroadcastDisplayMessage;
import org.openhab.binding.russound.internal.messages.RussoundSourceBroadcastDisplayMessage;
import org.openhab.binding.russound.internal.messages.RussoundZoneStateMessage;
import org.apache.commons.lang.StringUtils;
import org.openhab.core.binding.AbstractActiveBinding;
import org.openhab.core.types.Command;
import org.openhab.core.types.State;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
	

/**
 * Implement this class if you are going create an actively polling service
 * like querying a Website/Device.
 * 
 * @author Dan Cunningham
 * @since 1.5.0
 */
public class RussoundBinding extends AbstractActiveBinding<RussoundBindingProvider> implements ManagedService {

	private static final Logger logger = 
		LoggerFactory.getLogger(RussoundBinding.class);

	HashMap<String,RussoundDevice> controllers;
	
	/** 
	 * the refresh interval which is used to poll values from the Russound
	 * server (optional, defaults to 60000ms)
	 */
	private long refreshInterval = 5000;
	
	
	public RussoundBinding() {
		controllers = new HashMap<String,RussoundDevice>();
	}
		
	
	public void activate() {
	}
	
	public void deactivate() {
		// deallocate resources here that are no longer needed and 
		// should be reset when activating this binding again
	}

	
	/**
	 * @{inheritDoc}
	 */
	@Override
	protected long getRefreshInterval() {
		return refreshInterval;
	}

	/**
	 * @{inheritDoc}
	 */
	@Override
	protected String getName() {
		return "Russound Refresh Service";
	}
	
	/**
	 * @{inheritDoc}
	 */
	@Override
	protected void execute() {
		// the frequently executed code (polling) goes here ...
		logger.debug("execute() method is called!");
		/**
		 * we need to request zone and background state here.
		 */
	}

	/**
	 * @{inheritDoc}
	 */
	@Override
	protected void internalReceiveCommand(String itemName, Command command) {
		// the code being executed when a command was sent on the openHAB
		// event bus goes here. This method is only called if one of the 
		// BindingProviders provide a binding for the given 'itemName'.
		logger.debug("internalReceiveCommand() is called!");
	}
	
	/**
	 * @{inheritDoc}
	 */
	@Override
	protected void internalReceiveUpdate(String itemName, State newState) {
		// the code being executed when a state was sent on the openHAB
		// event bus goes here. This method is only called if one of the 
		// BindingProviders provide a binding for the given 'itemName'.
		logger.debug("internalReceiveCommand() is called!");
	}
		
	/**
	 * @{inheritDoc}
	 */
	@Override
	public void updated(Dictionary<String, ?> config) throws ConfigurationException {
		
		stopAll();
		controllers.clear();
		
		if (config != null) {
			
			// to override the default refresh interval one has to add a 
			// parameter to openhab.cfg like <bindingName>:refresh=<intervalInMs>
			String refreshIntervalString = (String) config.get("refresh");
			if (StringUtils.isNotBlank(refreshIntervalString)) {
				refreshInterval = Long.parseLong(refreshIntervalString);
			}
			
			Enumeration<String> keys = config.keys();
	        while (keys.hasMoreElements()) {
	            
	            String key = (String) keys.nextElement();
	            //look for name.hosts
	            if(key.endsWith(".host")){
	            	String entry = (String)config.get(key);
	            	String[] entries = entry.split("\\.");
	            	if(entries.length == 2){
	            		String name = entries[0];
	            		String host = (String)config.get(key);
	            		String port = (String)config.get(name + ".port");
						RussoundDevice device = new RussoundDevice(
								new RussoundNetworkConnection(host,
										Integer.parseInt(port)),
								new RussoundDeviceListenerImpl());
						controllers.put(name,device);
						device.start();
	            	}
	            }
	            
	            if(key.endsWith(".serialDevice")){
	            	String entry = (String)config.get(key);
	            	String[] entries = entry.split("\\.");
	            	if(entries.length == 2){
		            	String name = entries[0];
	            		String dev = (String)config.get(key);
	            		RussoundDevice device = new RussoundDevice(
								new RussoundSerialConnection(dev),
								new RussoundDeviceListenerImpl());
	            		controllers.put(name,device);
	            		device.start();
	            	}
	            }
	        }
	        
	        
	
			
			
			setProperlyConfigured(controllers.size() > 0);
		}
	}
	
	/**
	 * Stop all russound connections
	 */
	private void stopAll(){
		for(RussoundDevice device : controllers.values()){
				device.shutdown();
		}
	}
	
	class RussoundDeviceListenerImpl implements RussoundDeviceListener{

		@Override
		public void handleDirectDisplayFeedbackMessage(
				RussoundDirectDisplayFeedbackMessage message) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void handleSourceBroadcastDisplayMessage(
				RussoundSourceBroadcastDisplayMessage message) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void handleMultiFieldSourceBroadcastDisplayMessage(
				RussoundMultiFieldSourceBroadcastDisplayMessage message) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void handleZoneStateMessage(RussoundZoneStateMessage message) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
