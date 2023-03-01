package ru.yandex.practicum.filmorate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.yandex.practicum.filmorate.controllers.UserController;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class FilmorateApplication {

    public static void main(String[] args) {
        SpringApplication.run(FilmorateApplication.class, args);
    }
}
