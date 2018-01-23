package ua.kpi.apeps.repository.jdbc;

import lombok.AllArgsConstructor;
import ua.kpi.apeps.repository.Repository;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static ua.kpi.apeps.repository.jdbc.JdbcTransaction.getCurrentTransaction;

@AllArgsConstructor
abstract class AbstractJdbcRepository<E extends Serializable, ID extends Serializable> implements Repository<E, ID> {

    protected abstract E buildEntity(ResultSet rs) throws SQLException;

    protected abstract PreparedStatement prepareFindById(Connection connection, ID ids) throws SQLException;

    protected abstract PreparedStatement prepareFindAll(Connection connection) throws SQLException;

    protected abstract PreparedStatement prepareInsertStatement(Connection connection, E entity) throws SQLException;

    protected abstract PreparedStatement prepareDeleteStatement(Connection connection, ID id) throws SQLException;

    @Override
    public final Iterable<E> getAll() {
        try (Connection connection = getCurrentTransaction().getConnection()) {
            PreparedStatement findAllQuery = prepareFindAll(connection);
            ResultSet resultSet = findAllQuery.executeQuery();
            List<E> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildEntity(resultSet));
            }
            return result;
        } catch (SQLException ex) {
            throw new JdbcAccessException(ex);
        }
    }

    @Override
    public final E getById(ID id) {
        try (Connection connection = getCurrentTransaction().getConnection()) {
            PreparedStatement findByIdsQuery = prepareFindById(connection, id);
            ResultSet resultSet = findByIdsQuery.executeQuery();
            if (resultSet.next()) {
                return buildEntity(resultSet);
            }
            return null;
        } catch (SQLException ex) {
            throw new JdbcAccessException(ex);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public ID create(E entity) {
        try (Connection connection = getCurrentTransaction().getConnection()) {
            PreparedStatement insertStatement = prepareInsertStatement(connection, entity);
            insertStatement.executeUpdate();

            ResultSet key = insertStatement.getGeneratedKeys();
            if (key.next()) {
                return null;
            }
            return null;
        } catch (SQLException ex) {
            throw new JdbcAccessException(ex);
        }
    }

    @Override
    public int delete(ID ids) {
        try (Connection connection = getCurrentTransaction().getConnection()) {
            PreparedStatement deleteStatement = prepareDeleteStatement(connection, ids);
            return deleteStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new JdbcAccessException(ex);
        }
    }
}
