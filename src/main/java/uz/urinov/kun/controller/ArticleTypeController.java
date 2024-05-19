package uz.urinov.kun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.urinov.kun.dto.ArticleTypeDto;
import uz.urinov.kun.dto.ArticleTypeLangDto;
import uz.urinov.kun.dto.Result;
import uz.urinov.kun.service.ArticleTypeService;

@RestController
@RequestMapping("/article-type")
public class ArticleTypeController {

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

    // 3. List ArticleType (Pagination)
    @GetMapping("/page")
    public ResponseEntity<PageImpl<ArticleTypeDto>> getArticleTypeList(@RequestParam("page") int page,
                                                                       @RequestParam("size") int size) {
        PageImpl<ArticleTypeDto> articleTypeDtoList=articleTypeService.getArticleTypeList(page-1,size);
        return ResponseEntity.status(HttpStatus.OK).body(articleTypeDtoList);
    }

    // 4. Delete ArticleType
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Result> deleteArticleType(@PathVariable int id) {
        Result result=articleTypeService.deleteArticleType(id);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(result);
    }

    // 5. Get By Lang ArticleType
    @GetMapping("/lang")
    public ResponseEntity<PageImpl<ArticleTypeLangDto>> getArticleTypePage(@RequestParam("page") int page,
                                                                           @RequestParam("size") int size,
                                                                           @RequestParam("lang") String lang) {
        PageImpl<ArticleTypeLangDto> articleTypeDtoList=articleTypeService.getArticleTypePage(page-1,size,lang);
        return ResponseEntity.status(HttpStatus.OK).body(articleTypeDtoList);
    }

}
