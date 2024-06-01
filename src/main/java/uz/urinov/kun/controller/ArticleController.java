package uz.urinov.kun.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.urinov.kun.dto.ArticleCreateDto;
import uz.urinov.kun.dto.ArticleResponseDto;
import uz.urinov.kun.dto.JwtDTO;
import uz.urinov.kun.enums.LanguageEnum;
import uz.urinov.kun.enums.ProfileRole;
import uz.urinov.kun.enums.Result;
import uz.urinov.kun.exp.AppForbiddenException;
import uz.urinov.kun.service.ArticleService;
import uz.urinov.kun.util.SecurityUtil;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    ArticleService articleService;


    // 1. CREATE (Moderator) status(NotPublished)
    @PostMapping("/adm/created")
    public ResponseEntity<Result> createArticle(@RequestBody ArticleCreateDto createDto,
                                                @RequestHeader("Authorization") String token) {

        JwtDTO dto = SecurityUtil.getJwtDTO(token);
        if (!dto.getRole().equals(ProfileRole.ROLE_MODERATOR)) {
            throw new AppForbiddenException("Kechirasiz sizda bunday huquq yo'q");
        }

        Result result = articleService.createArticle(createDto, dto.getId());
        return ResponseEntity.status(result.isSuccess()? HttpStatus.CREATED:HttpStatus.CONFLICT).body(result);
    }

    // 2. Update (Moderator (status to not publish)) (remove old image) (title,description,content,shared_count,image_id, region_id,category_id)
    @PostMapping("/adm/update/{id}")
    public ResponseEntity<Result> updateArticle(@RequestBody ArticleCreateDto createDto,
                                                @RequestHeader("Authorization") String token,
                                                @PathVariable(value = "id") UUID articleId) {

        JwtDTO dto = SecurityUtil.getJwtDTO(token);
        if (!dto.getRole().equals(ProfileRole.ROLE_MODERATOR)) {
            throw new AppForbiddenException("Kechirasiz sizda bunday huquq yo'q");
        }

        Result result = articleService.updateArticle(createDto, dto.getId(),articleId);
        return ResponseEntity.status(result.isSuccess()? HttpStatus.CREATED:HttpStatus.CONFLICT).body(result);
    }

    // 3. Delete Article (MODERATOR)
    @PostMapping("/delete/{id}")
    public ResponseEntity<Result> deleteArticle(@RequestHeader("Authorization") String token,
                                                @PathVariable(value = "id") UUID articleId) {

        JwtDTO dto = SecurityUtil.getJwtDTO(token);
        if (!dto.getRole().equals(ProfileRole.ROLE_MODERATOR)) {
            throw new AppForbiddenException("Kechirasiz sizda bunday huquq yo'q");
        }

        Result result = articleService.deleteArticle( dto.getId(),articleId);
        return ResponseEntity.status(result.isSuccess()? HttpStatus.CREATED:HttpStatus.CONFLICT).body(result);
    }

    // 4. Change status by id (PUBLISHER) (publish,not_publish) bitta aqilliy tekshirib ruxsat berdi
    @PostMapping("/publisher/{id}")
    public ResponseEntity<Result> publisherArticle(@RequestHeader("Authorization") String token,
                                                @PathVariable(value = "id") UUID articleId) {

        JwtDTO dto = SecurityUtil.getJwtDTO(token);
        if (!dto.getRole().equals(ProfileRole.ROLE_PUBLISHER)) {
            throw new AppForbiddenException("Kechirasiz sizda bunday huquq yo'q");
        }

        Result result = articleService.publisherArticle( dto.getId(),articleId);
        return ResponseEntity.status(result.isSuccess()? HttpStatus.CREATED:HttpStatus.CONFLICT).body(result);
    }

    // 5. Get Last 5 Article By Types  ordered_by_created_date
    @GetMapping("/get-list-top5")
    public ResponseEntity<List<ArticleResponseDto>> getLast5ArticleByTypes(@RequestParam UUID types, @RequestHeader("Accept-Language")LanguageEnum languageEnum){
        List<ArticleResponseDto> responseDtoList=articleService.getLast5ArticleByTypes(types,languageEnum);
        return ResponseEntity.status(HttpStatus.OK).body(responseDtoList);
    }

}
