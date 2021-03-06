package de.xitaso.taskman.entities;

import de.xitaso.taskman.data.EntityBase;

public class Task extends EntityBase {
    private String description;
    private TaskState state;
    private Long projectId = null;

    public Task(String description) {
        this.description = description;
        this.setState(TaskState.ToDo);
    }

    /**
     * Gets the state
     * 
     * @return The state
     */
    public TaskState getState() {
        return state;
    }

    /**
     * Sets the state
     * 
     * @param state - The state
     */
    public void setState(TaskState state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public Long getProjectID() {
        return projectId;
    }

    public void setProjectID(Long projectId) {
        if (this.projectId != null && projectId != null) {
            throw new UnsupportedOperationException(
                    "Task is already assigned to a drifferent project! You must unassign it first.");
        }
        this.projectId = projectId;
    }
}
