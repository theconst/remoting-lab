package ua.kpi.apeps.repository.rmi;

import lombok.SneakyThrows;
import ua.kpi.apeps.repository.Repository;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

import static java.util.Arrays.asList;

/**
 * Defines repository that can be accessed remotely
 *
 * @param <E> type of an entity
 * @param <ID> type of id of the entity
 */
public interface RemoteRepository<E extends Serializable, ID extends Serializable> extends Remote {

    E getById(ID id) throws RemoteException;

    Collection<E> getByIds(Collection<ID> ids) throws RemoteException;

    Iterable<E> getAll() throws RemoteException;

    Collection<ID> create(Collection<E> entities) throws RemoteException;

    int delete(Collection<ID> ids) throws RemoteException;
}
