package de.xitaso.taskman.model;

public class Task {
    private String description;
    private TaskState state;
    private User assignee;

    public Task(String description) {
        this.description = description;
        this.setState(TaskState.ToDo);
    }

    public void assign(User user) {
        assignee = user;
    }

    public User getAssignee() {
        return assignee;
    }

    public TaskState getState() {
        return state;
    }

    public void setState(TaskState state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }
}