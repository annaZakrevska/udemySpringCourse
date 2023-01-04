package github.udemySpringCourse.todoapp.controller;


import github.udemySpringCourse.todoapp.model.Task;
import github.udemySpringCourse.todoapp.model.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;



import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Controller
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskRepository repository;

    TaskController(final TaskRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/tasks", params = {"!sort", "!page", "!size"})
    ResponseEntity<List<Task>> readAllTasks() {
        logger.warn("Exposing all the tasks!");
        return ResponseEntity.ok(repository.findAll());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/tasks")
    ResponseEntity<List<Task>> readAllTasks(Pageable page) {
        logger.info("Custom pageable");
        return ResponseEntity.ok(repository.findAll(page).getContent());
    }

    @RequestMapping(method = RequestMethod.PUT,value = "/tasks/{id}")
    ResponseEntity<?> updateTask(@PathVariable int id, @RequestBody @Valid Task toUpdate) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        toUpdate.setId(id);
        repository.save(toUpdate);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/tasks")
    ResponseEntity<?> showTask(@PathVariable int id) {
    return repository.findById(id).
            map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    @RequestMapping(method = RequestMethod.POST,value="/tasks")
    ResponseEntity<?> createTask (@Valid @RequestBody Task toCreate){
        Task result = repository.save(toCreate);
       return ResponseEntity.created(URI.create("/"+result.getId())).body(result);
    }
}
