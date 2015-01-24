package be.kadivnik.iot.util;

public enum MqttMessageTopicEnum {

	STATUS ("status"), 
	KEEPALIVE ("keepalive");
	
	private String topic;
	
	private MqttMessageTopicEnum(String topic) {
		this.topic = topic;
	}
	
	public String getTopic() {
		return topic;
	}
}
