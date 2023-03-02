package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ValidationException;
import java.util.*;

@Component
public class InMemoryUserStorage implements UserStorage {

    private Map<Long, User> users = new HashMap<>();

    private long generatorId = 1;

    public User findUserById(Long id) {
        if (users.containsKey(id)) {
            return users.get(id);
        } else {
            throw new ObjectNotFoundException("No user with ID: " + id);
        }
    }

    public List<User> findAll() {
        return new ArrayList<>(users.values());
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

    @Override
    public List<User> findAllByIdIn(List<Long> userIds) {
        List<User> listOfUsers = new ArrayList<>();
        if (userIds.isEmpty()) {
            throw new ObjectNotFoundException("The list of user id's is empty.");
        }
        for (User user : users.values()) {
            if (userIds.contains(user.getId())) {
                listOfUsers.add(user);
            }
        }
        return listOfUsers;
    }
}
