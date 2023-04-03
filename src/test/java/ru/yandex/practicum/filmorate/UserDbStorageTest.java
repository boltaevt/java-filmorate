package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserDbStorageTest {
    private final UserDbStorage userDbStorage;
    private final UserService userService;

    @Test
    void createUser() {
        User firstU = new User();
        firstU.setId(1L);
        firstU.setEmail("java@email.ru");
        firstU.setLogin("rio");
        firstU.setName("Ivan");
        firstU.setBirthday(LocalDate.of(2000, 11, 14));

        userDbStorage.create(firstU);

        assertTrue(userDbStorage.checkUserExists(firstU.getId()));
    }

    @Test
    void update() {
        User firstU = new User();
        firstU.setId(1L);
        firstU.setEmail("terminal@email.ru");
        firstU.setLogin("xxx");
        firstU.setName("Dmitry");
        firstU.setBirthday(LocalDate.of(2000, 10, 14));

        userDbStorage.create(firstU);

        User secondU = new User();
        secondU.setId(2L);
        secondU.setEmail("oop@email.ru");
        secondU.setLogin("ddd");
        secondU.setName("Danil");
        secondU.setBirthday(LocalDate.of(2003, 12, 18));

        secondU.setId(firstU.getId());

        userDbStorage.updateUserInfo(secondU);

        User user = userService.findUserById(firstU.getId());

        assertThat(user).hasFieldOrPropertyWithValue("login", "ddd");
    }

    @Test
    void getUser() {
        User firstU = new User();
        firstU.setEmail("dev@email.ru");
        firstU.setLogin("mmm");
        firstU.setName("Mark");
        firstU.setBirthday(LocalDate.of(2000, 9, 14));

        userDbStorage.create(firstU);

        Optional<User> optionalUser = Optional.ofNullable(userService.findUserById(1L));

        assertThat(optionalUser)
                .isPresent()
                .hasValueSatisfying(user -> assertThat(user)
                        .hasFieldOrPropertyWithValue("id", 1L));
    }

    @Test
    void getAllUsers() {
        User firstU = new User();
        firstU.setEmail("terminal@email.ru");
        firstU.setLogin("xxx");
        firstU.setName("Dmitry");
        firstU.setBirthday(LocalDate.of(2000, 10, 14));


        List<User> emptyList = userDbStorage.findAll();
        userDbStorage.create(firstU);
        List<User> notEmpty = userDbStorage.findAll();

        Assertions.assertEquals(emptyList.size() + 1, notEmpty.size());
    }
}
