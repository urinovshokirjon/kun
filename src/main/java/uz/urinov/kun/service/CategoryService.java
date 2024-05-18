package uz.urinov.kun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.urinov.kun.dto.CategoryDto;
import uz.urinov.kun.dto.Result;
import uz.urinov.kun.entity.CategoryEntity;
import uz.urinov.kun.entity.RegionEntity;
import uz.urinov.kun.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    // 1. Category create
    public Result createCategory(CategoryDto categoryDto) {
        CategoryEntity categoryEntity = getCategoryEntity(categoryDto);
        categoryRepository.save(categoryEntity);
        return new Result("Category saqlandi",true);
    }

    // 2. Update category
    public Result updateCategory(int id, CategoryDto categoryDto) {
        Optional<CategoryEntity> categoryEntityOptional = categoryRepository.findById(id);
        if (categoryEntityOptional.isEmpty()) {
            return new Result("Category not found",false);
        }
        CategoryEntity categoryEntity = categoryEntityOptional.get();
        categoryEntity.setNameUz(categoryDto.getNameUz());
        categoryEntity.setNameRu(categoryDto.getNameRu());
        categoryEntity.setNameEn(categoryDto.getNameEn());
        categoryEntity.setVisible(categoryDto.getVisible());
        categoryEntity.setOrderNumber(categoryDto.getOrderNumber());
        categoryRepository.save(categoryEntity);
        return new Result("Category tahrirlandi",true);
    }

    // 3. Category list
    public List<CategoryDto> getCategoryList() {
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        for (CategoryEntity categoryEntity : categoryRepository.findAllByVisibleTrue()) {

            categoryDtoList.add(getCategoryDto(categoryEntity));
        }

        return categoryDtoList;
    }

    //4. Category delete
    public Result deleteRegion(int id) {
        Optional<CategoryEntity> categoryEntityOptional = categoryRepository.findById(id);
        if (categoryEntityOptional.isEmpty()) {
            return new Result("Category not found",false);
        }
        CategoryEntity categoryEntity = categoryEntityOptional.get();
        categoryEntity.setVisible(false);
        categoryRepository.save(categoryEntity);
        return new Result("Category  o'chirildi",true);
    }





    public CategoryEntity getCategoryEntity(CategoryDto categoryDto) {
        CategoryEntity categoryEntity=new CategoryEntity();
        categoryEntity.setOrderNumber(categoryDto.getOrderNumber());
        categoryEntity.setNameUz(categoryDto.getNameUz());
        categoryEntity.setNameRu(categoryDto.getNameRu());
        categoryEntity.setNameEn(categoryDto.getNameEn());
        return categoryEntity;
    }

    public CategoryDto getCategoryDto(CategoryEntity categoryEntity) {
        CategoryDto categoryDto=new CategoryDto();
        categoryDto.setId(categoryEntity.getId());
        categoryDto.setOrderNumber(categoryEntity.getOrderNumber());
        categoryDto.setNameUz(categoryEntity.getNameUz());
        categoryDto.setNameRu(categoryEntity.getNameRu());
        categoryDto.setNameEn(categoryEntity.getNameEn());
        categoryDto.setVisible(categoryEntity.getVisible());
        categoryDto.setCreateDate(categoryEntity.getCreateDate());
        return categoryDto;
    }



}
