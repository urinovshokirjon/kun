package uz.urinov.kun.repository;

import org.springframework.data.repository.CrudRepository;
import uz.urinov.kun.entity.ArticleTypeEntity;
import uz.urinov.kun.entity.CategoryEntity;

import java.util.List;

public interface ArticleTypeRepository extends CrudRepository<ArticleTypeEntity, Integer> {

    // 3. List ArticleType
    List<ArticleTypeEntity> findAllByVisibleTrue();



}
