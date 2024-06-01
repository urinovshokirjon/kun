package uz.urinov.kun.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class ArticleCreateDto {
    @NotBlank(message = "Title bo'sh bo'lishi mumkin emas")
    @Size(min = 3,  message = "Berilgan title ning uzunligi 3 ta harifdan kam bo'lishi mumkin emas")
    private String title;                 // Yangilikning nomi

    @NotBlank(message = "Description bo'sh bo'lishi mumkin emas")
    @Size(min = 3,  message = "Berilgan description ning uzunligi 3 ta harifdan kam bo'lishi mumkin emas")
    private String description;           // Yangilik haqida qisqacha malumot

    @NotBlank(message = "Description bo'sh bo'lishi mumkin emas")
    @Size(min = 3,  message = "Berilgan description ning uzunligi 3 ta harifdan kam bo'lishi mumkin emas")
    private String content;               // Malumotni to'liq qismi

    private Integer imageId;              // Yangilikni rasmining Id si

    @NotNull(message = " Region number bo'sh bo'lishi mumkin emas")
    @Min(value = 1, message = "Region number ning qiymati minimal 1 bo'lsin")
    private Integer regionId;             // Bu yangilik qayer(region) da sodir bo'ldi

    @NotNull(message = " Category number bo'sh bo'lishi mumkin emas")
    @Min(value = 1, message = "Rategory number ning qiymati minimal 1 bo'lsin")
    private Integer categoryId;           // Bu yangilik qanday category ga tegishli  bo'ladi

    @NotNull(message = "ArticleType Id number bo'sh bo'lishi mumkin emas")
    @Min(value = 1, message = "ArticleType Id  number ning qiymati minimal 1 bo'lsin")
    private List<Integer> articleTypeIds; // Bu yangilikning artic type lari (bir nechta bo'lishi mumkin)


}
