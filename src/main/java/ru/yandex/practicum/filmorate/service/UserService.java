package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addFriend(long idUser, long idFriend) {
        if (!userStorage.findAll().contains(userStorage.findUserById(idUser))) {
            throw new ObjectNotFoundException("No such User.");
        }
        if (!userStorage.findAll().contains(userStorage.findUserById(idFriend))) {
            throw new ObjectNotFoundException("No such User.");
        }
        userStorage.findUserById(idUser).getFriendsList().add(idFriend);
        userStorage.findUserById(idFriend).getFriendsList().add(idUser);
    }

    public void removeFriend(long idUser, long idFriend) {
        userStorage.findUserById(idUser).getFriendsList().remove(idFriend);
        userStorage.findUserById(idFriend).getFriendsList().remove(idUser);
    }

    public List<User> getUserFriends(long idUser) {
        List<User> listOfFriends = new ArrayList<>(); //TODO: first user has -1 in Set.
        Set<Long> setOfFriends = new HashSet<>(userStorage.findUserById(idUser).getFriendsList());
        for (Long id : setOfFriends) {
            listOfFriends.add(userStorage.findUserById(id));
        }
        return listOfFriends;
    }

    public List<User> getCommonFriends(long idUser, long idOther) {
        List<User> commonFriends = new ArrayList<>();
        for (Long id : userStorage.findUserById(idUser).getFriendsList()) {
            if (userStorage.findUserById(idOther).getFriendsList().contains(id)) {
                commonFriends.add(userStorage.findUserById(id));
            }
        }
        return commonFriends;
    }

    public List<User> showCommonFriends(User user1, User user2) {
        List<User> commonFriends = new ArrayList<>();
        for (Long id : user1.getFriendsList()) {
            if (user2.getFriendsList().contains(id)) {
                commonFriends.add(userStorage.findUserById(id));
            }
        }
        return commonFriends;
    }

    public List<User> findAll() {
        return userStorage.findAll();
    }

    public User create(User user) {
        return userStorage.create(user);
    }

    public User update(User user) {
        return userStorage.updateUserInfo(user);
    }

    public User findUserById(Long id) {
        for (User user : userStorage.findAll()) {
            if (user.getId() == id) {
                return user;
            }
        }
        throw new ObjectNotFoundException("There is no such user.");
    }
}




