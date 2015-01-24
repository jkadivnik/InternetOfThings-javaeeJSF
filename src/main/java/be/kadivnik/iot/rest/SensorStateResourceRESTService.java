package be.kadivnik.iot.rest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import be.kadivnik.iot.data.SensorStateDAO;
import be.kadivnik.iot.model.Device;
import be.kadivnik.iot.model.SensorState;
import be.kadivnik.iot.service.SensorStateRegistrationService;

@Path("/sensorstates")
@RequestScoped
public class SensorStateResourceRESTService {

    @Inject
    private Logger log;

    @Inject
    private Validator validator;

    @Inject
    private SensorStateRegistrationService registration;
    
    @Inject
    private SensorStateDAO repository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<SensorState> listAllDevices() {
        return repository.findAllOrderedByDeviceAndName();
    }

    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public SensorState lookupSensorStateById(@PathParam("id") long id) {
        SensorState sensorState = repository.findById(id);
        if (sensorState == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return sensorState;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createSensorState(SensorState sensorState) {

        Response.ResponseBuilder builder = null;

        try {
            // Validates member using bean validation
            validateSensorState(sensorState);

            registration.register(sensorState);

            // Create an "ok" response
            builder = Response.ok();
        } catch (ConstraintViolationException ce) {
            // Handle bean validation issues
            builder = createViolationResponse(ce.getConstraintViolations());
        } catch (ValidationException e) {
            // Handle the unique constrain violation
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("name_device", "This combination is taken");
            builder = Response.status(Response.Status.CONFLICT).entity(responseObj);
        } catch (Exception e) {
            // Handle generic exceptions
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();
    }

    /**
     * <p>
     * Validates the given {@link SensorState} variable and throws validation exceptions based on the type of error. If the error is standard
     * bean validation errors then it will throw a ConstraintValidationException with the set of the constraints violated.
     * </p>
     * <p>
     * If the error is caused because an existing device with the same name is registered it throws a regular validation
     * exception so that it can be interpreted separately.
     * </p>
     * 
     * @param sensorState SensorState to be validated
     * @throws ConstraintViolationException If Bean Validation errors exist
     * @throws ValidationException If sensorState with the same unique fields already exists
     */
    private void validateSensorState(SensorState sensorState) throws ConstraintViolationException, ValidationException {
        // Create a bean validator and check for issues.
        Set<ConstraintViolation<SensorState>> violations = validator.validate(sensorState);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
        }

        // Check the uniqueness of the email address
        if (sensorStateAlreadyExists(sensorState.getName(), sensorState.getDevice())) {
            throw new ValidationException("Unique SensorState Violation");
        }
    }

    /**
     * Creates a JAX-RS "Bad Request" response including a map of all violation fields, and their message. This can then be used
     * by clients to show violations.
     * 
     * @param violations A set of violations that needs to be reported
     * @return JAX-RS response containing all violations
     */
    private Response.ResponseBuilder createViolationResponse(Set<ConstraintViolation<?>> violations) {
        log.fine("Validation completed. violations found: " + violations.size());

        Map<String, String> responseObj = new HashMap<String, String>();

        for (ConstraintViolation<?> violation : violations) {
            responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
        }

        return Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
    }

    /**
     * Checks if a sensorstate with the same name and linked to the same device is already registered. This is the only way to easily capture the
     * "@UniqueConstraint(columnNames = "name,device")" constraint from the SensorState class.
     * 
     * @param name The name to check
     * @return True if the name already exists, and false otherwise
     */
    public boolean sensorStateAlreadyExists(String name, Device device) {
        SensorState sensorState = null;
        try {
            sensorState = repository.findByNameAndDevice(name, device);
        } catch (NoResultException e) {
            // ignore
        }
        return sensorState != null;
    }
}
