package ua.kpi.apeps.server.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestServer {

    public static void main(String... args) {
        SpringApplication.run(RestServer.class, args);
    }
}
