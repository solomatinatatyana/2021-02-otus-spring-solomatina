package ru.otus.homework.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "books")
@NamedEntityGraph(name = "book-entity-graph",
        attributeNodes = {
        @NamedAttributeNode("author"),
        @NamedAttributeNode("genre")
    }
)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @ManyToOne(targetEntity = Author.class, cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    private List<Comment> comments;

    public Book(long id, String title, Author author, Genre genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public Book(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Id="+ id +
                ", Название='" + title + '\'' +
                ", автор=" + author.getFullName() +
                ", Жанр=" + genre.getName()+"}";
    }
}
