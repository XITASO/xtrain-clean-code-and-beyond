package de.xitaso.taskman.api;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import de.xitaso.taskman.api.models.ProjectDetails;
import de.xitaso.taskman.api.models.TaskCreation;
import de.xitaso.taskman.api.models.TaskDetails;
import de.xitaso.taskman.data.TaskRepository;
import de.xitaso.taskman.entities.Task;

@RestController
//@RequestMapping(path = "/tasks")
public class TasksController {

    @Autowired
    public TaskRepository tasksRepository;

    @PostMapping("/tasks")
    public ResponseEntity<ProjectDetails> create(@RequestBody() TaskCreation value) {

        var task = new Task(value.getDescription());
        tasksRepository.save(task);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(task.getID())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<TaskDetails> get(@PathVariable() long id) {
        var result = tasksRepository.findOne(id);
        if (result != null) {
            return ResponseEntity.ok(new TaskDetails(result.getID(), result.getState(), result.getDescription()));
        }
        return ResponseEntity.notFound().build();
    }

}
