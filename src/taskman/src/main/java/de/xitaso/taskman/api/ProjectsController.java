package de.xitaso.taskman.api;

import java.net.URI;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import de.xitaso.taskman.api.models.ProjectCreation;
import de.xitaso.taskman.api.models.ProjectDetails;
import de.xitaso.taskman.api.models.ProjectOverview;
import de.xitaso.taskman.api.models.ProjectUpdate;
import de.xitaso.taskman.data.TaskRepository;
import de.xitaso.taskman.entities.Project;
import de.xitaso.taskman.services.ProjectManagementService;

@RestController
public class ProjectsController {

    @Autowired
    public ProjectManagementService service;

    @Autowired
    public TaskRepository tasksRepository;

    @GetMapping("/projects")
    public ResponseEntity<ProjectOverview[]> getAll() {
        var result = StreamSupport.stream(service.findAll().spliterator(), false)
                .map(p -> new ProjectOverview(p.getID(), p.getName())).toArray(ProjectOverview[]::new);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/projects")
    public ResponseEntity<ProjectDetails> createProject(@RequestBody ProjectCreation newProject) {
        var project = new Project(newProject.getName());
        project.setDescription(newProject.getDescription());
        project.setDeadline(newProject.getDeadline());
        var id = service.createProject(project);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/projects/{id}")
    public ResponseEntity<ProjectDetails> getProject(@PathVariable long id) {
        var project = service.findById(id);
        if (project == null) {
            return ResponseEntity.notFound().build();
        }

        var result = new ProjectDetails(id, project.getName(), project.getDescription(), project.getDeadline(),
                project.getTaskIds().toArray(new Long[0]));
        return ResponseEntity.ok(result);
    }

    @PutMapping("/projects/{id}")
    public ResponseEntity<ProjectUpdate> updateProject(@PathVariable long id, @RequestBody ProjectUpdate updateData) {
        var project = service.findById(id);
        if (project == null) {
            return ResponseEntity.notFound().build();
        }

        project.setDeadline(updateData.getDeadline());
        project.setDescription(updateData.getDescription());

        for (int i = 0; i < updateData.getTaskIds().length; i++) {
            var task = tasksRepository.findOne(updateData.getTaskIds()[i]);
            if (task == null) {
                return ResponseEntity.notFound().build();
            }
            try {
                project.addTask(task);
            } catch (Exception ex) {
                var otherProject = service.findById(task.getProjectID());
                otherProject.removeTask(task);
                project.addTask(task);
            }
        }

        service.update(project);
        return ResponseEntity.ok().build();
    }
}
