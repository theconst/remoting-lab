package ua.kpi.apeps.repository.jdbc;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import ua.kpi.apeps.model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

@Component
@Qualifier("employee-repository")
@ConditionalOnProperty(value = "stub-mode", havingValue = "false")
public class JdbcEmployeeRepository extends AbstractJdbcRepository<Employee, Integer> {

    private static final String SELECT_BY_ID =
            "select id, name, surname, since, occupation, post " +
                    "from employee " +
                    "where id=?";
    private static final String SELECT_ALL =
            "select id, name, surname, since, occupation, post " +
                    "from employee";

    private static final String INSERT =
            "insert into employee(name, surname, since, occupation, post) " +
                    "values (?, ?, ?, ?, ?)";

    private static final String DELETE =
            "delete from employee " +
                    "where id=?";

    @Override
    protected Employee buildEntity(ResultSet rs) throws SQLException {
        return Employee.builder()
                .id(rs.getInt(1))
                .name(rs.getString(2))
                .surname(rs.getString(3))
                .since(rs.getTimestamp(4).toLocalDateTime())
                .occupation(rs.getString(5))
                .post(rs.getString(6))
                .build();
    }

    @Override
    protected PreparedStatement prepareFindById(Connection connection, Integer id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(SELECT_BY_ID);
        ps.setInt(1, id);

        return ps;
    }

    @Override
    protected PreparedStatement prepareFindAll(Connection connection) throws SQLException {
        return connection.prepareStatement(SELECT_ALL);
    }

    @Override
    protected PreparedStatement prepareInsertStatement(Connection connection, Employee emp) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(INSERT, RETURN_GENERATED_KEYS);
        ps.setString(1, emp.getName());
        ps.setString(2, emp.getSurname());
        ps.setTimestamp(3, Timestamp.valueOf(emp.getSince()));
        ps.setString(4, emp.getOccupation());
        ps.setString(5, emp.getPost());

        return ps;
    }

    @Override
    protected PreparedStatement prepareDeleteStatement(Connection connection, Integer id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(DELETE);
        ps.setInt(1, id);

        return ps;
    }
}
