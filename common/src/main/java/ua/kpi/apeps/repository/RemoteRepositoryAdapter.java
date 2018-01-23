package ua.kpi.apeps.repository;

import java.io.Serializable;
import java.rmi.RemoteException;

public class RemoteRepositoryAdapter<E extends Serializable, ID extends Serializable>
        implements RemoteRepository<E, ID> {

    private Repository<E, ID> adapted;

    private RemoteRepositoryAdapter(Repository<E, ID> adapted) throws RemoteException {
        this.adapted = adapted;
    }

    public static <E extends Serializable, ID extends Serializable>
    RemoteRepository<E, ID> of(Repository<E, ID> repository) throws RemoteException {
        return new RemoteRepositoryAdapter<>(repository);
    }

    @Override
    public E getById(ID id) throws RemoteException {
        return adapted.getById(id);
    }

    @Override
    public Iterable<E> getAll() throws RemoteException {
        return adapted.getAll();
    }

    @Override
    public ID create(E entity) throws RemoteException {
        return adapted.create(entity);
    }

    @Override
    public int delete(ID id) throws RemoteException {
        return adapted.delete(id);
    }
}
