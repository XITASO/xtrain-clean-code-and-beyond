package de.xitaso.taskman.entities;

import de.xitaso.taskman.data.EntityBase;

public class User extends EntityBase {
    private String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
