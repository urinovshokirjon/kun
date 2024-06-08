package uz.urinov.kun.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.urinov.kun.entity.ArticleEntity;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<ArticleEntity, String> {

    List<ArticleEntity> findTop5ByIdAndVisibleTrueOrderByCreateDateDesc(String id);

    // 5. Get Last 5 Article By Types  ordered_by_created_date
    @Query("SELECT at.article FROM  ArticleTypesEntity at inner join at.article where at.typesId=:id and at.article.status='PUBLISHED' and at.article.visible ORDER BY at.article.createDate desc limit 5")
    List<ArticleEntity> getLast5ArticleByTypes(@Param("id") Integer id);

    @Query("SELECT at.article FROM  ArticleTypesEntity at inner join at.article where at.typesId=:id and at.article.status='PUBLISHED' and (at.article.visible) ORDER BY at.article.createDate desc limit 1")
    List<ArticleEntity> getLast3ArticleByTypes(@Param("id") Integer id);

    //  9. Get Last 4 Article By Types and except given article id. ArticleShortInfo
    @Query("SELECT at.article FROM  ArticleTypesEntity at inner join at.article where at.typesId=:typesId and at.articleId<>:articleId and at.article.status='PUBLISHED' and at.article.visible ORDER BY at.article.createDate desc limit 4")
    List<ArticleEntity> getLast4ArticleByTypesExceptId(@Param("typesId") Integer typesId, @Param("articleId") String articleId);

    // 10. Get 4 most read articles ArticleShortInfo
    @Query("SELECT at.article FROM  ArticleTypesEntity at inner join at.article where at.typesId=:id and at.article.status='PUBLISHED' and at.article.visible ORDER BY at.article.viewCount desc limit 4")
    List<ArticleEntity> get4MostReadArticles(@Param("id") Integer id);


//    List<ArticleEntity>getLast8ArticleByTypes(List<String> ids);

    List<ArticleEntity>findAllByIdNotIn(List<String> ids);

    Optional<ArticleEntity> findByIdAndVisibleTrue(String id);


}
