package uz.urinov.kun.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import uz.urinov.kun.enums.LikeStatus;

import java.time.LocalDateTime;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleLikeResponseDto {
    private Integer id;
    private String articleId;
    private Integer profileId;
    private LikeStatus status;
    private LocalDateTime createdAt;
}
