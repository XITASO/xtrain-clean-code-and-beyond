package de.xitaso.taskman.databuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class DataBuilder<TEntity, TBuilder extends DataBuilder<TEntity, TBuilder>> {

    private final List<Consumer<TEntity>> configurations = new ArrayList<>();

    public TBuilder with(Consumer<TEntity> applyConfig) {
        configurations.add(applyConfig);
        return (TBuilder) this;
    }

    public TEntity build() {
        var instance = createInstance();
        configurations.forEach(applyConfig -> applyConfig.accept(instance));
        return instance;
    }

    protected abstract TEntity createInstance();
}
