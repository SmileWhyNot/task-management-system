package vlad.kuchuk.taskmanagementsystem.tasks.dto;

import org.mapstruct.*;
import vlad.kuchuk.taskmanagementsystem.tasks.entity.Task;

import java.util.Optional;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskMapper {
    Task toEntity(TaskDto taskDto);

    @AfterMapping
    default void linkComments(@MappingTarget Task task) {
        Optional.ofNullable(task.getComments())
                .ifPresent(comments -> comments.forEach(comment -> comment.setTask(task)));
    }

    TaskDto toDto(Task task);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "assignee", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "comments", ignore = true)
    Task partialUpdate(TaskDto taskDto, @MappingTarget Task task);
}