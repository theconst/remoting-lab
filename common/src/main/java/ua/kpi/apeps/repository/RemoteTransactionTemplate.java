package ua.kpi.apeps.repository;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.concurrent.Callable;

public interface RemoteTransactionTemplate<T> extends Remote {
    T doInTransaction(Callable<T> tBody) throws RemoteException;
}
