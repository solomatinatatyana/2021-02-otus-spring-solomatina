package ru.otus.homework.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework.utils.StreamProvider;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ConsoleServiceImplTest {

    private ByteArrayOutputStream bos;
    private ByteArrayInputStream bis;

    @Mock
    private StreamProvider streamProvider;
    @InjectMocks
    private ConsoleServiceImpl consoleService;

    @BeforeEach
    void setUp(){
        bos = new ByteArrayOutputStream();
        bis = new ByteArrayInputStream("Test".getBytes());
        streamProvider = new StreamProvider(new PrintStream(bos),bis);
        consoleService = new ConsoleServiceImpl(streamProvider);
    }

    @DisplayName("Должно напечататься \"Text\"")
    @Test()
    public void shouldPrintText(){
        consoleService.out("Text");
        assertEquals("Text\r\n", bos.toString());
    }

    @DisplayName("Должно вернуться \"Test\"")
    @Test()
    public void isShouldReturnNotNullInputStream() throws IOException {
        String test = consoleService.ask("");
        assertEquals("Test",test);
    }
}
