package ru.otus.homework.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.test.context.TestPropertySource;
import ru.otus.homework.service.locale.LocaleServiceImpl;
import ru.otus.homework.utils.LocaleProvider;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@TestPropertySource("classpath:application.yml")
@SpringBootTest
public class LocaleServiceImplTest {

    @MockBean
    private LocaleProvider localeProvider;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private LocaleServiceImpl localeService;

    @ParameterizedTest
    @MethodSource("generateData")
    @DisplayName("Должно вернуться значение параметра survey.pre-login для каждой локали")
    public void shouldPrintText(String locale, String expectedMessage){
        given(localeProvider.getName()).willReturn(locale);
        String info = localeService.getLocalizationMessage("survey.pre-login");
        System.out.println(info);
        assertEquals(expectedMessage,info);
    }

    private static Stream<Arguments> generateData() {
        return Stream.of(
                Arguments.of("ru-RU", "Сначала представьтесь Тест"),
                Arguments.of("en-US", "First login, please Test"),
                Arguments.of("en_EN", "First login, please default Test")
        );
    }
}
