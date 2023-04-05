package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Builder
public class Friend {
    private long id;
    private long userId;
    private long friendId;
    private boolean friendStatus;

    public Friend(long id, long userId, long friendId, boolean friendStatus) {
        this.id = id;
        this.userId = userId;
        this.friendId = friendId;
        this.friendStatus = friendStatus;
    }
}