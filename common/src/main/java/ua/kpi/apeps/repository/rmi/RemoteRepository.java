package ua.kpi.apeps.repository.rmi;

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
public interface RemoteRepository<E extends Serializable, ID extends Serializable>
        extends Remote, Repository<E, ID> {

    @Override
    Collection<E> getByIds(Collection<ID> ids) throws RemoteException;

    @Override
    Collection<E> getAll() throws RemoteException;

    @Override
    Collection<ID> create(Collection<E> entities) throws RemoteException;

    @Override
    int delete(Collection<ID> ids) throws RemoteException;

}
