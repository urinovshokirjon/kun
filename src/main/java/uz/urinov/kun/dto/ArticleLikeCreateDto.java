package uz.urinov.kun.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import uz.urinov.kun.enums.LikeStatus;

@Setter
@Getter
public class ArticleLikeCreateDto {

    @NotBlank(message = "Article Id bo'sh bo'lishi mumkin emas")
    private String articleId;
    @NotNull
    private LikeStatus status;
}
