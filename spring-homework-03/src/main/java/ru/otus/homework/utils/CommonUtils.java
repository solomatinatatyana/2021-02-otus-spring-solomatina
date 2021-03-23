package ru.otus.homework.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CommonUtils {

    public static BufferedReader getBufferedReaderFromResource(String fileName){
        InputStream resource = CommonUtils.class.getClassLoader().getResourceAsStream(fileName);
        return new BufferedReader(getInputStreamReader(resource));
    }

    public static InputStreamReader getInputStreamReader(InputStream in){
        return new InputStreamReader(in);
    }
}
