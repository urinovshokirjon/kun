package uz.urinov.kun.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.urinov.kun.dto.ArticleCreateDto;
import uz.urinov.kun.dto.ArticleResponseDto;
import uz.urinov.kun.dto.ArticleShortInfoDto;
import uz.urinov.kun.dto.JwtDTO;
import uz.urinov.kun.enums.LanguageEnum;
import uz.urinov.kun.enums.ProfileRole;
import uz.urinov.kun.enums.Result;
import uz.urinov.kun.exp.AppForbiddenException;
import uz.urinov.kun.service.ArticleService;
import uz.urinov.kun.util.SecurityUtil;

import java.util.List;

@SecurityRequirement(name = "Authorization")
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    ArticleService articleService;


    // 1. CREATE (Moderator) status(NotPublished)
//    @PreAuthorize("hasAnyRole('MODIRATOR')")
    @PostMapping("/moderator")
    public ResponseEntity<ArticleResponseDto> createArticle(@Valid @RequestBody ArticleCreateDto createDto) {
        return ResponseEntity.ok(articleService.createArticle(createDto));
    }

    // 2. Update (Moderator (status to not publish)) (remove old image) (title,description,content,shared_count,image_id, region_id,category_id)
    @PutMapping("/moderator/update/{id}")
    public ResponseEntity<ArticleResponseDto> updateArticle(@RequestBody ArticleCreateDto createDto,
                                                @PathVariable(value = "id") String articleId) {
        return ResponseEntity.ok(articleService.updateArticle(articleId,createDto));
    }

    // 3. Delete Article (MODERATOR)
//    @PreAuthorize("hasAnyRole('MODIRATOR','PUBLISH')")
    @PostMapping("/moderator/delete/{id}")
    public ResponseEntity<Result> deleteArticle(@PathVariable(value = "id") String articleId) {

        Result result = articleService.deleteArticle(articleId);
        return ResponseEntity.status(result.isSuccess()? HttpStatus.CREATED:HttpStatus.CONFLICT).body(result);
    }

    // 4. Change status by id (PUBLISHER) (publish,not_publish) bitta aqilliy tekshirib ruxsat berdi
//    @PreAuthorize("hasAnyRole('PUBLISHER')")
    @PostMapping("/publisher/{id}")
    public ResponseEntity<Result> publisherArticle(@PathVariable(value = "id") String articleId) {
        Result result = articleService.publisherArticle(articleId);
        return ResponseEntity.status(result.isSuccess()? HttpStatus.CREATED:HttpStatus.CONFLICT).body(result);
    }

    // 5. Get Last 5 Article By Types  ordered_by_created_date ArticleShortInfo
    @GetMapping("/get-list-top5")
    public ResponseEntity<List<ArticleShortInfoDto>> getLast5ArticleByTypes(@RequestParam Integer types){
        List<ArticleShortInfoDto> responseDtoList=articleService.getLast5ArticleByTypes(types);
        return ResponseEntity.status(HttpStatus.OK).body(responseDtoList);
    }

    // 6. Get Last 3 Article By Types  ordered_by_created_date ArticleShortInfo
    @GetMapping("/get-list-top3")
    public ResponseEntity<List<ArticleShortInfoDto>> getLast3ArticleByTypes(@RequestParam Integer types){
        List<ArticleShortInfoDto> responseDtoList=articleService.getLast3ArticleByTypes(types);
        return ResponseEntity.status(HttpStatus.OK).body(responseDtoList);
    }

    // 7. Get Last 8  Articles witch id not included in given list.([1,2,3]) ArticleShortInfo
    @GetMapping("/get-last-top8")
    public ResponseEntity<List<ArticleShortInfoDto>> getLast8ArticleByTypes(@RequestParam List<String> ids){
        List<ArticleShortInfoDto> responseDtoList=articleService.getLast8ArticleByTypes(ids);
        return ResponseEntity.status(HttpStatus.OK).body(responseDtoList);
    }

    // 8. Get Article By Id And Lang ArticleFullInfo
    @GetMapping("/getById-Lang")
    public ResponseEntity<ArticleShortInfoDto> getArticleByIdAndLang(@RequestParam String id){
        ArticleShortInfoDto responseDtoList=articleService.getArticleByIdAndLang(id);
        return ResponseEntity.status(HttpStatus.OK).body(responseDtoList);
    }

    //  9. Get Last 4 Article By Types and except given article id. ArticleShortInfo
    @GetMapping("/getArticleByTypesExceptId")
    public ResponseEntity<List<ArticleShortInfoDto>> getLast4ArticleByTypesExceptId(@RequestParam Integer typesId,@RequestParam String articleId){
        List<ArticleShortInfoDto> responseDtoList=articleService.getLast4ArticleByTypesExceptId(typesId,articleId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDtoList);
    }

    // 10. Get 4 most read articles ArticleShortInfo
    @GetMapping("/Get4MostReadArticles")
    public ResponseEntity<List<ArticleShortInfoDto>> get4MostReadArticles(@RequestParam Integer typesId){
        List<ArticleShortInfoDto> responseDtoList=articleService.get4MostReadArticles(typesId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDtoList);
    }

}
