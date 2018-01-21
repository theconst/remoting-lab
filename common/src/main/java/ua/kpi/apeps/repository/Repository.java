package ua.kpi.apeps.repository;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

/**
 * Generic repository of items that handle the underlying store
 *
 * @param <E> type of entity
 * @param <ID> id of the entity
 */
public interface Repository<E extends Serializable, ID extends Serializable> {

    /**
     * Get single item by id
     * @param id id
     * @return entity
     */
    default E getById(ID id) {
        return getByIds(singletonList(id))
                .iterator()
                .next();
    }

    /**
     * Get all items
     * @return collection of all stored items
     */
    Iterable<E> getAll();

    /**
     * Get all items by id
     * @param ids ids of the item to fetch
     * @return collection of item
     */
    Collection<E> getByIds(Collection<ID> ids);

    /**
     * Create entities in some repository
     *
     * @param entities entities
     * @return entities
     */
    Collection<ID> create(Collection<E> entities);

    /**
     * Deletes entities by ids
     *
     * @param ids ids of entities to delete
     * @return number of deleted entities
     */
    int delete(Collection<ID> ids);
}
