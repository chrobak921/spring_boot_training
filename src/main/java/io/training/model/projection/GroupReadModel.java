package io.training.model.projection;

import io.training.model.Task;
import io.training.model.TaskGroup;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

public class GroupReadModel {
    private String description;
    /**
    * Deadline dla ostatniego taska w grupie
    */
    private LocalDateTime deadline;
    private Set<GroupTaskReadModel> tasks;

    public GroupReadModel(TaskGroup source) {
        description = source.getDescription(); // pobieramy description dla TaskGroup
        source.getTasks().stream()  // pobieramy Set tasków
                .map(Task::getDeadline)  // z każdego taska pobieramy deadline
                .max(LocalDateTime::compareTo)  // pobieramy najwyższą date i zwracamy Optional
                .ifPresent(date -> deadline = date); // jeśli jest wynik to przypisujemy go do deadline
        tasks = source.getTasks().stream()
                .map(GroupTaskReadModel::new) // pobieramy taski i twotrzymy za ich pomocą GroupTaskReadModel
                .collect(Collectors.toSet()); // wrzucamy każdy GroupTaskReadModel do Set
    }

    String getDescription() {
        return description;
    }

     public void setDescription(final String description) {
        this.description = description;
    }

     public LocalDateTime getDeadline() {
        return deadline;
    }

     public void setDeadline(final LocalDateTime deadline) {
        this.deadline = deadline;
    }

     public Set<GroupTaskReadModel> getTasks() {
        return tasks;
    }

     public void setTasks(final Set<GroupTaskReadModel> tasks) {
        this.tasks = tasks;
    }
}
