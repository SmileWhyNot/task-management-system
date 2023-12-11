package vlad.kuchuk.taskmanagementsystem.comments.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import vlad.kuchuk.taskmanagementsystem.comments.dto.CommentDto;
import vlad.kuchuk.taskmanagementsystem.comments.dto.CreateCommentRequest;
import vlad.kuchuk.taskmanagementsystem.comments.service.CommentsService;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
@Slf4j
@Tag(
        name = "Comments API",
        description = "Comments API containing create and get operations"
)
public class CommentsController {

    private final CommentsService commentsService;

    @GetMapping("/{taskId}")
    @ResponseStatus(value = org.springframework.http.HttpStatus.OK)
    @Operation(
            summary = "Get Comments",
            description = "Retrieve a list of comments for a specific task."
    )
    public Set<CommentDto> getComments(@PathVariable Long taskId) {
        return commentsService.getComments(taskId);
    }

    @PostMapping
    @ResponseStatus(value = org.springframework.http.HttpStatus.CREATED)
    @Operation(
            summary = "Create Comment",
            description = "Create a new comment for a specific task."
    )
    public CommentDto createComment(@RequestBody @Valid CreateCommentRequest commentDto) {
        return commentsService.createComment(commentDto);
    }
}
