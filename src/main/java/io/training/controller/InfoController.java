package io.training.controller;

import io.training.TaskConfigurationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



// tu testujemy jedynie wyciąganie wartości z pliku application.properties/application.properties.
@RestController
public class InfoController {
    // Trzy rodzaje pobierania propsów z pliku
    // Możemy wstrzyknąć poprzez podanie pełnej nazwy właściwości
    @Value("${my.prop}")
    private String myProp;
    // Możemy też wstrzyknąć istniejące propsy poprzez istniejące dla nich klasy - nie jest to rekomendowane bo ściśle powiązany ze Spring
    @Autowired
    private DataSourceProperties dataSource;
    // Tu wstrzykujemy swoją property przy użyciu TaskConfigurationProperties - bez @Autowired ale za to z konstruktorem - rekomendowany sposób
    private TaskConfigurationProperties taskProp;

    InfoController(final TaskConfigurationProperties taskProp) {
        this.taskProp = taskProp;
    }

    @GetMapping("/info/url")
    String url(){
        return dataSource.getUrl(); // musimy jeszcze wyciągnać getUrl() z klasy DataSourceProperties
    }

    @GetMapping("/info/prop")
    String myProp(){
        return myProp;
    }

    @GetMapping("/info/taskProp")
    boolean isAllowMultipleTasksFromTemplate(){
        return taskProp.isAllowMultipleTasksFromTemplate();
    }
}
