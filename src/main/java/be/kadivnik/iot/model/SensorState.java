package be.kadivnik.iot.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@XmlRootElement
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"device", "name"}))
public class SensorState implements Serializable {

	private static final long serialVersionUID = 2819766402314278015L;

	@Id
    @GeneratedValue
    private Long id;

    @ManyToOne()
    @JoinColumn(name="device")
    private Device device;
    
    @NotNull
    @NotEmpty
    private String name; 

    @NotNull
    @NotEmpty
    private String sensorType;
    
    private String sensorValue;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSensorType() {
		return sensorType;
	}

	public void setSensorType(String sensorType) {
		this.sensorType = sensorType;
	}

	public String getSensorValue() {
		return sensorValue;
	}

	public void setSensorValue(String sensorValue) {
		this.sensorValue = sensorValue;
	} 
}
