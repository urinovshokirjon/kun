package uz.urinov.kun.dto;


import lombok.Data;

import java.util.List;

@Data
public class ArticleCreateDto {

    private String title;                 // Yangilikning nomi

    private String description;           // Yangilik haqida qisqacha malumot

    private String content;               // Malumotni to'liq qismi

    private Integer imageId;              // Yangilikni rasmining Id si

    private Integer regionId;             // Bu yangilik qayer(region) da sodir bo'ldi

    private Integer categoryId;           // Bu yangilik qanday category ga tegishli  bo'ladi

    private List<Integer> articleTypeIds; // Bu yangilikning artic type lari (bir nechta bo'lishi mumkin)


}
