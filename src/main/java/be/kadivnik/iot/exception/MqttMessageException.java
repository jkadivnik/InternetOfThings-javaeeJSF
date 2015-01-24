package be.kadivnik.iot.exception;

public class MqttMessageException extends Exception {

	private static final long serialVersionUID = 6640790429713636475L;

	public MqttMessageException(String message, Throwable throwable) {
		super(message, throwable);
	}
	
	public MqttMessageException(String message) {
		super(message);
	}
}
