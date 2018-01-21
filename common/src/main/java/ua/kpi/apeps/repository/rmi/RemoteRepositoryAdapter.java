package ua.kpi.apeps.repository.rmi;

import ua.kpi.apeps.repository.Repository;

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
        return adapted.getById(id);
    }

    @Override
    public Collection<E> getByIds(Collection<ID> ids) throws RemoteException {
        return adapted.getByIds(ids);
    }

    @Override
    public Iterable<E> getAll() throws RemoteException {
        return adapted.getAll();
    }

    @Override
    public Collection<ID> create(Collection<E> entities) throws RemoteException {
        return adapted.create(entities);
    }

    @Override
    public int delete(Collection<ID> ids) throws RemoteException {
        return adapted.delete(ids);
    }

    public static <E extends Serializable, ID extends Serializable>
        RemoteRepository<E, ID> of(Repository<E, ID> repository) throws RemoteException {
        return new RemoteRepositoryAdapter<>(repository);
    }
}
