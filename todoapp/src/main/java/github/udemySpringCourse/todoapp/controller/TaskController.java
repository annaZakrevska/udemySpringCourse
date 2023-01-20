package github.udemySpringCourse.todoapp.controller;


import github.udemySpringCourse.todoapp.model.Task;
import github.udemySpringCourse.todoapp.model.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.transaction.Transactional;
import javax.validation.Valid;
import java.beans.Transient;
import java.net.URI;
import java.util.List;

@RestController
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskRepository repository;

    TaskController(final TaskRepository repository) {
        this.repository = repository;
    }

    @GetMapping( value =  "/tasks", params = {"!sort", "!page", "!size"})
     ResponseEntity<List<Task>> readAllTasks() {
        logger.warn("Exposing all the tasks!");
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/tasks")
    ResponseEntity<List<Task>> readAllTasks(Pageable page) {
        logger.info("Custom pageable");
        return ResponseEntity.ok(repository.findAll(page).getContent());
    }

    @PutMapping( "/tasks/{id}")
    ResponseEntity<?> updateTask(@PathVariable int id, @RequestBody @Valid Task toUpdate) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        toUpdate.setId(id);
        repository.findById(id)
                .ifPresent(task -> {
                    task.updateFrom(toUpdate);
                    repository.save(task);
                });
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/tasks/{id}")
    ResponseEntity<Task> readtask(@PathVariable int id) {
    return repository.findById(id).
            map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/tasks")
    ResponseEntity<Task> createTask (@RequestBody @Valid Task toCreate){
        Task result = repository.save(toCreate);
       return ResponseEntity.created(URI.create("/"+result.getId())).body(result);
    }
    @Transactional
    @PatchMapping("/tasks/{id}")
    public ResponseEntity<Task> toggleTask(@PathVariable int id){
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.findById(id)
                .ifPresent(task -> task.setDone((!task.isDone())));
        return ResponseEntity.noContent().build();
    }

}
