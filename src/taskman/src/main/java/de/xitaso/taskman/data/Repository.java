package de.xitaso.taskman.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Repository<T> {
    private Map<Long, T> repository = new HashMap<>();

    /**
     * @return id of the saved entity.
     */
    public synchronized long save(T entity) {
        var id = getNextId();
        repository.put(id, entity);
        return id;
    }

    public Optional<T> findOne(long id) {
        return Optional.ofNullable(repository.get(id));
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
        var highestId = repository.keySet().stream().max(Long::compare);
        return highestId.get() + 1L;
    }

}
