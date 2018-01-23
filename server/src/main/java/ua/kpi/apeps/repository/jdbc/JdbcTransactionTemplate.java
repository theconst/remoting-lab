package ua.kpi.apeps.repository.jdbc;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ua.kpi.apeps.repository.TransactionTemplate;

import java.util.concurrent.Callable;

/**
 * Simple template for performing transactions
 *
 * @param <R> return type of the callback
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JdbcTransactionTemplate<R> implements TransactionTemplate<R> {

    public static <T> T singleJdbcTransaction(Callable<T> transactionBody) {
        return new JdbcTransactionTemplate<T>()
                .doInTransaction(transactionBody);
    }

    public static void singleJdbcTransaction(Runnable runnable) {
        new JdbcTransactionTemplate<Void>()
                .doInTransaction(() -> {
                    runnable.run();
                    return null;
                });
    }

    public R doInTransaction(Callable<R> transactionBody) {
        JdbcTransaction transaction = JdbcTransaction.beginTransaction();
        try {
            R result = transactionBody.call();
            transaction.commit();
            return result;
        } catch (Exception ex) {
            transaction.rollback();
            throw new JdbcAccessException(ex);
        }
    }
}
