package de.xitaso.taskman.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class Project {
    private String name;
    private String description;
    private LocalDate deadline;
    private Collection<Task> tasks = new ArrayList<Task>();

    public Project(String name) {
        this.name = name;
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public void removeTask(Task task) {
        this.tasks.remove(task);
    }

    public Collection<Task> getTasks() {
        return tasks;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }
}
