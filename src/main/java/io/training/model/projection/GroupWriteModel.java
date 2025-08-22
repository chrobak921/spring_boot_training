package io.training.model.projection;

import io.training.model.TaskGroup;

import java.util.Set;
import java.util.stream.Collectors;

public class GroupWriteModel {
    private String description;
    private Set<GroupTaskWriteModel> tasks;

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Set<GroupTaskWriteModel> getTasks() {
        return tasks;
    }

    public void setTasks(final Set<GroupTaskWriteModel> tasks) {
        this.tasks = tasks;
    }

    public TaskGroup toGroup() {
        var result = new TaskGroup(); // zwracamy TaskGroup z taskami utworzonymi przez GroupTaskWriteModel (okrojone, a część pól będzie tworzona automatycznie czy to przez hibernate czy )
        result.setDescription(description);
        result.setTasks(
                tasks.stream()
                        .map(GroupTaskWriteModel::totask)
                        .collect(Collectors.toSet()) // pobieramy kolejne GroupTaskWriteModel i tworzymy z nich Task i wrzucamy do kolekcji
        );
        return result;
    }
}
