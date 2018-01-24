package ua.kpi.apeps.repository;

import java.io.Serializable;

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
    E getById(ID id);

    /**
     * Get all items
     * @return collection of all stored items
     */
    Iterable<E> getAll();

    /**
     * Create entities in some repository
     *
     * @param entities entities
     * @return entities
     */
    ID create(E entities);

    /**
     * Deletes entities by ids
     *
     * @param ids ids of entities to delete
     * @return number of deleted entities
     */
    int delete(ID ids);
}
