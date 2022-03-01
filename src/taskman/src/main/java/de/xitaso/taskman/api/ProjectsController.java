package de.xitaso.taskman.api;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.xitaso.taskman.model.Project;

@RestController
public class ProjectsController {

    @GetMapping("/projects")
    public Collection<Project> hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        var result = new ArrayList<Project>(2);
        result.add(new Project("First"));
        result.add(new Project("Second"));
        return result;
    }
}
