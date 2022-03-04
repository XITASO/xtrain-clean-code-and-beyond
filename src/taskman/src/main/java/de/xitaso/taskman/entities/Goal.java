package de.xitaso.taskman.entities;

import de.xitaso.taskman.data.EntityBase;

public class Goal extends EntityBase {
    private String description;

    public Goal(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
