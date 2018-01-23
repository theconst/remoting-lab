package ua.kpi.apeps.repository;

import lombok.AllArgsConstructor;

import java.rmi.RemoteException;
import java.util.concurrent.Callable;

@AllArgsConstructor(staticName = "of")
public class RemoteTransactionTemplateAdapter<T> implements RemoteTransactionTemplate<T> {

    private TransactionTemplate<T> t;

    @Override
    public T doInTransaction(Callable<T> tBody) throws RemoteException {
        return t.doInTransaction(tBody);
    }
}
