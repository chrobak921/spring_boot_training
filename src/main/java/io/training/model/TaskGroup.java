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
    // można by użyć tylko @OneToMany w klasie TaskGroup i nie ustawiać nic w Task ale wtedy trzeba podać referencję do kolumny w klasie Task
//    @JoinColumn(referencedColumnName = "task_group_id")
    private Set<Task> tasks;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

//    @Embedded
//    private Audit audit =  new Audit();

    public TaskGroup() {
    }

    public int getId() { return id; }

    void setId(int id) { this.id = id; }

    public String getDescription() { return description; }

    public void setDescription(String description) {
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

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    Project getProject() {
        return project;
    }

    void setProject(Project project) {
        this.project = project;
    }
}
