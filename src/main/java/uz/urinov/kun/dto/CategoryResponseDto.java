package uz.urinov.kun.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryResponseDto {

    private Integer id;

    private Integer orderNumber;

    private String nameUz;

    private String nameRu;

    private String nameEn;

    private String name;

    private Boolean visible;

    private LocalDate createDate;
}
