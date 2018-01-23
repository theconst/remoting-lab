package ua.kpi.apeps.repository;

import java.util.concurrent.Callable;

@FunctionalInterface
public interface TransactionTemplate<R> {

    R doInTransaction(Callable<R> tBody);
}
