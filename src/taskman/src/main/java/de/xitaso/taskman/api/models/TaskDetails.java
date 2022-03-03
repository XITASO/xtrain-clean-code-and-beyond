package de.xitaso.taskman.api.models;

import de.xitaso.taskman.entities.TaskState;

public class TaskDetails {
    private String description;
    private TaskState state;

    public TaskDetails(TaskState state, String description) {
        this.state = state;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public TaskState getState() {
        return state;
    }

}
