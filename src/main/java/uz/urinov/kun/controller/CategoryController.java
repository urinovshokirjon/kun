package uz.urinov.kun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.urinov.kun.dto.CategoryCreateDto;
import uz.urinov.kun.dto.CategoryResponseDto;
import uz.urinov.kun.enums.LanguageEnum;
import uz.urinov.kun.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    // 1. Create category
    @PostMapping("/create")
    public ResponseEntity<CategoryResponseDto> createCategory(@RequestBody CategoryCreateDto categoryDto) {
        CategoryResponseDto result = categoryService.createCategory(categoryDto);
        return ResponseEntity.ok().body(result);
    }

    // 2. Update category
    @PutMapping("/update/{id}")
    public ResponseEntity<Boolean> updateCategory(@PathVariable int id, @RequestBody CategoryCreateDto categoryDto) {
        Boolean result = categoryService.updateCategory(id, categoryDto);
        return ResponseEntity.ok().body(result);
    }

    // 3. Category list
    @GetMapping("/list")
    public ResponseEntity<List<CategoryResponseDto>> getCategoryList() {
      List<CategoryResponseDto> categoryDtoList=categoryService.getCategoryList();
      return ResponseEntity.status(HttpStatus.OK).body(categoryDtoList);
    }

    // 4. Delete category
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteRegion( @PathVariable int id) {
        Boolean result = categoryService.deleteRegion(id);
        return ResponseEntity.ok().body(result);
    }

    // 5. Category By Lang
    @GetMapping("/lang")
    public ResponseEntity<List<CategoryResponseDto>> getCategoryByLang(@RequestHeader(value = "Accept-Language") LanguageEnum lang) {
        List<CategoryResponseDto> categoryLangDtoList=categoryService.getCategoryByLang2(lang);
        return ResponseEntity.status(HttpStatus.OK).body(categoryLangDtoList);
    }


}
