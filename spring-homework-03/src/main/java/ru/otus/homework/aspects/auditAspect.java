package ru.otus.homework.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class auditAspect {
    @AfterThrowing("execution(* ru.otus.homework.dao.SurveyDaoImpl.*(..))")
    public void auditAfter(JoinPoint joinPoint){
        System.out.println("Вызов исключения из метода : " + joinPoint.getSignature().getName());
    }
}
