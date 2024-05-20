package uz.urinov.kun.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import uz.urinov.kun.dto.ArticleTypeCreateDto;
import uz.urinov.kun.entity.ArticleTypeEntity;
import uz.urinov.kun.entity.CategoryEntity;
import uz.urinov.kun.mapper.ArticleTypeMapper;
import uz.urinov.kun.mapper.CategoryMapper;

import java.util.List;

public interface ArticleTypeRepository extends CrudRepository<ArticleTypeEntity, Integer> {

    // 3. List ArticleType
    Page<ArticleTypeEntity> findAllByVisibleTrueOrderByOrderNumber(Pageable pageable);

    //
    @Query(value = "SELECT id," +
            " CASE :lang " +
            " WHEN 'UZ' THEN name_uz " +
            " WHEN 'RU' THEN name_ru " +
            " WHEN 'EN' THEN name_en " +
            " END AS name " +
            " FROM article_type ORDER BY order_number DESC ", nativeQuery = true)
    Page<ArticleTypeMapper> getArticleTypePage(@Param("lang") String lang, Pageable pageable);

}

