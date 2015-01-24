package be.kadivnik.iot.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import be.kadivnik.iot.model.Device;
import be.kadivnik.iot.service.DeviceRegistrationService;

@Model
public class DeviceController extends BaseController {

    @Inject
    private DeviceRegistrationService deviceRegistration;
    
    @Produces
    @Named
    private Device newDevice;

    @PostConstruct
    public void initNewMember() {
        newDevice = new Device();
    }

    public void register() {
        try {
        	deviceRegistration.register(newDevice);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful");
            getFacesContext().addMessage(null, m);
            initNewMember();
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
            getFacesContext().addMessage(null, m);
        }
    }
}
