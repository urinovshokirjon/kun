package uz.urinov.kun.repository;

import org.springframework.data.repository.CrudRepository;
import uz.urinov.kun.entity.CategoryEntity;
import uz.urinov.kun.entity.RegionEntity;

import java.util.List;

public interface CategoryRepository extends CrudRepository<CategoryEntity, Integer> {

    // 3. Region list
    List<CategoryEntity> findAllByVisibleTrue();



}
