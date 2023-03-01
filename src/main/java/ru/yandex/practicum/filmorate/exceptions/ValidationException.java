package ru.yandex.practicum.filmorate.exceptions;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

class ValidationException extends ConstraintViolationException {

    public ValidationException(String message, Set<? extends ConstraintViolation<?>> constraintViolations) {
        super(message, constraintViolations);
    }
}