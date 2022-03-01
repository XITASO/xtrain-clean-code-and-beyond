package de.xitaso.taskman.api;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import de.xitaso.taskman.api.models.ProjectCreation;
import de.xitaso.taskman.entities.Project;

@RestController
public class ProjectsController {

    @GetMapping("/projects")
    public Project[] hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        var result = new ArrayList<Project>(2);
        result.add(new Project("First"));
        result.add(new Project("Second"));
        return result.toArray(new Project[result.size()]);
    }

    @PostMapping("/projects")
    public ResponseEntity<ProjectDetails> createProject(@RequestBody ProjectCreation newProject) {

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/projects/{name}")
                .buildAndExpand(newProject.getName())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
