package vlad.kuchuk.taskmanagementsystem.comments.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import vlad.kuchuk.taskmanagementsystem.comments.entity.Comment;

import java.util.List;
import java.util.Set;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommentMapper {
    @Mapping(target = "creationDate", expression = "java(java.time.ZonedDateTime.now())")
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "task", ignore = true)
    Comment toEntity(CreateCommentRequest commentDto);

    CommentDto toDto(Comment comment);

    Set<CommentDto> toDtoSet(List<Comment> comments);
}