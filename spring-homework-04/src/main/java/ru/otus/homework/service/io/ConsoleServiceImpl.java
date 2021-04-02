package ru.otus.homework.service.io;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.homework.utils.StreamProvider;

import java.io.BufferedReader;
import java.io.IOException;

@Service
public class ConsoleServiceImpl implements ConsoleService {

    private final BufferedReader bufferedReader;
    private final StreamProvider streamProvider;

    @Autowired
    public ConsoleServiceImpl(StreamProvider streamProvider) {
        this.streamProvider = streamProvider;
        this.bufferedReader = streamProvider.getBf();
    }

    @Override
    public void out(String message){
        streamProvider.getOut().println(message);
    }

    @Override
    public String ask(String message) throws IOException {
        out(message);
        return streamProvider.getBf().readLine();
    }
}
