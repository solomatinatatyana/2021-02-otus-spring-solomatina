package ru.otus.homework.dao;

import com.opencsv.CSVReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.homework.domain.Question;
import ru.otus.homework.service.locale.LocaleServiceImpl;
import ru.otus.homework.utils.LocaleProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class ResourceDaoImplTest {

    @MockBean
    private LocaleProvider localeProvider;

    @Autowired
    private ResourceDao resourceDao;

    @Autowired
    private LocaleServiceImpl localeService;

    @ParameterizedTest
    @MethodSource("generateData")
    @DisplayName("Должны вернуться вопросы для каждой локали")
    public void getCsvSurvey(String locale, List<String> expectedText){
        given(localeProvider.getName()).willReturn(locale);
        CSVReader reader = resourceDao.getCsvSurvey(localeService.getLocalizationMessage("survey.name"));
        List<Question> questionList = resourceDao.getCsvToBean(reader).parse();
        List<String> questionTextList = new ArrayList<>();
        questionList.forEach(question -> questionTextList.add(question.getQuestionText()));
        assertThat(questionList.containsAll(expectedText));
    }

    private static Stream<Arguments> generateData() {
        return Stream.of(
                Arguments.of("ru-RU", Arrays.asList(
                        "Какие виды тестирования существуют?",
                        "Дымовое тестирование это...",
                        "Какие языки программирования вы знаете",
                        "REST это...",
                        "Сколько уровней в модели OSI"
                )),
                Arguments.of("en-US", Arrays.asList(
                        "What types of testing exist?",
                        "Smoke testing is...",
                        "What programming languages you know?",
                        "REST is...",
                        "How many layers has model OSI?"
                ))
        );
    }

}
