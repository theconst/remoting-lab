package ua.kpi.apeps.server.rest;

import lombok.AllArgsConstructor;
import ua.kpi.apeps.model.Employee;
import ua.kpi.apeps.repository.EmployeeRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.Collection;

@Path("/employees")
@Produces(MediaType.TEXT_PLAIN)
@AllArgsConstructor
public class EmployeeController {

    EmployeeRepository repository;

    @GET
    public String getAllEmployees() {
        try {
            return repository.getAll().toString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @GET
    @Path("{id}")
    public String getById(@PathParam("id") Integer id) {
        try {
            return repository.getById(id).toString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
