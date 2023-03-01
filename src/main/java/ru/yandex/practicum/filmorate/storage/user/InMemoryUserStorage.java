package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {

    private Map<Long, User> users = new HashMap<>();

    private long generatorId = 1;

    public User findUserById(Long id) {
        if (users.containsKey(id)) {
            return users.get(id);
        } else {
            throw new ObjectNotFoundException("No such user.");
        }
    }

    public List<User> findAll() {
        List<User> usersList = new ArrayList<>();
        for (User user : users.values()) {
            usersList.add(user);
        }
        return usersList;
    }

    public User create(User user) throws ValidationException {
        if (user.getName() == null || user.getName().isBlank()) {
            User userNew = new User(generatorId, user.getEmail(), user.getLogin(), user.getLogin(), user.getBirthday());
            users.put(generatorId, userNew);
            generatorId++;
            return userNew;
        } else {
            User userNew = new User(generatorId, user.getEmail(), user.getLogin(), user.getName(), user.getBirthday());
            users.put(generatorId, userNew);
            generatorId++;
            return userNew;
        }
    }

    public User updateUserInfo(User user) {
        if (!users.containsKey(user.getId())) {
            throw new ObjectNotFoundException("Такого пользователя не существует. Сначала необходимо создать.");
        }
        if (user.getName().isBlank()) {
            users.remove(user.getId());
            User userUpdated = new User(user.getId(), user.getEmail(), user.getLogin(), user.getLogin(), user.getBirthday());
            users.put(user.getId(), userUpdated);
            return userUpdated;
        } else {
            users.remove(user.getId());
            User userUpdated = new User(user.getId(), user.getEmail(), user.getLogin(), user.getName(), user.getBirthday());
            users.put(user.getId(), userUpdated);
            return userUpdated;
        }
    }
}
