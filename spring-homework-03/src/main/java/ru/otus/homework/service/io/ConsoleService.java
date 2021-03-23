package ru.otus.homework.service.io;

import java.io.IOException;

public interface ConsoleService {
    void out(String message);
    String ask(String message) throws IOException;
}
