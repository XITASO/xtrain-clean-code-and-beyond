package de.xitaso.taskman.api.models;

public class ProjectOverview {
    private long id;
    private String name;

    public ProjectOverview(long id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
