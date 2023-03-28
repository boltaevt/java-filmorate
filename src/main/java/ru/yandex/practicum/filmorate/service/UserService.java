package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.FriendDbStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;

@Service
public class UserService {

    private final UserStorage userStorage;
    private final FriendDbStorage friendDbStorage;

    @Autowired
    public UserService(UserStorage userStorage, FriendDbStorage friendDbStorage) {
        this.userStorage = userStorage;
        this.friendDbStorage = friendDbStorage;
    }

    public void addFriend(long idUser, long idFriend) {
        if ((userStorage.checkUserExists(idUser)) && (userStorage.checkUserExists(idFriend))) {
            friendDbStorage.addFriend(idUser, idFriend);
        } else if (!userStorage.checkUserExists(idUser)) {
            throw new ObjectNotFoundException("User with id '" + idUser + "' not found.");
        } else {
            throw new ObjectNotFoundException("User with id '" + idFriend + "' not found.");
        }
    }

    public void removeFriend(long idUser, long idFriend) {
        friendDbStorage.deleteFriend(idUser, idFriend);
        userStorage.findUserById(idUser).getFriendIds().remove(idFriend);
    }

    public List<User> getUserFriends(long idUser) {
        List<Long> setOfFriends = friendDbStorage.findUserFriends(idUser);
        return new ArrayList<>(userStorage.findAllByIdIn(setOfFriends));
    }

    public List<User> getCommonFriends(long idUser, long idOther) {
        List<Long> userFriends = new ArrayList<>(List.copyOf(friendDbStorage.findUserFriends(idUser)));
        List<Long> otherFriends = new ArrayList<>(List.copyOf(friendDbStorage.findUserFriends(idOther)));
        List<User> commonFriends = new ArrayList<>();
        userFriends.retainAll(otherFriends);
        if (!userFriends.isEmpty()) {
            commonFriends.addAll(userStorage.findAllByIdIn(userFriends));
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
        User user = userStorage.findUserById(id);
        user.getFriendIds().addAll(friendDbStorage.findUserFriends(id));
        return user;
    }
}




