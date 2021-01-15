package com.mondris.pastebinapp.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@SpringBootApplication
public class PastebinAPpApplication {

    public static void main(String[] args) {
        SpringApplication.run(PastebinAPpApplication.class, args);
    }

}
