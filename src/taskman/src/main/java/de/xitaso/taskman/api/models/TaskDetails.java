package de.xitaso.taskman.api.models;

import de.xitaso.taskman.entities.TaskState;

public class TaskDetails {
    private String description;
    private TaskState state;
    private long id;

    public TaskDetails(long id, TaskState state, String description) {
        this.id = id;
        this.state = state;
        this.description = description;
    }

    public long getID() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public TaskState getState() {
        return state;
    }

}
