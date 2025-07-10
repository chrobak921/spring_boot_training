package io.training.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity

@Table(name = "task_groups")
public class TaskGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Task group's description must be not empty") // jpa validator że pole nie moż być puste
    private String description;
    private boolean done;
    // @OneToMany - jeden TaskGroup ma wiele Tasks
    // - fetch = FetchType.LAZY - pobiera listę tasków z bazy tylko gry wywołamy getTasks (default value); eager - pobiera za każdym razem gdy wyciągamy taskGroup
    // - cascade = CascadeType.ALL - zmiana w taskGroup to zmiana na wszystkich taskach, np delete, zapis czy update
    // - mappedBy = "group" - wewnątrz każdego task (many) ta grupa (TaskGroup) jest zmapowana na pole group -> TaskGroup group
    // hibernate ma własną implementację kolekcji, a przychodząca do głowy kolekcja tu jest List, jednak hibernate'owa implementacja List
    // nie utrzymuje kolejności obiektów, więc lepszym rozwiązaniem tu będzie Set, bo chociaż pozwoli nam trzymać tyko unikalne taski
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "group")
    private Set<Task> tasks;

    @Embedded
    private Audit audit =  new Audit();

    TaskGroup() {
    }

    public int getId() { return id; }

    void setId(int id) { this.id = id; }

    public String getDescription() { return description; }

    void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

}
