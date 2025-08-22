package io.training.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
// Pola createdOn i updatedOn zostały wyniesione do innej klasy aby nie powtarzać ich w kolejnych encjach, bo są powtarzalne
// Można na kilka sposobów dołączyć je do encji - 1. @Inheritance(strategy = ) i klasa nadrzędna BaseAuditableEntity, 2. @Embedded i klasa Audit która jest @Embeddable - tu nie musimy dziedziczyć

// mówi że będziemy dziedziczyć jakieś pola i ustawia metodę dziedziczenia - tu z klasy BaseAuditableEntity
// - przykład: mamy klasę A ze wspólnymi kolumnami (np: id, createdOn), i klasy B i C które reprezentują inne dane (np: B - name, surname i C - companyName)
// - TABLE_PER_CLASS - Każda podklasa ma własną, niezależną tabelę, zawierającą także pola z klasy nadrzędnej - B I C zawierają pola z A
// - JOINED - Każda klasa (nadrzędna i podrzędna) ma własną tabelę, połączoną przez klucz główny (PK) - joinuje tabele A z B i A z C - wtedy wspólne pola są w A i mamy jakieś foreign keys do B i C
// - SINGLE_TABLE - Wszystkie klasy dziedziczące są zapisywane w jednej tabeli - jeden sql ale wiele kolumn może być niewykorzystane oraz potrzeba kolumny rozgraniczającej (np osoba(name)/osoba prawna(company_name))
//@Inheritance(strategy =  InheritanceType.TABLE_PER_CLASS)
@Table(name = "tasks")
public class Task
//        extends  BaseAuditableEntity
{
    @Id // to jest wymagane jako główne pole Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // automatycznie inkrementuje wartość primary key
    private int id;
    @NotBlank(message = "Task's description must be not empty") // jpa validator że pole nie moż być puste
    private String description;
    private boolean done;
    private LocalDateTime deadline;
    @Embedded // mówi że dodajemy do encji graty z innej klasy, która jest @Embeddedable - najczęściej by rozszerzać jakieś wspólne kolumny dla wielu encji
//    @AttributeOverride(column = @Column(name = "updatedOn"), name = "updatedOn") // pomocnicza adnotacja która może zmienić np mapowanie pól w Audit
    private Audit audit =  new Audit();// musimy zainicjalizować, bo inaczej hibernate widzi null i na null nie może wykonać onCreate i onUpdate

    @ManyToOne // mówi że wiele tasków może należeć do jednej grupy
    // targetEntity - mówi do jakiej encji się odnosimy - tu od razu widać że jest to TaskGroup
    // cascade - oznacza, jakie operacje mają być propagowane (czyli „przechodzić”) z encji głównej (tej, która zawiera relację) na encję powiązaną
    //     PERSIST - Gdy zapisujesz (persist) encję główną, zapisze się też powiązana encja.
    //     MERGE - Gdy wykonasz merge na encji głównej, wykona się też merge na powiązanej.
    //     REMOVE - Gdy usuniesz encję główną, zostanie też usunięta powiązana encja.
    //     REFRESH - Gdy wykonasz refresh, odświeżona zostanie także encja powiązana.
    //     DETACH - Gdy encja główna zostanie odłączona od kontekstu (EntityManager), to samo stanie się z encją powiązaną.
    //     ALL - Skrót oznaczający: PERSIST, MERGE, REMOVE, REFRESH, DETACH
    @JoinColumn(name = "task_group_id") // po której kolumnie robimy join - foreign key w task dla task_group
    private TaskGroup group;

    // adnotacja @Column pozwala np mapować pole na inną nazwę w bazie, czy jest nullable, jej dopuszcalny length, czy jest unique
    // adnotacja @Transient nadajemy jeśli nie chcemy aby pole było zapisywane do bazy danych - to pole nie jest trwałe


    Task() {
    }

    // dodatkowy konstruktor żeby nie upubliczniać podstawowego konstruktora i przy tym wszystkich getterów i setterów
    // użycie np w GroupTaskWriteModel aby inicjalnie stworzyć taska
     public Task(String description, LocalDateTime deadline) {
        this.description = description;
        this.deadline = deadline;
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

    public LocalDateTime getDeadline() {
        return deadline;
    }

    void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public TaskGroup getGroup() {
        return group;
    }

    public void setGroup(TaskGroup group) {
        this.group = group;
    }

    public void updateFrom(final Task source) {
        description = source.description;
        done = source.done;
        deadline = source.deadline;
        group = source.group;
    }
}
