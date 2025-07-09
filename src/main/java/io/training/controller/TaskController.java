package io.training.controller;
import io.training.model.Task;
import io.training.model.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;


// Tylko endpointy udostępnione w kontrolerze będą wykonywane na TaskRepository. Spring Data rest jest już niedostępny odkąd używamy @Repository w TaskRepository.
// Przykład z Data Rest Repository jest dostępny w klasach BasicTaskRepository i BasicTaskController
@RestController
class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskRepository repository;

    TaskController(final TaskRepository repository) {
        this.repository = repository;
    }

    //Custom controller eliminuje możliwość automatu dla sort/paging/size więc eliminujemy te requesty z naszego controlera, a odwołanie do nich odeśle nas do wbudowanego controlera z JPA - chyba że mamy kontroler obsługujący pageable i sort
    @GetMapping(value = "/tasks", params = {"!sort", "!paging", "!size"}) // tu forma RequestMapping dedykowana dla requesta GET
    ResponseEntity<List<Task>> readAllTasks() {// @ResponseEntity - abstrakcja reprezentująca odpowiedź (np można ustawić status code 201)
        logger.warn("Exposing all the tasks!");// w ten sposób do istniejącego requesta TestRepository można dodać np loggera
        return ResponseEntity.ok(repository.findAll());// tu zwracamy wywołanie istniejącego TestRepository.findAll()
    }

    @GetMapping(value = "/tasks")
    ResponseEntity<List<Task>> readAllTasks(Pageable page) {
        logger.info("Custom pageable");
        return ResponseEntity.ok(repository.findAll(page).getContent());
    }
    
    @GetMapping("/tasks/{id}")
    ResponseEntity<Task> readTask(@PathVariable int id) {
        Optional<Task> task = repository.findById(id);
        if ( task.isPresent() ) {
            return ResponseEntity.ok(task.get());
        }
        return ResponseEntity.notFound().build();

    }
    
    @PostMapping("/tasks")
    ResponseEntity<Task> createTask(@RequestBody @Valid Task toCreate) {
        Task created = repository.save(toCreate);
        return ResponseEntity.created(URI.create("/tasks/" + created.getId())).body(created);
    }

    @PutMapping("/tasks/{id}")
    // @PathVariable pobiera variable z path -> /tasks/{id}
    // @RequestBody mówi że ten obiekt będzie wysyłany a @Valid musimy dodać jeśli nie chcemy 500 internal error tylko użyć validatora z TodoAppApplication->validator()
    ResponseEntity<?> updateTask(@PathVariable int id, @RequestBody @Valid Task toUpdate) {
        //- to było w pierwotnej wersji gdy setter był public, po stworzeniu metody updateFrom(), zmieniliśmy zapis aby uwzględniał @PostUpdate z Task
//        if (!repository.existsById(id)) {
//            return ResponseEntity.notFound().build();// zwracamy 404 Not Found
//        }
//        toUpdate.setId(id); //ustawiamy id w obiekcie toUpdate
//        repository.save(toUpdate);// zapisujemy do bazy
//        return ResponseEntity.noContent().build();// zwraca 204 bez content?
        Optional<Task> task = repository.findById(id);
        if ( task.isPresent() ) {
            task.get().updateFrom(toUpdate); // update na Optional<Task> task
            repository.save(task.get());// zapisujemy do bazy, gdybyśmy podali @Transient moglibyśmy pominąć ten save do bazy, bo hibernate sam by zapisał
        }
        return ResponseEntity.of(task); // ResponseEntity.of ustawia 404 Not Found jeśli nie ma taska w Optional oraz OK jeśli jest
    }
    // metody oznaczone tą adnotacją mówią hibernate, że każde działanie na niej ma na początku metody transaction begin a na końcu transaction end więc nastepuje zapis - nie musimy robić repository.save() jak w poprzednich
    // transactional działa tylko na metodach w których cześniej pobraliśmy encję i na niej operujemy - create więc nie będzie działać bo zapisywanie nowej encji tu nie działa
    // transactional powinien być dodawany również w metodach w których wszystkio ma się wykonać, a jeśli cos się nie wykona, to wszystko ma zostać wycofane - tyczy się zarówno controllerów czy serwisów
    @Transactional
    @PatchMapping("/tasks/{id}")
    public ResponseEntity<?> toggleTask(@PathVariable int id) {// metody @Transactional muszą być publiczne
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.findById(id)
                .ifPresent(task -> task.setDone(!task.isDone()));
        return ResponseEntity.noContent().build();// zwraca 204 bez content
    }
}
