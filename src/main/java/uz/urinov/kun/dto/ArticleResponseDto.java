package uz.urinov.kun.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import uz.urinov.kun.enums.ArticleStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleResponseDto {

    private String id;

    private String title;   // Yangilikning nomi

    private String description; /// Yangilik haqida qisqacha malumot

    private String content;       ///Malumotni to'liq qismi

    private Integer sharedCount;  // Yangilikni ulashilganlar soni

    private Integer viewCount;    // Yangilikni ko'rilganlar soni

    private Integer imageId;      // Yangilikni rasmining Id si

    private LocalDateTime createDate;  // Yangilikni yozilgan vaqti

    private LocalDateTime publishedDate;                 // Yangilik tahrir(tekshiruv)dan o'tgan va hammaga ko'rsatilgan vaqti

    private RegionResponseDTO region;                   // BU yangilik qayer(region) da sodir bo'ldi

    private CategoryResponseDto category;              // BU yangilik qanday category ga tegishli  bo'ladi

    private ProfileResponseDTO moderator;                  // Yangilikni yozgan odam

    private ProfileResponseDTO publisher;                  // Yangilikni tahrir(tekshirgan) odam

    private ArticleStatus status;

    private Boolean visible;

}
