package ru.otus.homework.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "authors")
public class Author {
    @Id
    private  long id;

    @Field(name = "fio")
    private  String fullName;

    public Author(long id) {
        this.id = id;
    }

    public Author(String fullName) {
        this.fullName = fullName;
    }
}
