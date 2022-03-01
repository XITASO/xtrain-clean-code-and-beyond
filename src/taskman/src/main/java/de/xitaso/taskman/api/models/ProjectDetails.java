package de.xitaso.taskman.api.models;

import java.time.LocalDate;

public class ProjectDetails {
    private String name;
    private String description;
    private LocalDate deadline;

    public ProjectDetails(String name, String description, LocalDate deadline) {
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
