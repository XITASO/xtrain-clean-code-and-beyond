package de.xitaso.taskman.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Project {
    private String name;
    private String description;
    private LocalDate deadline;
    private Collection<Task> tasks = new ArrayList<Task>();

    @JsonCreator
    public Project(@JsonProperty("name") String name) {
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
