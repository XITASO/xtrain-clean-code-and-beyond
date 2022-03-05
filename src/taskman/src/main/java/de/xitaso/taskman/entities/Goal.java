package de.xitaso.taskman.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import de.xitaso.taskman.data.EntityBase;

public class Goal extends EntityBase {
    public String title;
    public String description;
    public LocalDate deadline;
    public User responsiblePerson;
    public Collection<Project> projects = new ArrayList();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public User getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(User responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public Collection<Project> getProjects() {
        return projects;
    }

    public void addProject(Project project) {
        projects.add(project);
    }

    public boolean removeProject(Project project) {
        return projects.remove(project);
    }
}
