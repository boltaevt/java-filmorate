package ru.yandex.practicum.filmorate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;


import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.*;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User findUserById(@PathVariable(value = "id") long id) {
        return userService.findUserById(id);
    }

    @PutMapping("/{idUser}/friends/{idFriend}")
    public void addFriend(@PathVariable long idUser, @PathVariable long idFriend) {
        userService.addFriend(idUser, idFriend);
    }

    @DeleteMapping("/{idUser}/friends/{idFriend}")
    public void deleteFriend(@PathVariable long idUser, @PathVariable long idFriend) {
        userService.removeFriend(idUser, idFriend);
    }

    @GetMapping("/{userId}/friends")
    public List<User> getUserFriends(@PathVariable long userId) {
        return userService.getUserFriends(userId);
    }

    @GetMapping("/{idUser}/friends/common/{idOther}")
    public List<User> getCommonFriends(@PathVariable long idUser, @PathVariable Long idOther) {
        return userService.getCommonFriends(idUser, idOther);
    }

    @PostMapping
    public User create(@RequestBody @Valid User user) throws ValidationException {
        return userService.create(user);
    }

    @PutMapping
    public User updateUserInfo(@RequestBody @Valid User user) throws ValidationException {
        return userService.update(user);
    }
}