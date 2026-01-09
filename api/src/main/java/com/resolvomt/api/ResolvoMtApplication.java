package com.resolvomt.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ResolvoMtApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResolvoMtApplication.class, args);
    }
}
