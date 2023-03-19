package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserStorage {

    User findUserById(Long id);

    List<User> findAll();

    User create(User user);

    User updateUserInfo(User user);

    List<User> findAllByIdIn(List<Long> userIds);
}
