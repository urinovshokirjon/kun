package uz.urinov.kun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.urinov.kun.dto.ArticleTypeCreateDto;
import uz.urinov.kun.dto.ArticleTypeResponseDto;
import uz.urinov.kun.enums.LanguageEnum;
import uz.urinov.kun.service.ArticleTypeService;

@RestController
@RequestMapping("/article-type")
public class ArticleTypeController {

    @Autowired
    private ArticleTypeService articleTypeService;

    // 1. Create ArticleType
    @PostMapping("/create")
    public ResponseEntity<ArticleTypeResponseDto> createType(@RequestBody ArticleTypeCreateDto articleTypeDto) {
        ArticleTypeResponseDto result = articleTypeService.createType(articleTypeDto);
        return ResponseEntity.ok().body(result);
    }

    // 2. Update ArticleType
    @PutMapping("/update/{id}")
    public ResponseEntity<Boolean> updateType(@PathVariable int id, @RequestBody ArticleTypeCreateDto articleTypeDto) {
        Boolean result=articleTypeService.updateType(id,articleTypeDto);
        return ResponseEntity.ok().body(result);
    }

    // 3. List ArticleType (Pagination)
    @GetMapping("/page")
    public ResponseEntity<PageImpl<ArticleTypeResponseDto>> getArticleTypeList(@RequestParam("page") int page,
                                                                             @RequestParam("size") int size) {
        PageImpl<ArticleTypeResponseDto> articleTypeDtoList=articleTypeService.getArticleTypeList(page-1,size);
        return ResponseEntity.status(HttpStatus.OK).body(articleTypeDtoList);
    }

    // 4. Delete ArticleType
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteArticleType(@PathVariable int id) {
        Boolean result=articleTypeService.deleteArticleType(id);
        return ResponseEntity.ok().body(result);
    }

    // 5. Get By Lang ArticleType
    @GetMapping("/lang")
    public ResponseEntity<PageImpl<ArticleTypeResponseDto>> getArticleTypePage(@RequestParam("page") int page,
                                                                               @RequestParam("size") int size,
                                                                               @RequestHeader(value = "Accept-Language") LanguageEnum lang) {
        PageImpl<ArticleTypeResponseDto> articleTypeDtoList=articleTypeService.getArticleTypePage2(page-1,size,lang);
        return ResponseEntity.status(HttpStatus.OK).body(articleTypeDtoList);
    }

}
