package de.xitaso.taskman.api.models;

import java.time.LocalDate;

public class ProjectDetails {
    private LocalDate deadline;
    private String description;
    private long id;
    private String name;
    private Long[] taskIds;

    public ProjectDetails(long id, String name, String description, LocalDate deadline, Long[] taskIds) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.taskIds = taskIds;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public String getDescription() {
        return description;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long[] getTaskIds() {
        return taskIds;
    }
}
