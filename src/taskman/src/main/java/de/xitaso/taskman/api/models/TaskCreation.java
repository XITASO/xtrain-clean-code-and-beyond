package de.xitaso.taskman.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TaskCreation {
    private String description;

    public TaskCreation(@JsonProperty("description") String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
