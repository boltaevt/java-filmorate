package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.film.Interfaces.FilmStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmDbStorageTest {
    private final FilmStorage filmDbStorage;

    @Test
    void addFilm() {
        Film film = new Film();
        film.setName("testFilm");
        film.setDescription("Film for tests");
        film.setReleaseDate(LocalDate.of(2000, 10, 14));
        film.setDuration(150);

        Mpa filmMpa = new Mpa();
        filmMpa.setId(1L);
        film.setMpa(filmMpa);

        filmDbStorage.addFilm(film);
        assertThat(filmDbStorage.getFilm(film.getId())).hasFieldOrPropertyWithValue("name", "testFilm");
    }

    @Test
    void getFilm() {
        Film film = new Film();
        Mpa filmMpa = new Mpa();
        film.setName("xxx");
        film.setDescription("Film for tests");
        filmMpa.setId(2L);
        film.setMpa(filmMpa);
        film.setReleaseDate(LocalDate.of(2000, 12, 21));

        filmDbStorage.addFilm(film);

        assertThat(filmDbStorage.getFilm(film.getId())).hasFieldOrPropertyWithValue("name", "xxx");
    }

    @Test
    void update() {
        Film film = new Film();
        Mpa mpa = new Mpa();
        film.setName("filmName");
        film.setDescription("Film for tests");
        mpa.setId(3L);
        film.setMpa(mpa);
        film.setReleaseDate(LocalDate.of(2012, 12, 12));

        filmDbStorage.addFilm(film);

        Film filmForUpdate = new Film();
        filmForUpdate.setId(film.getId());
        Mpa mpa2 = new Mpa();
        filmForUpdate.setName("forUp");
        filmForUpdate.setDescription("Film for tests");
        mpa2.setId(3L);
        filmForUpdate.setMpa(mpa2);
        filmForUpdate.setReleaseDate(LocalDate.of(2012, 12, 12));

        filmDbStorage.updateFilm(filmForUpdate);

        assertThat(filmDbStorage.getFilm(film.getId())).hasFieldOrPropertyWithValue("name", "forUp");
    }

    @Test
    void getAllFilms() {
        Film film = new Film();
        Mpa mpa = new Mpa();
        film.setName("test");
        film.setDescription("Film for tests");
        mpa.setId(4L);
        film.setMpa(mpa);
        film.setReleaseDate(LocalDate.of(2001, 6, 17));

        filmDbStorage.addFilm(film);

        List<Film> testList = new ArrayList<>(filmDbStorage.getAllFilms());

        Assertions.assertEquals(filmDbStorage.getAllFilms(), testList);
    }
}