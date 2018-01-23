package ua.kpi.apeps.repository.jdbc;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;

@Slf4j
class JdbcTransaction {

    /* Stores transactions as thread local variable */
    private static ThreadLocal<JdbcTransaction> CTX = new ThreadLocal<>();

    private Connection connection;

    private int nesting = 0;

    public static JdbcTransaction beginTransaction() {
        JdbcTransaction tx = CTX.get();
        if (tx == null) {
            tx = new JdbcTransaction();
            try {
                tx.connection = ConnectionManager.getConnection();
            } catch (Exception e) {
                tx.closeConnectionOrPanic();
                throw new JdbcAccessException(e);
            }
            CTX.set(tx);
            log.debug("Created new transaction with 0 nesting");
        }
        tx.nesting++;
        log.debug("Nesting {}", tx.nesting);

        return tx;
    }

    public static JdbcTransaction getCurrentTransaction() {
        JdbcTransaction tx = CTX.get();

        if (tx == null) {
            throw new IllegalStateException("Transaction not set. Did you use beginTransaction?");
        }
        log.debug("Nesting {}", tx.nesting);
        CTX.set(tx);
        return tx;
    }

    public void commit() {
        --nesting;
        log.debug("Nesting {}", nesting);
        try {
            connection.commit();
            if (nesting == 0) {
                closeConnectionOrPanic();
            }
        } catch (Exception ex) {
            closeConnectionOrPanic();

            throw new JdbcAccessException(ex);
        }
    }

    private void closeConnectionOrPanic() {
        log.debug("Closing connection...");
        CTX.set(null);
        try {
            if (connection != null) {
                connection.close();
                log.debug("Real connection closed");
            }
        } catch (Exception f) {
            log.error("Fatal", f);
            throw new Error("Unable to close connection, possible memory leak");
        }
    }

    public void rollback() {
        --nesting;
        log.debug("Nesting {}", nesting);
        try {
            connection.rollback();
            if (nesting == 0) {
                closeConnectionOrPanic();
            }
        } catch (Exception ex) {
            closeConnectionOrPanic();

            throw new JdbcAccessException(ex);
        }
    }

    public Connection getConnection() {
        return new TransactionAwareConnectionProxy(connection);
    }

}
