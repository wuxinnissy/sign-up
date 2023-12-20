package com.n1ssy2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class SignServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SignServerApplication.class, args);
        log.info("server started");
    }

}
