package ua.kpi.apeps.repository.jdbc;


/**
 * Wraps all related to singleJdbcTransaction exceptions
 */
public class JdbcAccessException extends RuntimeException {

    public JdbcAccessException(Throwable cause) {
        super(cause);
    }

    public JdbcAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
