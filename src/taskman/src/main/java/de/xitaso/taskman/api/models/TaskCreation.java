package de.xitaso.taskman.api.models;

public class TaskCreation {
    private String description;

    public TaskCreation(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
