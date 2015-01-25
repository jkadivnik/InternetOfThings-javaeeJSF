package be.kadivnik.iot.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import be.kadivnik.iot.model.Device;
import be.kadivnik.iot.service.DeviceService;

@Model
public class DeviceController extends BaseController {

    @Inject
    private DeviceService deviceService;
    
    @Produces
    @Named
    private Device newDevice;

    @Produces
    @Named
	private Device selectedDevice;

    @PostConstruct
    public void initDeviceController() {
        initNewDevice();
        initSelectedDevice();
    }
    
    public void initNewDevice() {
    	newDevice = new Device();
    }
    
    public void initSelectedDevice() {
    	selectedDevice = new Device();
    }

    @Override
    public void create() {
        try {
        	deviceService.create(newDevice);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Created!", "Creation successful");
            getFacesContext().addMessage(null, m);
            initNewDevice();
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Creation unsuccessful");
            getFacesContext().addMessage(null, m);
        }
    }

	@Override
	public void update() {
		try {
			deviceService.update(selectedDevice);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Updated!", "Update successful");
            getFacesContext().addMessage(null, m);
            initSelectedDevice();
		} catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Update unsuccessful");
            getFacesContext().addMessage(null, m);
		}
	}

	@Override
	void delete() {
		try {
			deviceService.delete(selectedDevice);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Deleted!", "Delete successful");
            getFacesContext().addMessage(null, m);
            initSelectedDevice();
		} catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Delete unsuccessful");
            getFacesContext().addMessage(null, m);
		}
	}
}
