package ru.yandex.practicum.filmorate.storage.user;

import java.util.List;

public interface FriendStorage {

    void addFriend(long userId, long userFriendId);

    void deleteFriend(long userId, long friendId);

    boolean confirmUserHasFriends(long userId);

    List<Long> findUserFriends(long userId);

}
