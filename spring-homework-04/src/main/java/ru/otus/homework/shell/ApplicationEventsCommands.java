package ru.otus.homework.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework.domain.Student;
import ru.otus.homework.service.locale.LocaleService;
import ru.otus.homework.service.survey.SurveyService;

import java.io.IOException;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationEventsCommands {
    private final SurveyService surveyService;
    private final LocaleService localeService;

    private String firstName;
    private String secondName;

    @ShellMethod(value = "login to survey", key = {"l","login"})
    public Student login(@ShellOption(defaultValue = "defaultFirstName") String firstName,
                         @ShellOption(defaultValue = "defaultSecondName") String secondName){
        this.firstName = firstName;
        this.secondName = secondName;
        return new Student(firstName,secondName);
    }

    @ShellMethod(value = "Start Survey", key = {"s","start"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String takeASurvey() throws IOException {
        return surveyService.takeASurvey();
    }

    private Availability isPublishEventCommandAvailable() {
        return firstName == null & secondName==null? Availability.unavailable(localeService.getLocalizationMessage("survey.pre-login")): Availability.available();
    }
}
