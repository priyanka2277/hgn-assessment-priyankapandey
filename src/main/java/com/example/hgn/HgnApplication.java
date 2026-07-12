package com.example.hgn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HgnApplication {

    public static void main(String[] args) {
        SpringApplication.run(HgnApplication.class, args);
    }

}
