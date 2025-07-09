package io.training.controller;


import io.training.model.Task;
import io.training.model.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Controller()
@RequestMapping("/oldStyle/tasks")
class OldTypeTaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskRepository repository;

    OldTypeTaskController(TaskRepository repository){
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    List<Task> readAllTasks() {
        logger.info("Custom pageable");
        return repository.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    @ResponseBody
    Task readTask(@PathVariable int id, HttpServletResponse response) throws IOException {
        Optional<Task> task = repository.findById(id);
        if ( task.isPresent() ) {
            return task.get();
        }
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
        return null;

    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    Task createTask(@RequestBody @Valid Task toCreate, HttpServletResponse response) {
        Task created = repository.save(toCreate);
        response.setStatus(HttpServletResponse.SC_CREATED);
        response.setHeader("Location", "/oldStyle/tasks" + created.getId());
        return created;
    }

    // Zakomentowane, gdyż po zmianie setId(id) na pakietowy nie jest on juz dostępny
    // - utowrzono metodę updateFrom() aby aktualizować dane dla taska który jest najpierw zaczytywany z bazy - patrz ta metoda w TaskController
//    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
//    @ResponseBody
//    void updateTask(@PathVariable int id, @RequestBody @Valid Task toUpdate, HttpServletResponse response) throws IOException {
//        if (!repository.existsById(id)) {
//            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//        }
//        toUpdate.setId(id);
//        repository.save(toUpdate);
//        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
//    }
}
