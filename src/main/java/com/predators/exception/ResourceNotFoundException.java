package com.predators.exception;

public class ResourceNotFoundException extends RuntimeException {
                                                  //Создаём unchecked исключение, наследуем RuntimeException

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
