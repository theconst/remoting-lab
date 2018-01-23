package ua.kpi.apeps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * Server that runs on both RMI and web endpoints
 */
@SpringBootApplication
public class Server {

    public static void main(String... args) {
        ApplicationContext context = SpringApplication.run(Server.class, args);
        int rmiPort = Integer.valueOf(context.getEnvironment().getProperty("rmi.port"));

        /* not using spring rmi to make the application explicit */
//        RMIServer.run(
//                context.getBean("employee-shift-record-repository",
//                        context.getBean("")
//                        rmiPort);
    }
}
