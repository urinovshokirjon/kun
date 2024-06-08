package uz.urinov.kun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.urinov.kun.dto.TypesCreateDto;
import uz.urinov.kun.dto.TypesResponseDto;
import uz.urinov.kun.enums.LanguageEnum;
import uz.urinov.kun.enums.Result;
import uz.urinov.kun.service.TypesService;

@RestController
@RequestMapping("/types")
public class TypesController {

    @Autowired
    private TypesService typesService;

    // 1. Create ArticleType (ADMIN)
    @PostMapping("/adm/create")
    public ResponseEntity<TypesResponseDto> createType(@RequestBody TypesCreateDto articleTypeDto) {

        TypesResponseDto result = typesService.createType(articleTypeDto);
        return ResponseEntity.ok().body(result);
    }

    // 2. Update ArticleType (ADMIN)
    @PutMapping("/adm/update/{id}")
    public ResponseEntity<Result> updateType(@PathVariable int id,
                                             @RequestBody TypesCreateDto articleTypeDto ){

        Result result = typesService.updateType(id, articleTypeDto);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(result);
    }

    // 3. List ArticleType (ADMIN) (Pagination)
    @GetMapping("/adm/page")
    public ResponseEntity<PageImpl<TypesResponseDto>> getArticleTypeList(@RequestParam("page") int page,
                                                                         @RequestParam("size") int size) {


        PageImpl<TypesResponseDto> articleTypeDtoList = typesService.getArticleTypeList(page - 1, size);
        return ResponseEntity.status(HttpStatus.OK).body(articleTypeDtoList);
    }

    // 4. Delete ArticleType (ADMIN)
    @DeleteMapping("/adm/delete/{id}")
    public ResponseEntity<Result> deleteArticleType(@PathVariable int id) {

        Result result = typesService.deleteArticleType(id);
        return ResponseEntity.status(result.isSuccess()?HttpStatus.OK:HttpStatus.CONFLICT).body(result);
    }

    // 5. Get By Lang ArticleType
    @GetMapping("/lang")
    public ResponseEntity<PageImpl<TypesResponseDto>> getArticleTypePage(@RequestParam("page") int page,
                                                                         @RequestParam("size") int size,
                                                                         @RequestHeader(value = "Accept-Language") LanguageEnum lang) {
        PageImpl<TypesResponseDto> articleTypeDtoList = typesService.getArticleTypePage2(page - 1, size, lang);
        return ResponseEntity.status(HttpStatus.OK).body(articleTypeDtoList);
    }

}
