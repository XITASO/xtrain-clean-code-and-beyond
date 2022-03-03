package de.xitaso.taskman.api;

import java.net.URI;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import de.xitaso.taskman.api.models.ProjectCreation;
import de.xitaso.taskman.api.models.ProjectDetails;
import de.xitaso.taskman.api.models.ProjectOverview;
import de.xitaso.taskman.entities.Project;
import de.xitaso.taskman.services.ProjectManagementService;

@RestController
public class ProjectsController {

    @Autowired
    public ProjectManagementService service;

    @GetMapping("/projects")
    public ProjectOverview[] getAll() {
        var result = StreamSupport.stream(service.findAll().spliterator(), false)
                .map(p -> new ProjectOverview(p.getID(), p.getName())).toArray(ProjectOverview[]::new);

        return result;
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

        var result = new ProjectDetails(id, project.getName(), project.getDescription(), project.getDeadline());
        return ResponseEntity.ok(result);
    }
}
