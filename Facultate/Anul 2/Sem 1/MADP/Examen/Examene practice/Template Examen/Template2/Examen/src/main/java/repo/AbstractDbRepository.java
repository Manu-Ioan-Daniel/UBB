package repo;

import models.Entity;
import utils.DbConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDbRepository<E extends Entity<Long>> implements Repository<Long, E> {

    protected final Connection connection;


    public AbstractDbRepository() {
        this.connection = DbConnection.getInstance().getConnection();
    }

    public abstract E createEntity(ResultSet rs) throws SQLException;
    public abstract PreparedStatement findOneStatement(Long id) throws SQLException;
    public abstract PreparedStatement findAllStatement() throws SQLException;
    public abstract PreparedStatement saveStatement(E entity) throws SQLException;
    public abstract PreparedStatement deleteStatement(Long id) throws SQLException;
    public abstract PreparedStatement updateStatement(E entity) throws SQLException;

    @Override
    public Optional<E> findOne(Long id) {
        try (PreparedStatement statement = findOneStatement(id);
             ResultSet rs = statement.executeQuery()) {

            if (!rs.next()) {
                return Optional.empty();
            }

            return Optional.of(createEntity(rs));
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<E> findAll() {
        List<E> entities = new ArrayList<>();
        try (PreparedStatement statement = findAllStatement(); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next())
                entities.add(createEntity(resultSet));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return entities;
    }

    @Override
    public Optional<E> save(E entity) {
        if(findOne(entity.getId()).isPresent()){
            return Optional.of(entity);
        }
        try (PreparedStatement statement = saveStatement(entity)) {
            statement.executeUpdate();
            return Optional.empty();
        } catch (SQLException e) {
            return Optional.of(entity);
        }
    }

    @Override
    public Optional<E> delete(Long id) {
        Optional<E> entity = findOne(id);
        if (entity.isEmpty()) {
            return Optional.empty();
        }

        try (PreparedStatement statement = deleteStatement(id)) {
            statement.executeUpdate();
            return entity;
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<E> update(E entity) {
        try (PreparedStatement statement = updateStatement(entity)) {
            statement.executeUpdate();
            return Optional.empty();
        } catch (SQLException e) {
            return Optional.of(entity);
        }
    }
}
