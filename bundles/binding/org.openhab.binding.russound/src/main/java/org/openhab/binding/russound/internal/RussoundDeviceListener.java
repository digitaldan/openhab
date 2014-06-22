package org.openhab.binding.russound.internal;

import org.openhab.binding.russound.internal.messages.RussoundDirectDisplayFeedbackMessage;
import org.openhab.binding.russound.internal.messages.RussoundMultiFieldSourceBroadcastDisplayMessage;
import org.openhab.binding.russound.internal.messages.RussoundSourceBroadcastDisplayMessage;
import org.openhab.binding.russound.internal.messages.RussoundZoneStateMessage;

public interface RussoundDeviceListener {
	public void handleDirectDisplayFeedbackMessage(RussoundDirectDisplayFeedbackMessage message);
	public void handleSourceBroadcastDisplayMessage(RussoundSourceBroadcastDisplayMessage message);
	public void handleMultiFieldSourceBroadcastDisplayMessage (RussoundMultiFieldSourceBroadcastDisplayMessage message);
	public void handleZoneStateMessage(RussoundZoneStateMessage message);
}
