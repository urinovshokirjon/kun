package uz.urinov.kun.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import uz.urinov.kun.enums.ArticleStatus;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleShortInfoDto {

    private String id;

    private String title;   // Yangilikning nomi

    private String description; /// Yangilik haqida qisqacha malumot

    private Integer imageId;      // Yangilikni rasmining Id si
}
