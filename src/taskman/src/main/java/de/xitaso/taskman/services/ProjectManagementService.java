package de.xitaso.taskman.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.xitaso.taskman.data.ProjectRepository;
import de.xitaso.taskman.entities.Project;

@Service
public class ProjectManagementService {

    @Autowired
    private ProjectRepository repository;

    public long createProject(Project project) {
        return repository.save(project);
    }

    public Project findById(long id) {
        return repository.findOne(id);
    }

    public Iterable<Project> findAll() {
        return repository.findAll();
    }

    public void update(Project details) {
    }
}
