package ru.otus.homework.util;

import java.util.UUID;

public class CommonUtils {
    public static String getRandomRowId(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
