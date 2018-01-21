package ua.kpi.apeps.server.rest;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import ua.kpi.apeps.repository.EmployeeRepository;
import ua.kpi.apeps.repository.rmi.EmployeeRepositoryFactory;
import ua.kpi.apeps.repository.rmi.EmployeeRepositoryStub;

import static ua.kpi.apeps.repository.rmi.EmployeeRepositoryFactory.Modes.STUB;

public class JerseyServer extends AbstractBinder {

    public static final String CLASSNAMES = "jersey.config.server.provider.classnames";

    public static void main(String[] args) {
        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        handler.addServlet(new ServletHolder(new ServletContainer(config())), "/*");
        handler.setContextPath("/");

        Server jettyServer = new Server(8080);
        jettyServer.setHandler(handler);

        try {
            jettyServer.start();
            jettyServer.join();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            jettyServer.destroy();
        }
    }

    @Override
    protected void configure() {
        bind(EmployeeRepositoryStub.class).to(EmployeeController.class);
    }

    private static ResourceConfig config() {
        EmployeeRepository repository = EmployeeRepositoryFactory.createRepository(STUB);

        return new ResourceConfig()
                .register(new EmployeeController(repository));
    }
}
