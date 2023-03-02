package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addFriend(long idUser, long idFriend) {
        List<Long> userIds = userStorage.findAll().stream().map(User::getId).collect(Collectors.toList());
        if (!userIds.contains(idUser)) {
            throw new ObjectNotFoundException("User with id not found " + idUser);
        }
        if (!userIds.contains(idFriend)) {
            throw new ObjectNotFoundException("User with id not found " + idFriend);
        }
        userStorage.findUserById(idUser).getFriendIds().add(idFriend);
        userStorage.findUserById(idFriend).getFriendIds().add(idUser);
    }

    public void removeFriend(long idUser, long idFriend) {
        userStorage.findUserById(idUser).getFriendIds().remove(idFriend);
        userStorage.findUserById(idFriend).getFriendIds().remove(idUser);
    }

    public List<User> getUserFriends(long idUser) {
        Set<Long> setOfFriends = new HashSet<>(userStorage.findUserById(idUser).getFriendIds());
        return new ArrayList<>(userStorage.findAllByIdIn(List.copyOf(setOfFriends)));
    }

    public List<User> getCommonFriends(long idUser, long idOther) {
        List<Long> userFriends = new ArrayList<>(List.copyOf(userStorage.findUserById(idUser).getFriendIds()));
        List<Long> otherFriends = new ArrayList<>(List.copyOf(userStorage.findUserById(idOther).getFriendIds()));
        List<User> commonFriends = new ArrayList<>();
        userFriends.retainAll(otherFriends);
        if (!userFriends.isEmpty()) {
            for (User user : userStorage.findAllByIdIn(userFriends)) {
                commonFriends.add(user);
            }
        }
        return commonFriends;

    }

//    public List<User> showCommonFriends(User user1, User user2) {
//        List<Long> common = new ArrayList<>(List.copyOf(user1.getFriendIds()));
//        common.retainAll(user2.getFriendIds());
//        return userStorage.findAllByIdIn(common);
//    }

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




