package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {

    public User findUserById(Long id);

    public List<User> findAll();

    public User create(User user);

    public User updateUserInfo(User user);
}
