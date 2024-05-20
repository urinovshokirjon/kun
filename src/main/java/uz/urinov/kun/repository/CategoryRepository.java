package uz.urinov.kun.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import uz.urinov.kun.entity.CategoryEntity;
import uz.urinov.kun.mapper.CategoryMapper;

import java.util.List;

public interface CategoryRepository extends CrudRepository<CategoryEntity, Integer> {

    // 3. Region list
    @Query("FROM CategoryEntity WHERE visible=true ORDER BY orderNumber DESC ")
    List<CategoryEntity> findAllByVisibleTrueOrderByOrderNumber();

    // 5.Category lang
    @Query(value ="SELECT id, " +
            " CASE :lang " +
            " WHEN 'UZ' THEN name_uz " +
            " WHEN 'RU' THEN name_ru " +
            " WHEN 'EN' THEN name_en " +
            " END AS name " +
            " FROM category ORDER BY order_number DESC " ,nativeQuery = true)
    List<CategoryMapper> findAllLang(@Param("lang") String lang);



}
