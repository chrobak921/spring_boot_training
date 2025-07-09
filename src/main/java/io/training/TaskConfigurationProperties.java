package io.training;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

// Ma na sobie @Configuration
@Configuration // trzeba jeszcze narzucić hierarchie z  @Configuration żeby działało
@ConfigurationProperties("task") // prefix ustawiony na "task" tak jak w application.properties
public class TaskConfigurationProperties {
    private boolean allowMultipleTasksFromTemplate;

    public boolean isAllowMultipleTasksFromTemplate() {
        return allowMultipleTasksFromTemplate;
    }

    public void setAllowMultipleTasksFromTemplate(boolean allowMultipleTasksFromTemplate) {
        this.allowMultipleTasksFromTemplate = allowMultipleTasksFromTemplate;
    }
}
