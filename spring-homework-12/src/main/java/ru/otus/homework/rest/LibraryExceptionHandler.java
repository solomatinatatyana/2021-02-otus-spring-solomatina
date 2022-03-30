package ru.otus.homework.rest;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.homework.exceptions.AuthorException;
import ru.otus.homework.exceptions.BookException;
import ru.otus.homework.exceptions.GenreException;

@ControllerAdvice
public class LibraryExceptionHandler {

    @ExceptionHandler(AuthorException.class)
    public ModelAndView handleNotFound(@ModelAttribute("ex") AuthorException ex){
        return new ModelAndView("/error/404","error",ex.getMessage());
    }

    @ExceptionHandler(BookException.class)
    public ModelAndView handleNotFound(@ModelAttribute("ex") BookException ex){
        return new ModelAndView("/error/404","error",ex.getMessage());
    }

    @ExceptionHandler(GenreException.class)
    public ModelAndView handleNotFound(@ModelAttribute("ex") GenreException ex){
        return new ModelAndView("/error/404","error",ex.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView accessError(@ModelAttribute("ex") AccessDeniedException ex){
        return new ModelAndView("/error/403","error",ex.getMessage());
    }
}
