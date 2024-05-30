package uz.urinov.kun.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.urinov.kun.dto.ArticleCreateDto;
import uz.urinov.kun.dto.JwtDTO;
import uz.urinov.kun.enums.ProfileRole;
import uz.urinov.kun.enums.Result;
import uz.urinov.kun.exp.AppForbiddenException;
import uz.urinov.kun.service.ArticleService;
import uz.urinov.kun.util.SecurityUtil;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @PostMapping
    public ResponseEntity<Result> createArticle(@RequestBody ArticleCreateDto createDto,
                                                @RequestHeader("Authorization") String token) {

        JwtDTO dto = SecurityUtil.getJwtDTO(token);
        if (!dto.getRole().equals(ProfileRole.ROLE_MODERATOR)) {
            throw new AppForbiddenException("Kechirasiz sizda bunday huquq yo'q");
        }

        Result result = articleService.createArticle(createDto, dto.getId());
        return ResponseEntity.status(result.isSuccess()? HttpStatus.CREATED:HttpStatus.CONFLICT).body(result);
    }


}
