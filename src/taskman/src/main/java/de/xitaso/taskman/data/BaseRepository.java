package de.xitaso.taskman.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseRepository<T, ID extends Serializable> {
    private Map<ID, T> repository = new HashMap<>();

    public T save(T entity) {

    }

T findOne(ID key)
}
