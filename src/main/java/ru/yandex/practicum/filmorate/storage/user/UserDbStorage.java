package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;

import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Repository
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;
    private Long userId = 1L;

    @Autowired
    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User create(User user) {
        String sqlQuery = "INSERT into UserTable (email, login, name, birthday) VALUES (?, ?, ?, ?)";

        if (user.getName().isBlank()) {
            jdbcTemplate.update(sqlQuery, user.getEmail(), user.getLogin(), user.getLogin(), user.getBirthday());
            User newUser = new User(userId, user.getEmail(), user.getLogin(), user.getLogin(), user.getBirthday());
            userId++;
            return newUser;
        } else {
            jdbcTemplate.update(sqlQuery, user.getEmail(), user.getLogin(), user.getName(), user.getBirthday());
            User newUser = new User(userId, user.getEmail(), user.getLogin(), user.getName(), user.getBirthday());
            userId++;
            return newUser;
        }
    }

    @Override
    public User findUserById(Long id) {

        String sqlQuery = "SELECT id, email, login, name, birthday " + "FROM UserTable " + "WHERE id = ?;";
        if (checkUserExists(id)) {
            return jdbcTemplate.query(sqlQuery, new UserRowMapper(), id).stream().findFirst().get();
        } else {
            throw new ObjectNotFoundException("User does not exist. Id: " + id);
        }
    }

    @Override
    public boolean checkUserExists(long id) {
        boolean checker = false;
        String sqlQuery = "SELECT COUNT (*) FROM UserTable WHERE id = ?;";
        Integer count = jdbcTemplate.queryForObject(sqlQuery, Integer.class, id);
        checker = count > 0;
        return checker;
    }

    @Override
    public List<User> findAll() {
        String sqlQuery = "SELECT id, email, login, name, birthday FROM UserTable LIMIT 100";
        return jdbcTemplate.query(sqlQuery, new UserRowMapper());
    }

    @Override
    public User updateUserInfo(User user) {
        long id = user.getId();
        String sqlConfirmation = "SELECT * FROM UserTable WHERE id=?;";
        if ((jdbcTemplate.query(sqlConfirmation, new UserRowMapper(), id).stream().findFirst().isPresent())) {
            String sqlQuery = "UPDATE UserTable SET email=?, login=?, name=?, birthday=? WHERE id=?";
            jdbcTemplate.update(sqlQuery, user.getEmail(), user.getLogin(), user.getName(), user.getBirthday(), id);
            return user;
        } else {
            throw new ObjectNotFoundException("User with following id does not exist:" + user.getId());
        }
    }

    @Override
    public List<User> findAllByIdIn(List<Long> userIds) {
        List<User> usersFound = new ArrayList<>();
        for (Long id : userIds) {
            usersFound.add(findUserById(id));
        }
        return usersFound;
    }

    private class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new User(rs.getInt("id"), rs.getString("email"), rs.getString("login"), rs.getString("name"), rs.getDate("birthday").toLocalDate());
        }
    }
}
