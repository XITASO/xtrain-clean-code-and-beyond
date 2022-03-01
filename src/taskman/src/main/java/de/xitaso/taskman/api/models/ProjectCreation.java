package de.xitaso.taskman.api.models;

import java.time.LocalDate;

public class ProjectCreation {
    private String name;
    private String description;
    private LocalDate deadline;

    public ProjectCreation(String name, String description, LocalDate deadline) {
        this.name = name;
        this.description = description;
        this.deadline = deadline;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDeadline() {
        return deadline;
    }
}
