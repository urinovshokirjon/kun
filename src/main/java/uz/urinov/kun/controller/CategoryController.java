package uz.urinov.kun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.urinov.kun.dto.CategoryCreateDto;
import uz.urinov.kun.dto.CategoryResponseDto;
import uz.urinov.kun.dto.JwtDTO;
import uz.urinov.kun.enums.LanguageEnum;
import uz.urinov.kun.enums.ProfileRole;
import uz.urinov.kun.enums.Result;
import uz.urinov.kun.exp.AppForbiddenException;
import uz.urinov.kun.service.CategoryService;
import uz.urinov.kun.util.SecurityUtil;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    // 1. Create category (ADMIN)
    @PostMapping("/create")
    public ResponseEntity<CategoryResponseDto> createCategory(@RequestBody CategoryCreateDto categoryDto,
                                                              @RequestHeader("Authorization") String token) {

        JwtDTO dto = SecurityUtil.getJwtDTO(token);
        if (!dto.getRole().equals(ProfileRole.ROLE_ADMIN)) {
            throw new AppForbiddenException("Kechirasiz sizda bunday huquq yo'q");
        }
        CategoryResponseDto result = categoryService.createCategory(categoryDto);
        return ResponseEntity.ok().body(result);
    }

    // 2. Update category (ADMIN)
    @PutMapping("/update/{id}")
    public ResponseEntity<Result> updateCategory(@PathVariable int id,
                                                 @RequestBody CategoryCreateDto categoryDto,
                                                 @RequestHeader("Authorization") String token) {

        JwtDTO dto = SecurityUtil.getJwtDTO(token);
        if (!dto.getRole().equals(ProfileRole.ROLE_ADMIN)) {
            throw new AppForbiddenException("Kechirasiz sizda bunday huquq yo'q");
        }
        Result result = categoryService.updateCategory(id, categoryDto);
        return ResponseEntity.status(result.isSuccess()?HttpStatus.OK:HttpStatus.CONFLICT).body(result);
    }

    // 3. Category list (ADMIN)
    @GetMapping("/list")
    public ResponseEntity<List<CategoryResponseDto>> getCategoryList(@RequestHeader("Authorization") String token) {

        JwtDTO dto = SecurityUtil.getJwtDTO(token);
        if (!dto.getRole().equals(ProfileRole.ROLE_ADMIN)) {
            throw new AppForbiddenException("Kechirasiz sizda bunday huquq yo'q");
        }
      List<CategoryResponseDto> categoryDtoList=categoryService.getCategoryList();
      return ResponseEntity.status(HttpStatus.OK).body(categoryDtoList);
    }

    // 4. Delete category (ADMIN)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Result> deleteRegion( @PathVariable int id,
                                                @RequestHeader("Authorization") String token) {

        JwtDTO dto = SecurityUtil.getJwtDTO(token);
        if (!dto.getRole().equals(ProfileRole.ROLE_ADMIN)) {
            throw new AppForbiddenException("Kechirasiz sizda bunday huquq yo'q");
        }
        Result result = categoryService.deleteRegion(id);
        return ResponseEntity.status(result.isSuccess()?HttpStatus.OK:HttpStatus.CONFLICT).body(result);
    }

    // 5. Category By Lang
    @GetMapping("/lang")
    public ResponseEntity<List<CategoryResponseDto>> getCategoryByLang(@RequestHeader(value = "Accept-Language") LanguageEnum lang) {
        List<CategoryResponseDto> categoryLangDtoList=categoryService.getCategoryByLang2(lang);
        return ResponseEntity.status(HttpStatus.OK).body(categoryLangDtoList);
    }


}
