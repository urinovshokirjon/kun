package uz.urinov.kun.controller;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.urinov.kun.dto.ArticleTypeDto;
import uz.urinov.kun.dto.Result;
import uz.urinov.kun.service.ArticleTypeService;

import java.util.List;

@RestController
@RequestMapping("/article-type")
public class ArticleTypeCategory {
    @Autowired
    private ArticleTypeService articleTypeService;

    // 1. Create ArticleType
    @PostMapping("/create")
    public ResponseEntity<Result> createType(@RequestBody ArticleTypeDto articleTypeDto) {
        Result result = articleTypeService.createType(articleTypeDto);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(result);
    }

    // 2. Update ArticleType
    @PutMapping("/update/{id}")
    public ResponseEntity<Result> updateType(@PathVariable int id, @RequestBody ArticleTypeDto articleTypeDto) {
        Result result=articleTypeService.updateType(id,articleTypeDto);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(result);
    }

    // 3. List ArticleType
    @GetMapping("/list")
    public ResponseEntity<List<ArticleTypeDto>> getArticleTypeList() {
        List<ArticleTypeDto> articleTypeDtoList=articleTypeService.getArticleTypeList();
        return ResponseEntity.status(HttpStatus.OK).body(articleTypeDtoList);
    }

    // 4. Delete ArticleType
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Result> deleteArticleType(@PathVariable int id) {
        Result result=articleTypeService.deleteArticleType(id);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(result);
    }
}
