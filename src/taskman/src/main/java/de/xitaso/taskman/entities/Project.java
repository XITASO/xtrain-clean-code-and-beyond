package de.xitaso.taskman.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import de.xitaso.taskman.data.EntityBase;

public class Project extends EntityBase {
    private String name;
    private String description;
    private LocalDate deadline;

    private Collection<Long> taskIds = new ArrayList<>();

    public Project(String name) {
        this.name = name;
    }

    public synchronized void addTask(Task task) {
        this.taskIds.add(task.getID());
        task.setProjectID(this.getID());
    }

    public void removeTask(Task task) {
        this.taskIds.remove(task.getID());
        task.setProjectID(null);
    }

    public Collection<Long> getTaskIds() {
        return taskIds;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    /**
     * The Project End date describes the date when the project has to be finished.
     * 
     * @param endDate - date when the project should be finished
     */
    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }
}
