package uz.urinov.kun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.urinov.kun.dto.ArticleTypeCreateDto;
import uz.urinov.kun.dto.ArticleTypeResponseDto;
import uz.urinov.kun.dto.JwtDTO;
import uz.urinov.kun.enums.LanguageEnum;
import uz.urinov.kun.enums.ProfileRole;
import uz.urinov.kun.enums.Result;
import uz.urinov.kun.exp.AppForbiddenException;
import uz.urinov.kun.service.ArticleTypeService;
import uz.urinov.kun.util.SecurityUtil;

@RestController
@RequestMapping("/article-type")
public class ArticleTypeController {

    @Autowired
    private ArticleTypeService articleTypeService;

    // 1. Create ArticleType (ADMIN)
    @PostMapping("/create")
    public ResponseEntity<ArticleTypeResponseDto> createType(@RequestBody ArticleTypeCreateDto articleTypeDto,
                                                             @RequestHeader() String token) {
        JwtDTO dto = SecurityUtil.getJwtDTO(token);
        if (!dto.getRole().equals(ProfileRole.ROLE_ADMIN)) {
            throw new AppForbiddenException("Kechirasiz sizda bunday huquq yo'q");
        }
        ArticleTypeResponseDto result = articleTypeService.createType(articleTypeDto);
        return ResponseEntity.ok().body(result);
    }

    // 2. Update ArticleType (ADMIN)
    @PutMapping("/update/{id}")
    public ResponseEntity<Result> updateType(@PathVariable int id,
                                             @RequestBody ArticleTypeCreateDto articleTypeDto,
                                             @RequestHeader("Authorization") String token ){

        JwtDTO dto = SecurityUtil.getJwtDTO(token);
        if (!dto.getRole().equals(ProfileRole.ROLE_ADMIN)) {
            throw new AppForbiddenException("Kechirasiz sizda bunday huquq yo'q");
        }
        Result result = articleTypeService.updateType(id, articleTypeDto);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(result);
    }

    // 3. List ArticleType (ADMIN) (Pagination)
    @GetMapping("/page")
    public ResponseEntity<PageImpl<ArticleTypeResponseDto>> getArticleTypeList(@RequestParam("page") int page,
                                                                               @RequestParam("size") int size,
                                                                               @RequestHeader("Authorization") String token) {

        JwtDTO dto = SecurityUtil.getJwtDTO(token);
        if (!dto.getRole().equals(ProfileRole.ROLE_ADMIN)) {
            throw new AppForbiddenException("Kechirasiz sizda bunday huquq yo'q");
        }
        PageImpl<ArticleTypeResponseDto> articleTypeDtoList = articleTypeService.getArticleTypeList(page - 1, size);
        return ResponseEntity.status(HttpStatus.OK).body(articleTypeDtoList);
    }

    // 4. Delete ArticleType (ADMIN)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Result> deleteArticleType(@PathVariable int id,
                                                    @RequestHeader("Authorization") String token) {

        JwtDTO dto = SecurityUtil.getJwtDTO(token);
        if (!dto.getRole().equals(ProfileRole.ROLE_ADMIN)) {
            throw new AppForbiddenException("Kechirasiz sizda bunday huquq yo'q");
        }
        Result result = articleTypeService.deleteArticleType(id);
        return ResponseEntity.status(result.isSuccess()?HttpStatus.OK:HttpStatus.CONFLICT).body(result);
    }

    // 5. Get By Lang ArticleType
    @GetMapping("/lang")
    public ResponseEntity<PageImpl<ArticleTypeResponseDto>> getArticleTypePage(@RequestParam("page") int page,
                                                                               @RequestParam("size") int size,
                                                                               @RequestHeader(value = "Accept-Language") LanguageEnum lang) {
        PageImpl<ArticleTypeResponseDto> articleTypeDtoList = articleTypeService.getArticleTypePage2(page - 1, size, lang);
        return ResponseEntity.status(HttpStatus.OK).body(articleTypeDtoList);
    }

}
