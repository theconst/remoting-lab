package ua.kpi.apeps.repository.jdbc;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import ua.kpi.apeps.model.EmployeeShiftRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

@Component
@Qualifier("employee-shift-record-repository")
@ConditionalOnProperty(value = "stub-mode", havingValue = "false")
public class JdbcEmployeeShiftRecordRepository extends AbstractJdbcRepository<EmployeeShiftRecord, Integer> {

    private static final String SELECT_BY_ID =
            "select id, comment, employee_id, worked_from, worked_to, was_controlled " +
                    "from employee_shift_record where id=?";

    private static final String SELECT_ALL =
            "select id, comment, employee_id, worked_from, worked_to, was_controlled " +
                    "from employee_shift_record";

    private static final String INSERT =
            "insert into employee_shift_record(comment, employee_id, worked_from, worked_to, was_controlled) " +
                    "values (?, ?, ?, ?, ?)";

    private static final String DELETE = "delete from employee_shift_record where id=?";

    @Override
    protected EmployeeShiftRecord buildEntity(ResultSet rs) throws SQLException {
        return EmployeeShiftRecord.builder()
                .id(rs.getInt(1))
                .comment(rs.getString(2))
                .employeeId(rs.getInt(3))
                .workedFrom(rs.getTimestamp(4).toLocalDateTime())
                .workedTo(rs.getTimestamp(5).toLocalDateTime())
                .wasControlled(rs.getBoolean(6))
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
    protected PreparedStatement prepareInsertStatement(Connection connection, EmployeeShiftRecord esr) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(INSERT, RETURN_GENERATED_KEYS);
        ps.setString(1, esr.getComment());
        ps.setInt(2, esr.getEmployeeId());
        ps.setTimestamp(3, Timestamp.valueOf(esr.getWorkedFrom()));
        ps.setTimestamp(4, Timestamp.valueOf(esr.getWorkedTo()));
        ps.setBoolean(5, esr.getWasControlled());

        return ps;
    }

    @Override
    protected PreparedStatement prepareDeleteStatement(Connection connection, Integer id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(DELETE);
        ps.setInt(1, id);

        return ps;
    }
}
