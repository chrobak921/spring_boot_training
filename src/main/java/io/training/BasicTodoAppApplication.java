//package io.training;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
//import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
//import org.springframework.validation.Validator;
//import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
//
//// Tej aplikacji używaliśmy w początkowym stadium gdy używaliśmy Spring Data Rest Repositoru - w połączenieu z Basic controller i repositorry
//@SpringBootApplication
//public class BasicTodoAppApplication implements RepositoryRestConfigurer { // RepositoryRestConfigurer dodaje np listenery do Spring
//
//	public static void main (String[] args) {
//		SpringApplication.run(BasicTodoAppApplication.class, args);
//	}
//
//	@Bean
//	Validator validator() { // walidator, kóry zamiast 500 internal error zwraca 400 Bad Status
//		return new LocalValidatorFactoryBean(); //oraz json z polem którego dotyczy walidacja i message dla niego (w tym przypadku zrobione dla tasks -> description)
//	}
//
//	@Override
//	public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener validatingListener) { // listener do użycia walidatora
//		validatingListener.addValidator("beforeCreate", validator());
//		validatingListener.addValidator("beforeSave", validator());
//	}
//}
