package org.openhab.binding.russound.internal;

import org.openhab.binding.russound.internal.messages.RussoundDirectDisplayFeedbackMessage;
import org.openhab.binding.russound.internal.messages.RussoundMultiFieldSourceBroadcastDisplayMessage;
import org.openhab.binding.russound.internal.messages.RussoundSourceBroadcastDisplayMessage;
import org.openhab.binding.russound.internal.messages.RussoundZoneStateMessage;

/**
 * Listener interface for Russound incoming messages
 * @author daniel
 *
 */
public interface RussoundDeviceListener {
	/**
	 * Handler for Direct Display Feedback Messages
	 * @param message
	 */
	public void handleDirectDisplayFeedbackMessage(RussoundDirectDisplayFeedbackMessage message);
	/**
	 * Handler for Source Broadcast Display Messages
	 * @param message
	 */
	public void handleSourceBroadcastDisplayMessage(RussoundSourceBroadcastDisplayMessage message);
	/**
	 * Handler for Multi Field Source Broadcast Display Messages
	 * @param message
	 */
	public void handleMultiFieldSourceBroadcastDisplayMessage (RussoundMultiFieldSourceBroadcastDisplayMessage message);
	/**
	 * Handler for Zone State Messages
	 * @param message
	 */
	public void handleZoneStateMessage(RussoundZoneStateMessage message);
}
