package ru.yandex.practicum.filmorate.controllers;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;


import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.*;


@RestController
@RequestMapping(value = "/users")
public class UserController {

    private Map<Integer, User> users = new HashMap<>();

    private int generatorId = 1;


    @GetMapping
    public List<User> findAll() {

        List<User> usersList = new ArrayList<>();
        for (User user : users.values()) {
            usersList.add(user);
        }

        return usersList;
    }


    @PostMapping
    public User create(@RequestBody @Valid User user) throws ValidationException {


        if (user.getName() == null) {
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

    @PutMapping
    public User updateUserInfo(@RequestBody @Valid User user) throws ValidationException {

        if (!users.containsKey(user.getId())) {
            throw new ValidationException("Такого пользователя не существует. Сначала необходимо создать.");
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


