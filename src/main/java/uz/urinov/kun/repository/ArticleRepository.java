package uz.urinov.kun.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.urinov.kun.entity.ArticleEntity;
import uz.urinov.kun.mapper.ArticleShortInfoMapper;

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

    // 5. Get Last 5 Article By Types  ordered_by_created_date
    @Query(value = "select * from article as a\n" +
            "inner join article_types as ats  on ats.article_id = a.id\n" +
            "where types_id = ?1 and visible = true and status = 'PUBLISHED'\n" +
            "order by created_date desc\n" +
            "limit 5", nativeQuery = true)
    List<ArticleEntity> getLast5ByTypesIdNative(Integer typesId);

    // 5. 6. Get Last 5 Article By Types  ordered_by_created_date
    @Query(value = " SELECT a.id,a.title,a.description,a.imageId,a.publishedDate " +
            " from ArticleEntity as a " +
            " inner join a.articleTypes as ats " +
            " where ats.typesId= ?1 and a.visible = true and a.status = 'PUBLISHED' " +
            " order by a.createDate desc " +
            " limit ?2 ")
    List<ArticleShortInfoMapper> getByTypesId(Integer typesId,int limit);

    // 7. Get Last 8  Articles witch id not included in given list.([1,2,3,]) ArticleShortInfo
    @Query(value = " SELECT a.id,a.title,a.description,a.imageId,a.publishedDate " +
            " from ArticleEntity as a " +
            " where a.visible = true and a.status = 'PUBLISHED' " +
            " and a.id not in ?1" +
            " order by a.createDate desc " +
            " limit 8 ")
    List<ArticleShortInfoMapper> getLast8(List<String> ids);

    // 12. Get Last 5 Article By Types  And By Region Key ArticleShortInfo
    @Query(value = " SELECT a.id,a.title,a.description,a.imageId,a.publishedDate " +
            " from ArticleEntity as a " +
            " inner join a.articleTypes as ats " +
            " where ats.typesId= ?1 and a.regionId=?2 and a.visible = true and a.status = 'PUBLISHED' " +
            " order by a.createDate desc " +
            " limit 5 ")
    List<ArticleShortInfoMapper> getLast5ArticleTypesAndRegion(Integer typesId,Integer regionId);

    //   13. Get Article list by Region Key (Pagination) ArticleShortInfo
    @Query(value = " SELECT a.id,a.title,a.description,a.imageId,a.publishedDate " +
            " from ArticleEntity as a " +
            " inner join a.articleTypes as ats " +
            " where  a.regionId=?1 and a.visible = true and a.status = 'PUBLISHED' " +
            " order by a.createDate desc ")
    Page<ArticleShortInfoMapper> getArticleListRegionPage(Integer regionId, Pageable pageable);

    // 14. Get Last 5 Article Category Key ArticleShortInfo
    @Query(value = " SELECT a.id,a.title,a.description,a.imageId,a.publishedDate " +
            " from ArticleEntity as a " +
            " inner join a.articleTypes as ats " +
            " where  a.categoryId=?1 and a.visible = true and a.status = 'PUBLISHED' " +
            " order by a.createDate desc " +
            " limit 5 ")
    List<ArticleShortInfoMapper> getLast5ArticleCategory(Integer categoryId);

    //  15. Get Article By Category Key (Pagination) ArticleShortInfo
    @Query(value = " SELECT a.id,a.title,a.description,a.imageId,a.publishedDate " +
            " from ArticleEntity as a " +
            " inner join a.articleTypes as ats " +
            " where  a.categoryId=?1 and a.visible = true and a.status = 'PUBLISHED' " +
            " order by a.createDate desc ")
    Page<ArticleShortInfoMapper> getArticleListCategoryPage(Integer categoryId, Pageable pageable);



    Optional<ArticleEntity> findByIdAndVisibleTrue(String id);


}
