package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.film.Interfaces.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.Interfaces.MpaStorage;
import ru.yandex.practicum.filmorate.storage.film.Interfaces.GenreStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;
    private final MpaStorage mpaStorage;
    private final GenreStorage genreStorage;

    @Override
    public Film addFilm(Film film) {
        Map<String, Object> sqlValues = new HashMap<>();

        sqlValues.put("customName", film.getName());
        sqlValues.put("description", film.getDescription());
        sqlValues.put("releaseDate", film.getReleaseDate());
        sqlValues.put("duration", film.getDuration());
        sqlValues.put("mpaFilm", film.getMpa().getId());

        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("film")
                .usingGeneratedKeyColumns("id");
        film.setId(simpleJdbcInsert.executeAndReturnKey(sqlValues).longValue());
        addGenre(film.getId(), film.getGenres());


        return getFilm(film.getId());
    }

    @Override
    public Film updateFilm(Film film) {

        if (isExists(film.getId())) {

            String sqlQuery = "UPDATE film SET customName = ?, description = ?, releaseDate = ?, duration = ?, mpaFilm = ? WHERE id = ?";

            jdbcTemplate.update(sqlQuery,
                    film.getName(),
                    film.getDescription(),
                    film.getReleaseDate(),
                    film.getDuration(),
                    film.getMpa().getId(),
                    film.getId());

            addGenre(film.getId(), film.getGenres());

            return film;
        } else {
            throw new ObjectNotFoundException("Not found.");
        }
    }

    @Override
    public Film getFilm(Long filmId) {
        if (isExists(filmId)) {

            String sqlQuery = "SELECT * FROM film WHERE id = ?";

            return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToFilm, filmId);
        } else {
            throw new ObjectNotFoundException("Object not found");
        }
    }

    @Override
    public List<Film> getAllFilms() {
        String sqlQuery = "SELECT * FROM film";

        return jdbcTemplate.query(sqlQuery, this::mapRowToFilm);
    }

    @Override
    public List<Film> getPopular(int count) {
        String sqlQuery = "SELECT * FROM film AS f LEFT JOIN (SELECT filmId, COUNT (userId) AS rate " +
                "FROM likes" +
                " GROUP BY filmId) AS l ON f.id = l.filmId ORDER BY rate DESC LIMIT ?";
        return jdbcTemplate.query(sqlQuery, this::mapRowToFilm, count);
    }

    private Film mapRowToFilm(ResultSet resultSet, int rowNumber) throws SQLException {
        Film film = new Film();
        film.setId(resultSet.getInt("id"));
        film.setName(resultSet.getString("customName"));
        film.setDescription(resultSet.getString("description"));
        film.setReleaseDate(resultSet.getDate("releaseDate").toLocalDate());
        film.setDuration(resultSet.getLong("duration"));
        film.setMpa(mpaStorage.getFilmMpa(resultSet.getLong("mpaFilm")));
        film.setGenres(genreStorage.getFilmGenre(film.getId()));

        return film;
    }


    public boolean isExists(Long id) {
        String sqlQuery = "SELECT id FROM film WHERE id = ?";
        return jdbcTemplate.queryForRowSet(sqlQuery, id).next();
    }

    private void addGenre(Long filmId, Set<Genre> genres) {
        deleteGenre(filmId);
        if ((genres != null) && (!genres.isEmpty())) {
            StringBuilder sqlQuery = new StringBuilder("INSERT INTO film_genre (filmId, genreId) VALUES ");
            for (Genre filmGenre : new HashSet<>(genres)) {
                sqlQuery.append("(").append(filmId).append(", ").append(filmGenre.getId()).append("), ");
            }
            sqlQuery.delete(sqlQuery.length() - 2, sqlQuery.length());
            jdbcTemplate.update(sqlQuery.toString());
        }
    }


    private void deleteGenre(Long filmId) {
        String sqlQuery = "DELETE FROM film_genre WHERE filmId = ?;";
        jdbcTemplate.update(sqlQuery, filmId);
    }
}