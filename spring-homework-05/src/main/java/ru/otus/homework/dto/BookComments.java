package ru.otus.homework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.homework.domain.Book;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookComments {
    private Book book;
    private long CommentsCount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookComments)) return false;
        BookComments that = (BookComments) o;
        return getCommentsCount() == that.getCommentsCount() &&
                Objects.equals(getBook(), that.getBook());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBook(), getCommentsCount());
    }
}
