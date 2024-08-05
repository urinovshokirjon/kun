package uz.urinov.kun.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.urinov.kun.dto.CommentLikeCreateDto;
import uz.urinov.kun.dto.CommentLikeResponseDto;
import uz.urinov.kun.service.CommentLikeService;

@RestController
@RequestMapping("/comment-like")
public class CommentLikeController {
    @Autowired
    private CommentLikeService commentLikeService;

    // 1. Profile Comment like and dislike
    @PostMapping("/like-dislike")
    public ResponseEntity<CommentLikeResponseDto> addCommentLikeAndDislike(@Valid @RequestBody CommentLikeCreateDto commentLikeCreateDto) {
        CommentLikeResponseDto articleLikeResponseDto=commentLikeService.addCommentLikeAndDislike(commentLikeCreateDto);
        return ResponseEntity.ok(articleLikeResponseDto);
    }
}
