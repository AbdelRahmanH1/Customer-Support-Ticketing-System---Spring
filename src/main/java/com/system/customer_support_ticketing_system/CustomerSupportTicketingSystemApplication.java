package com.system.customer_support_ticketing_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class CustomerSupportTicketingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerSupportTicketingSystemApplication.class, args);
    }

}
