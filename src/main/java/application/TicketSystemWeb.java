package application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "controllers")
public class TicketSystemWeb {

    public static void main(String[] args) {
        SpringApplication.run(TicketSystemWeb.class, args);
    }

    public TicketSystemWeb(){

    }
}
