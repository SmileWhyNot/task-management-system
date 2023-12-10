package vlad.kuchuk.taskmanagementsystem.comments.controller;

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
public class CommentsController {

    private final CommentsService commentsService;

    @GetMapping("/{taskId}")
    @ResponseStatus(value = org.springframework.http.HttpStatus.OK)
    public Set<CommentDto> getComments(@PathVariable Long taskId) {
        return commentsService.getComments(taskId);
    }

    @PostMapping
    @ResponseStatus(value = org.springframework.http.HttpStatus.CREATED)
    public CommentDto createComment(@RequestBody @Valid CreateCommentRequest commentDto) {
        return commentsService.createComment(commentDto);
    }
}
