package be.kadivnik.iot.service.mqtt;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

@Startup
@Singleton
public class MqttSubscribeService implements MqttCallback  {

	@Inject
	private Logger log; 

	@Inject
	private MqttMessageFactory mqttMessageFactory;
	
	private MqttClient mqttClient;
	
	@PostConstruct
	public void connect() {
		try {
			mqttClient = new MqttClient("tcp://192.168.1.102:1883", "Subscriber");
			mqttClient.setCallback(this);
			mqttClient.connect();
			mqttClient.subscribe("status", 0);
		} catch (MqttException e) {
			log.log(Level.SEVERE, "MqttClient Connection failed : " + e.getStackTrace());
			
		}
	}
	
	@Override
	public void connectionLost(Throwable throwable) {
		log.log(Level.SEVERE, "Connection lost to the mqtt server : " + throwable.getStackTrace());
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		log.log(Level.INFO, "A new message has arrived!");
		MqttMessageService mqttMessageService = mqttMessageFactory.getMessageService(topic);
		
		mqttMessageService.handleMessage(message.getPayload().toString());
	}

}
