package ua.kpi.apeps.repository.rmi;

import ua.kpi.apeps.repository.Repository;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Collection;

public class RemoteRepositoryAdapter<E extends Serializable, ID extends Serializable>
        implements RemoteRepository<E, ID> {

    private Repository<E, ID> adapted;

    private RemoteRepositoryAdapter(Repository<E, ID> adapted) throws RemoteException {
        this.adapted = adapted;
    }

    @Override
    public E getById(ID id) throws RemoteException {
        try {
            return adapted.getById(id);
        } catch (IOException ex) {
            throw new RemoteException("IOException on remote side", ex);
        }
    }

    @Override
    public Collection<E> getByIds(Collection<ID> ids) throws RemoteException {
        try {
            return adapted.getByIds(ids);
        } catch (IOException ex) {
            throw new RemoteException("IOException on remote side", ex);
        }
    }

    @Override
    public Collection<E> getAll() throws RemoteException {
        try {
            return adapted.getAll();
        } catch (IOException ex) {
            throw new RemoteException("IOException on remote side", ex);
        }
    }

    @Override
    public Collection<ID> create(Collection<E> entities) throws RemoteException {
        try {
            return adapted.create(entities);
        } catch (IOException ex) {
            throw new RemoteException("IOException on remote side", ex);
        }
    }

    @Override
    public int delete(Collection<ID> ids) throws RemoteException {
        try {
            return adapted.delete(ids);
        } catch (IOException ex) {
            throw new RemoteException("IOException on remote side", ex);
        }
    }

    public static <E extends Serializable, ID extends Serializable>
    RemoteRepository<E, ID> of(Repository<E, ID> repository)
            throws RemoteException {
        return new RemoteRepositoryAdapter<>(repository);
    }
}
