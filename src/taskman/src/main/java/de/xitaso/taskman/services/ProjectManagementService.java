package de.xitaso.taskman.services;

import java.util.Optional;

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

    public Optional<Project> findById(long id) {
        return Optional.ofNullable(repository.findOne(id));
    }

    public Iterable<Project> findAll() {
        return repository.findAll();
    }

    public void update(Project details) {
        // TODO clarify if we need to check of existing ID
//        var actual = dataStore.findOne(details.getID());
//        if (actual == null) {
//            throw new UnsupportedOperationException();
//        }
        repository.update(details);
    }
}
