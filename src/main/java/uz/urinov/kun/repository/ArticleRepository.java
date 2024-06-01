package uz.urinov.kun.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.urinov.kun.entity.ArticleEntity;

import java.util.List;
import java.util.UUID;

public interface ArticleRepository extends JpaRepository<ArticleEntity, UUID> {

    List<ArticleEntity> findTop5ByIdAndVisibleTrueOrderByCreateDateDesc(UUID id);

}
