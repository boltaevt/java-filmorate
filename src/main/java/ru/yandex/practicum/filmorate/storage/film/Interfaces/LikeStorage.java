package ru.yandex.practicum.filmorate.storage.film.Interfaces;

public interface LikeStorage {

        void addLike(Long filmId, Long userId);

        void deleteLike(Long filmId, Long userId);
}
