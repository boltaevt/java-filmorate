package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.storage.film.Interfaces.LikeStorage;

@Repository
public class LikeDbStorage implements LikeStorage {

    JdbcTemplate jdbcTemplate;

    public LikeDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addLike(Long filmId, Long userId) {
        String sqlQuery = "INSERT INTO likes (filmId, userId) VALUES (?, ?);";
        jdbcTemplate.update(sqlQuery, filmId, userId);
    }

    @Override
    public void deleteLike(Long filmId, Long userId) {
        String sqlCheck = "SELECT COUNT (*) FROM likes WHERE userId = ?;";
        if (jdbcTemplate.queryForObject(sqlCheck, Integer.class, userId) > 0) {
            String sqlQuery = "DELETE FROM likes WHERE filmId = ? AND userId = ?;";
            jdbcTemplate.update(sqlQuery, filmId, userId);
        } else {
            throw new ObjectNotFoundException("User not found");
        }
    }
}