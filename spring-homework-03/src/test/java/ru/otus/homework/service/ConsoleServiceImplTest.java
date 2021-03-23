package ru.otus.homework.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import ru.otus.homework.service.io.ConsoleServiceImpl;
import ru.otus.homework.utils.StreamProvider;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@TestPropertySource("classpath:application.yml")
@SpringBootTest
public class ConsoleServiceImplTest {

    private ByteArrayOutputStream bos;
    private ByteArrayInputStream bis;

    @MockBean
    private StreamProvider streamProvider;
    @Autowired
    private ConsoleServiceImpl consoleService;

    @BeforeEach
    void setUp(){
        bos = new ByteArrayOutputStream();
        bis = new ByteArrayInputStream("Test".getBytes());
        given(streamProvider.getOut()).willReturn(new PrintStream(bos));
        given(streamProvider.getIn()).willReturn(bis);
        given(streamProvider.getBf()).willReturn(new BufferedReader(new InputStreamReader(bis)));
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
