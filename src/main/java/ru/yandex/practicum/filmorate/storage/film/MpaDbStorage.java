package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.film.Interfaces.MpaStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class MpaDbStorage implements MpaStorage {

    private final JdbcTemplate jdbcTemplate;

    public MpaDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Mpa getById(Long id) {
        String sqlCheckQuery = "SELECT COUNT (*) FROM MPA WHERE mpa = ?;";

        if (jdbcTemplate.queryForObject(sqlCheckQuery, Integer.class, id) > 0) {

        String sqlQuery = "SELECT * FROM MPA WHERE mpa = ?";

        return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToMpa, id);

        } else {
            throw new ObjectNotFoundException("Object not found");
        }
    }

    @Override
    public List<Mpa> getAll() {
        String sqlQuery = "SELECT * FROM MPA ORDER BY MPA";
        return jdbcTemplate.query(sqlQuery, this::mapRowToMpa);
    }

    @Override
    public Mpa getFilmMpa (Long mpa) {
        String sqlQuery = "SELECT * FROM MPA WHERE mpa = ?";
        return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToMpa, mpa);
    }

    private Mpa mapRowToMpa(ResultSet resultSet, int rowNumber) throws SQLException {
        Mpa mpa = new Mpa();
        mpa.setId(resultSet.getLong("mpa"));
        mpa.setName(resultSet.getString("name"));

        return mpa;
    }
}