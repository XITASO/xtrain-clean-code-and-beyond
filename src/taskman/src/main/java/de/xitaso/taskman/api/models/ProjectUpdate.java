package de.xitaso.taskman.api.models;

import java.time.LocalDate;

public class ProjectUpdate {
    private String description;
    private LocalDate deadline;
    private Long[] taskIds;

    public ProjectUpdate(String description, LocalDate deadline, Long[] taskIds) {
        super();
        this.description = description;
        this.deadline = deadline;
        this.taskIds = taskIds;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public Long[] getTaskIds() {
        return taskIds;
    }

}
