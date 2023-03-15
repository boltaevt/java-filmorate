package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Long, Film> films = new HashMap<>();
    private long generatorId = 1;

    public List<Film> findAll() {
        return new ArrayList<>(films.values());
    }

    public Film create(Film film) throws ValidationException {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("дата релиза — не раньше 28 декабря 1895 года.");
        }
        Film filmNew = new Film(generatorId, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration());
        films.put(generatorId, filmNew);
        generatorId++;
        return filmNew;
    }

    public Film updateFilmInfo(Film film) throws ValidationException, ObjectNotFoundException {
        if (!films.containsKey(film.getId())) {
            throw new ObjectNotFoundException("Фильма с идентификатором " + film.getId() + " нет. Необходимо создать.");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("дата релиза — не раньше 28 декабря 1895 года.");
        }
        films.remove(film.getId());
        Film filmUpdated = new Film(film.getId(), film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration());
        films.put(film.getId(), filmUpdated);
        return filmUpdated;
    }

    public Film findFilm(long id) throws ObjectNotFoundException {
        if (!films.containsKey(id)) {
            throw new ObjectNotFoundException("error");
        }
        return films.get(id);
    }

}
