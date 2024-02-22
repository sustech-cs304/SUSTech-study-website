package com.example.mvcdemo2;

import com.example.mvcdemo2.service.StudentService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Mvcdemo2Application {
    private static final Logger LOGGER = LoggerFactory.getLogger(Mvcdemo2Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Mvcdemo2Application.class, args);
        LOGGER.info("Customized INFO log {}", 1);
        LOGGER.debug("Customized DEBUG log {}", 2);
        LOGGER.error("Customized ERROR log {}", 3);
        LOGGER.trace("Customized TRACE log {}", 4);
        //SpringApplication.run(Mvcdemo2Application.class, args);
    }


}
