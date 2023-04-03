package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.film.Interfaces.GenreStorage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Genre getById(Long id) {
        List <Genre> genres = getAll();
        boolean checker = false;
        Genre returnGenre = new Genre();
        for (Genre genre : genres) {
            if (genre.getId() == id) {
                checker = true;
                returnGenre = genre;
            }
        }
        if (!checker) {
            throw new ObjectNotFoundException("Not found");
        }
        return returnGenre;
    }

    @Override
    public List<Genre> getAll() {
        String sqlQuery = "SELECT * FROM genre ORDER BY id;";

        return jdbcTemplate.query(sqlQuery, this::mapRowToGenre);
    }

    @Override
    public Set<Genre> getFilmGenre(Long filmId) {

        String sqlQuery = "SELECT * FROM film_genre JOIN genre ON " +
                "genre.id = film_genre.genreId WHERE film_genre.filmId = ?;";
        Set<Genre> genreSet = new HashSet<>(jdbcTemplate.query(sqlQuery, this::mapRowToGenre, filmId));

        return genreSet;
    }

    private Genre mapRowToGenre(ResultSet resultSet, int rowNumber) throws SQLException {
        Genre genre = new Genre();
        genre.setId(resultSet.getLong("id"));
        genre.setName(resultSet.getString("name"));

        return genre;
    }
}