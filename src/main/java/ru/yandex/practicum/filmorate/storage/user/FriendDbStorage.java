package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Friend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Repository
public class FriendDbStorage {

    private final JdbcTemplate jdbcTemplate;

    public FriendDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addFriend(long userId, long userFriendId) {
        String sqlQuery = "INSERT into Friendship (userId, userFriendId, status) VALUES (?, ?, true)";
        jdbcTemplate.update(sqlQuery, userId, userFriendId);
    }

    public void deleteFriend(long userId, long friendId) {
        String sqlQuery = "DELETE FROM Friendship WHERE userId = ? AND userFriendId = ?;";
        jdbcTemplate.update(sqlQuery, userId, friendId);
    }

    public boolean confirmUserHasFriends(long userId) {
        boolean checker;
        String sqlQuery = "SELECT COUNT (*) FROM Friendship WHERE userId = ?;";
        Integer count = jdbcTemplate.queryForObject(sqlQuery, Integer.class, userId);
        checker = count > 0;
        return checker;
    }

    public List<Long> findUserFriends(long userId) {
        if (confirmUserHasFriends(userId)) {
            String sqlQuery = "SELECT userFriendId FROM Friendship WHERE userId = ?";
            List<Long> friends = jdbcTemplate.queryForList(sqlQuery, Long.class, userId);
            return friends;
        } else {
            List<Long> anotherList = Collections.emptyList();
            return anotherList;
        }
    }

    private class FriendRowMapper implements RowMapper<Friend> {
        @Override
        public Friend mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Friend(rs.getInt("id"), rs.getLong("userId"),
                    rs.getLong("userFriendId"), rs.getBoolean("status"));
        }
    }
}
