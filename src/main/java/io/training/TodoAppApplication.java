package io.training;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.Validator;

@SpringBootApplication
public class TodoAppApplication {

    public static void main (String[] args) {
        SpringApplication.run(TodoAppApplication.class, args);
    }

    @Bean // zmieniliśmy validator na validator javax, bo ten springowy tak czy inaczej javax rozszerza
    Validator validator() { // walidator, kóry zamiast 500 internal error zwraca 400 Bad Status
        return new LocalValidatorFactoryBean(); //oraz json z polem którego dotyczy walidacja i message dla niego (w tym przypadku zrobione dla tasks -> description)
    }
}
