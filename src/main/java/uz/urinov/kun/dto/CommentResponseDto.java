package uz.urinov.kun.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.urinov.kun.entity.ArticleEntity;
import uz.urinov.kun.entity.ProfileEntity;

import java.time.LocalDateTime;

@Setter
@Getter
public class CommentResponseDto {

    private Integer id;

    private Integer profileId;

    private Integer commentId;

    private String content;

    private Integer replyId;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

}
