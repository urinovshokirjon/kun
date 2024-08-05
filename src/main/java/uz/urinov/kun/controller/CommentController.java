package uz.urinov.kun.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.urinov.kun.dto.CommentCreateDto;
import uz.urinov.kun.enums.Result;
import uz.urinov.kun.service.CommentService;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    // 1. Comment add
    @PostMapping()
    public ResponseEntity<Result> addComment(@Valid @RequestBody CommentCreateDto commentCreateDto) {
        Result result = commentService.addComment(commentCreateDto);
        return ResponseEntity.status(result.isSuccess() ? 201 : 409).body(result);
    }
}
