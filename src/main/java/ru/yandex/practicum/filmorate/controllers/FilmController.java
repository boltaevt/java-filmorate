package ru.yandex.practicum.filmorate.controllers;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/films")
public class FilmController {

    private Map<Integer, Film> films = new HashMap<>();
    private int generatorId = 1;

    @GetMapping
    public List<Film> findAll() {
        return new ArrayList<>(films.values());
    }

    @PostMapping
    public Film create(@RequestBody @Valid Film film) throws ValidationException {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("дата релиза — не раньше 28 декабря 1895 года.");
        }
        Film filmNew = new Film(generatorId, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration());
        films.put(generatorId, filmNew);
        generatorId++;
        return filmNew;
    }

    @PutMapping
    public Film updateFilmInfo(@RequestBody @Valid Film film) throws ValidationException {
        if (!films.containsKey(film.getId())) {
            throw new ValidationException("Фильма с идентификатором " + film.getId() + " нет. Необходимо создать.");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("дата релиза — не раньше 28 декабря 1895 года.");
        }
        films.remove(film.getId());
        Film filmUpdated = new Film(film.getId(), film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration());
        films.put(film.getId(), filmUpdated);
        return filmUpdated;
    }
}


