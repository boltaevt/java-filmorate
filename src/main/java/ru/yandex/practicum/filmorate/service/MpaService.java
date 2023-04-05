package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.film.MpaDbStorage;

import java.util.List;

@Service
public class MpaService {

    private final MpaDbStorage mpaDbStorage;

    public MpaService(MpaDbStorage mpaStorage) {
        this.mpaDbStorage = mpaStorage;
    }

    public Mpa getById(Long id) {
        return mpaDbStorage.getById(id);
    }

    public List<Mpa> getAll() {
        return mpaDbStorage.getAll();
    }

    public Mpa getFilmMpa(Long filmId) {
        return mpaDbStorage.getFilmMpa(filmId);
    }
}