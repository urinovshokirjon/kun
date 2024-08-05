package uz.urinov.kun.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.urinov.kun.dto.ArticleLikeCreateDto;
import uz.urinov.kun.dto.ArticleLikeResponseDto;
import uz.urinov.kun.service.ArticleLikeService;

@SecurityRequirement(name = "Authorization")
@RestController
@RequestMapping("/article-like")
public class ArticleLikeController {
    @Autowired
    private ArticleLikeService articleLikeService;

    // 1. Profile Article like and dislike
    @PostMapping("/like-dislike")
    public ResponseEntity<ArticleLikeResponseDto> addArticleLikeAndDislike(@Valid @RequestBody ArticleLikeCreateDto articleLikeCreateDto) {
        ArticleLikeResponseDto articleLikeResponseDto=articleLikeService.addArticleLikeAndDislike(articleLikeCreateDto);
        return ResponseEntity.ok(articleLikeResponseDto);
    }
}
