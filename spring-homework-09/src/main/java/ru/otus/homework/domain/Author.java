package ru.otus.homework.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;

    @Column(name = "fio",nullable = false, unique = true)
    private  String fullName;

    public Author(long id) {
        this.id = id;
    }

    public Author(String fullName) {
        this.fullName = fullName;
    }
}
