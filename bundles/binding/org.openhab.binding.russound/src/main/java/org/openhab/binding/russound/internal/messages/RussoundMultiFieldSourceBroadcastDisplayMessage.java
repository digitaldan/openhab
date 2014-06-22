package org.openhab.binding.russound.internal.messages;

public class RussoundMultiFieldSourceBroadcastDisplayMessage{
	private int source;
	private int fieldId;
	private String text;
	
	
	public RussoundMultiFieldSourceBroadcastDisplayMessage(int source, int fieldId, String text) {
		this.source = source;
		this.fieldId = fieldId;
		this.text = text;
	}
	public int getSource() {
		return source;
	}
	public void setSource(int source) {
		this.source = source;
	}
	public int getFieldId() {
		return fieldId;
	}
	public void setFieldId(int fieldId) {
		this.fieldId = fieldId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}
