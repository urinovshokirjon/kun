package uz.urinov.kun.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import uz.urinov.kun.mapper.RegionMapper;
import uz.urinov.kun.entity.RegionEntity;

import java.util.List;

public interface RegionRepository extends CrudRepository<RegionEntity, Integer> {

    // 3. Region list
    @Query("from RegionEntity where visible = true order by orderNumber desc")
    List<RegionEntity> findAllVisible();

    // 5. Region By Lang
    @Query(value = "SELECT id, " +
            " CASE :lang " +
            " WHEN 'UZ' THEN name_uz " +
            " WHEN 'RU' THEN name_ru " +
            " WHEN 'EN' THEN name_en " +
            " END AS name " +
            " FROM region ORDER BY order_number DESC; ", nativeQuery = true)
    List<RegionMapper> findAll(@Param("lang") String lang);





}
