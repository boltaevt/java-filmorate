package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.Objects;

@Data
@Slf4j
public class User {

    protected int id;
    @NotBlank
    @Email(message = "Адрес электронной почты не соответствует формату: xxxxx@yyyy.hh.")
    protected String email;
    @NotBlank(message = "Логин не может состоять из пробелов.")
    protected String login;
    protected String name;
    @Past(message = "Дата рождения не может быть в будущем.")
    protected LocalDate birthday;

    public User(int id, String email, String login, String name, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
