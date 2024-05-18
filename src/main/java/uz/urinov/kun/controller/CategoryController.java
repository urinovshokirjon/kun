package uz.urinov.kun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.urinov.kun.dto.CategoryDto;
import uz.urinov.kun.dto.CategoryLangDto;
import uz.urinov.kun.dto.RegionLangDto;
import uz.urinov.kun.dto.Result;
import uz.urinov.kun.entity.CategoryEntity;
import uz.urinov.kun.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    // 1. Create category
    @PostMapping("/create")
    public ResponseEntity<Result> createCategory(@RequestBody CategoryDto categoryDto) {
        Result result = categoryService.createCategory(categoryDto);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(result);
    }

    // 2. Update category
    @PutMapping("/update/{id}")
    public ResponseEntity<Result> updateCategory(@PathVariable int id, @RequestBody CategoryDto categoryDto) {
        Result result = categoryService.updateCategory(id, categoryDto);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(result);
    }

    // 3. Category list
    @GetMapping("/list")
    public ResponseEntity<List<CategoryDto>> getCategoryList() {
      List<CategoryDto> categoryDtoList=categoryService.getCategoryList();
      return ResponseEntity.status(HttpStatus.OK).body(categoryDtoList);
    }

    // 4. Delete category
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Result> deleteRegion( @PathVariable int id) {
        Result result = categoryService.deleteRegion(id);
        return ResponseEntity.status(result.isSuccess()? HttpStatus.OK: HttpStatus.CONFLICT).body(result);
    }

    // 5. Category By Lang
    @GetMapping("/lang")
    public ResponseEntity<List<CategoryLangDto>> getCategoryByLang(@RequestParam String lang) {
        List<CategoryLangDto> categoryLangDtoList=categoryService.getCategoryByLang(lang);
        return ResponseEntity.status(HttpStatus.OK).body(categoryLangDtoList);
    }


}
