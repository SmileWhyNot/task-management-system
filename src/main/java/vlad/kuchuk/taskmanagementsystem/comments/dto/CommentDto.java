package vlad.kuchuk.taskmanagementsystem.comments.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import vlad.kuchuk.taskmanagementsystem.tasks.dto.SmallTaskInfoResponse;
import vlad.kuchuk.taskmanagementsystem.user.dto.SmallUserDto;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@Accessors(chain = true)
@Schema(description = """
                      Comment data for request and response\s
                      Response: full info provided by Server\s
                      Request: creationDate provided by Client is ignored. Server calculates it\s
                      """)
public class CommentDto implements Serializable {
    private Long id;
    @NotNull
    private String text;
    @NotNull
    private ZonedDateTime creationDate;
    private SmallUserDto author;
    private SmallTaskInfoResponse task;
}