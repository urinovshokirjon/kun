package uz.urinov.kun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.urinov.kun.dto.CategoryCreateDto;
import uz.urinov.kun.dto.CategoryResponseDto;
import uz.urinov.kun.entity.CategoryEntity;
import uz.urinov.kun.enums.LanguageEnum;
import uz.urinov.kun.enums.Result;
import uz.urinov.kun.exp.AppBadException;
import uz.urinov.kun.mapper.CategoryMapper;
import uz.urinov.kun.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    // 1. Category create
    public CategoryResponseDto createCategory(CategoryCreateDto categoryDto) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setOrderNumber(categoryDto.getOrderNumber());
        categoryEntity.setNameUz(categoryDto.getNameUz());
        categoryEntity.setNameRu(categoryDto.getNameRu());
        categoryEntity.setNameEn(categoryDto.getNameEn());
        categoryRepository.save(categoryEntity);
        return toDTO(categoryEntity);

    }

    // 2. Update category
    public Result updateCategory(int id, CategoryCreateDto categoryDto) {
        CategoryEntity categoryEntity = getCategoryEntityById(id);
        categoryEntity.setOrderNumber(categoryDto.getOrderNumber());
        categoryEntity.setNameUz(categoryDto.getNameUz());
        categoryEntity.setNameRu(categoryDto.getNameRu());
        categoryEntity.setNameEn(categoryDto.getNameEn());
        categoryRepository.save(categoryEntity);
        return new Result("CategoryEntity uodate",true);
    }

    // 3. Category list
    public List<CategoryResponseDto> getCategoryList() {

        List<CategoryResponseDto> categoryDtoList = new ArrayList<>();

        for (CategoryEntity categoryEntity : categoryRepository.findAllByVisibleTrueOrderByOrderNumber()) {
            categoryDtoList.add(toDTO(categoryEntity));
        }
        return categoryDtoList;
    }

    //4. Category delete
    public Result deleteRegion(int id) {
        CategoryEntity categoryEntity = getCategoryEntityById(id);
        categoryRepository.delete(categoryEntity);
        return new Result("CategoryEntity delete",true);
    }
    // 5. Category By Lang
    public List<CategoryResponseDto> getCategoryByLang(LanguageEnum lang) {

        List<CategoryResponseDto> categoryLangDtoList = new ArrayList<>();

        for (CategoryEntity categoryEntity : categoryRepository.findAllByVisibleTrueOrderByOrderNumber()) {
            CategoryResponseDto categoryLangDto=new CategoryResponseDto();
            categoryLangDto.setId(categoryEntity.getId());

            switch (lang){
                case UZ-> categoryLangDto.setName(categoryEntity.getNameUz());
                case RU-> categoryLangDto.setNameRu(categoryEntity.getNameRu());
                case EN -> categoryLangDto.setNameEn(categoryEntity.getNameEn());
            }

            categoryLangDtoList.add(categoryLangDto);
        }
    return categoryLangDtoList;
    }

    // 5. Category By Lang
    public List<CategoryResponseDto> getCategoryByLang2(LanguageEnum lang) {

        List<CategoryResponseDto> categoryLangDtoList = new ArrayList<>();
        for (CategoryMapper categoryMapper : categoryRepository.findAllLang(lang.name())) {
            CategoryResponseDto categoryLangDto=new CategoryResponseDto();
            categoryLangDto.setId(categoryMapper.getId());
            categoryLangDto.setName(categoryMapper.getName());
            categoryLangDtoList.add(categoryLangDto);
        }
        return categoryLangDtoList;
    }

    public CategoryResponseDto toDTO(CategoryEntity entity){
        CategoryResponseDto dto = new CategoryResponseDto();
        dto.setId(entity.getId());
        dto.setNameUz(entity.getNameUz());
        dto.setNameEn(entity.getNameEn());
        dto.setNameRu(entity.getNameRu());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setCreateDate(entity.getCreateDate());
        return dto;
    }


    public CategoryEntity getCategoryEntityById(int id) {
       return categoryRepository.findById(id).orElseThrow(()->{
           throw new AppBadException("Category not found");
       });
    }



}
