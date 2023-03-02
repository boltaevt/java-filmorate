package ru.yandex.practicum.filmorate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;

@RestController
@RequestMapping(value = "/films")
public class FilmController {
    FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public List<Film> findAll() {
        return filmService.findAll();
    }

    @GetMapping("/{id}")
    public Film findFilmById(@PathVariable long id) throws ObjectNotFoundException {
        return filmService.findFilmById(id);
    }

    @PostMapping
    public Film create(@RequestBody @Valid Film film) throws ValidationException {
        return filmService.create(film);
    }

    @PutMapping
    public Film updateFilmInfo(@RequestBody @Valid Film film) throws ValidationException, ObjectNotFoundException {
        return filmService.updateFilmInfo(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public void likeFilm(@PathVariable("id") long filmId, @PathVariable long userId)
            throws ObjectNotFoundException {
        filmService.addLikeToFilm(filmId, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void unlikeFilm(@PathVariable("id") long filmId, @PathVariable long userId)
            throws ObjectNotFoundException {
        filmService.removeLikeFromFilm(filmId, userId);
    }

    @GetMapping("/popular")
    public List<Film> getMostLikedFilms(@RequestParam(required = false) Integer count) {
        if (count == null || count == 0) {
            count = 10;
        }
        return filmService.showTenMostPopularFilms(count);
    }
}


