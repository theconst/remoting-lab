package ua.kpi.apeps.repository;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Defines repository that can be accessed remotely
 *
 * @param <E> type of an entity
 * @param <ID> type of id of the entity
 */
public interface RemoteRepository<E extends Serializable, ID extends Serializable> extends Remote {

    E getById(ID id) throws RemoteException;

    Iterable<E> getAll() throws RemoteException;

    ID create(E entity) throws RemoteException;

    int delete(ID ids) throws RemoteException;
}
