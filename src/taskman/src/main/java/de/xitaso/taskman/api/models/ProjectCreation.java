package de.xitaso.taskman.api.models;

import java.time.LocalDate;

public class ProjectCreation {
    private LocalDate deadline;
    private String description;
    private String name;

    public ProjectCreation(String name, String description, LocalDate deadline) {
        this.name = name;
        this.description = description;
        this.deadline = deadline;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }
}
