package ru.otus.homework.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.util.List;
import java.util.UUID;

public class CommonUtils {
    public static String getRandomRowId(){
        return UUID.randomUUID().toString().replace("-","");
    }

    public static String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    public static <T> T mapFromJson(String json, Class<T> clazz) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

    public static <T> List<T> mapListFromJson(String json, Class<T> clazz) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, TypeFactory.rawClass(clazz)));
    }
}
