package ru.otus.homework.rest.mappers;


import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.rest.dto.CommentDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentDto toCommentDto(Comment comment);

    @InheritInverseConfiguration
    Comment toComment(CommentDto commentDto);

    List<CommentDto> toCommentDtoList(List<Comment> commentList);
}
