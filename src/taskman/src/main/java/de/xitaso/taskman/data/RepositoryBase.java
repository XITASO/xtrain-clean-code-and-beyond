package de.xitaso.taskman.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class RepositoryBase<T extends EntityBase> {
    private Map<Long, T> repository = new HashMap<>();

    /**
     * @return id of the saved entity.
     */
    public synchronized long save(T entity) {
        var id = getNextId();
        entity.setID(id);
        repository.put(id, entity);
        return id;
    }

    public synchronized void update(T entity) {
        repository.put(entity.getID(), entity);
    }

    /**
     * 
     * @return The entity with the given id or null if it was not found.
     */
    public T findOne(long id) {
        return repository.get(id);
    }

    public Iterable<T> findAll() {
        return repository.values();
    }

    public boolean exists(long id) {
        return repository.containsKey(id);
    }

    public long count() {
        return repository.size();
    }

    private long getNextId() {
        var highestId = repository.size() > 0 ? repository.keySet().stream().max(Long::compare) : Optional.of(0L);
        return highestId.get() + 1L;
    }

}
