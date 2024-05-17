package uz.urinov.kun.repository;

import org.springframework.data.repository.CrudRepository;
import uz.urinov.kun.entity.RegionEntity;

import java.util.List;

public interface RegionRepository extends CrudRepository<RegionEntity, Integer> {

    // 3. Region list
    List<RegionEntity> findAllByVisibleTrue();



}
