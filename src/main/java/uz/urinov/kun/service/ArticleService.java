package uz.urinov.kun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.urinov.kun.dto.ArticleCreateDto;
import uz.urinov.kun.dto.ArticleResponseDto;
import uz.urinov.kun.dto.ArticleShortInfoDto;
import uz.urinov.kun.entity.ArticleEntity;
import uz.urinov.kun.entity.ProfileEntity;
import uz.urinov.kun.enums.ArticleStatus;
import uz.urinov.kun.enums.Result;
import uz.urinov.kun.exp.AppBadException;
import uz.urinov.kun.repository.*;
import uz.urinov.kun.util.SecurityUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {
    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    RegionRepository regionRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    TypesRepository typesRepository;

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    private ArticleTypesService articleTypesService;

    // 1 . CREATE (Moderator) status(NotPublished)
    public ArticleResponseDto createArticle(ArticleCreateDto createDto) {

        ProfileEntity moderator = SecurityUtil.getProfile();

        ArticleEntity articleEntity = new ArticleEntity();

        articleEntity.setTitle(createDto.getTitle());
        articleEntity.setDescription(createDto.getDescription());
        articleEntity.setContent(createDto.getContent());
        articleEntity.setImageId(createDto.getImageId());
//        articleEntity.setRegion(region);
        articleEntity.setRegionId(createDto.getRegionId());
//        articleEntity.setCategory(category);
        articleEntity.setCategoryId(createDto.getCategoryId());
//        articleEntity.setModerator(profile);
        articleEntity.setModeratorId(moderator .getId());
//        articleEntity.setStatus(ArticleStatus.NOT_PUBLISHED);
        articleRepository.save(articleEntity);

        articleTypesService.create(articleEntity.getId(),createDto.getTypesList());
        return toFullDto(articleEntity);
    }

    //   // 2. Update (Moderator (status to not publish)) (remove old image) (title,description,content,shared_count,image_id, region_id,category_id)
    public ArticleResponseDto updateArticle(String articleId, ArticleCreateDto dto) {
        ArticleEntity entity = get(articleId);
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        entity.setImageId(dto.getImageId());
        entity.setRegionId(dto.getRegionId());
        entity.setCategoryId(dto.getCategoryId());
        entity.setStatus(ArticleStatus.NOT_PUBLISHED);
        articleRepository.save(entity);

        articleTypesService.merge(articleId, dto.getTypesList());
        return toFullDto(entity);
    }
     // 3. Delete Article (MODERATOR)
    public Result deleteArticle(String articleId) {
        ArticleEntity articleEntity = get(articleId);
        articleEntity.setVisible(false);
        articleRepository.save(articleEntity);
        return new Result(articleEntity.getTitle()+". (Shu title maqola o'chirildi)",true);
    }

    // 4. Change status by id (PUBLISHER) (publish,not_publish)
    public Result publisherArticle(String articleId) {

        ProfileEntity profileEntity = SecurityUtil.getProfile();

        ArticleEntity articleEntity = get(articleId);

        articleEntity.setStatus(ArticleStatus.PUBLISHED);
        articleEntity.setPublishedDate(LocalDateTime.now());
        articleEntity.setPublisherId(profileEntity.getId());
        articleRepository.save(articleEntity);
        return new Result("Maqolaga ruxsad berildi",true);
    }

    // 5. Get Last 5 Article By Types  ordered_by_created_date
    public List<ArticleShortInfoDto> getLast5ArticleByTypes(Integer type) {

        List<ArticleShortInfoDto> articleShortInfoDtoList=new ArrayList<>();

        for (ArticleEntity articleEntity : articleRepository.getLast5ArticleByTypes(type)) {
            ArticleShortInfoDto articleShortInfoDto = toShortDto(articleEntity);
            articleShortInfoDtoList.add(articleShortInfoDto);
        }
        return articleShortInfoDtoList;
    }

    // 6. Get Last 3 Article By Types  ordered_by_created_date
    public List<ArticleShortInfoDto> getLast3ArticleByTypes(Integer type) {

        List<ArticleShortInfoDto> articleShortInfoDtoList=new ArrayList<>();

        for (ArticleEntity articleEntity : articleRepository.getLast3ArticleByTypes(type)) {
            ArticleShortInfoDto articleShortInfoDto = toShortDto(articleEntity);
            articleShortInfoDtoList.add(articleShortInfoDto);
        }
        return articleShortInfoDtoList;
    }

    // 7. Get Last 8  Articles witch id not included in given list.([1,2,3])
    public List<ArticleShortInfoDto> getLast8ArticleByTypes(List<String> ids) {

        List<ArticleShortInfoDto> articleShortInfoDtoList=new ArrayList<>();

        for (ArticleEntity articleEntity : articleRepository.findAllByIdNotIn(ids)) {
            ArticleShortInfoDto articleShortInfoDto = toShortDto(articleEntity);
            articleShortInfoDtoList.add(articleShortInfoDto);
        }
        return articleShortInfoDtoList;
    }
    // 8. Get Article By Id And Lang ArticleFullInfo
    public ArticleShortInfoDto getArticleByIdAndLang(String id) {

        ArticleEntity articleEntity = get(id);
        articleEntity.setViewCount(articleEntity.getViewCount() + 1);
        articleRepository.save(articleEntity);
        return toShortDto(articleEntity);
    }

    //  9. Get Last 4 Article By Types and except given article id. ArticleShortInfo
    public List<ArticleShortInfoDto> getLast4ArticleByTypesExceptId(Integer typesId, String articleId) {
        List<ArticleShortInfoDto> articleShortInfoDtoList=new ArrayList<>();

        for (ArticleEntity articleEntity : articleRepository.getLast4ArticleByTypesExceptId(typesId, articleId)) {
            articleShortInfoDtoList.add(toShortDto(articleEntity));
        }
        return articleShortInfoDtoList;
    }

    // 10. Get 4 most read articles ArticleShortInfo
    public List<ArticleShortInfoDto> get4MostReadArticles(Integer typesId) {
        List<ArticleShortInfoDto> articleShortInfoDtoList=new ArrayList<>();
        for (ArticleEntity articleEntity : articleRepository.get4MostReadArticles(typesId)) {
            articleShortInfoDtoList.add(toShortDto(articleEntity));
        }
        return articleShortInfoDtoList;

    }

    public ArticleResponseDto toFullDto(ArticleEntity entity){
        ArticleResponseDto dto=new ArticleResponseDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setContent(entity.getContent());
        dto.setImageId(entity.getImageId());
        dto.setViewCount(entity.getViewCount());
        dto.setCreateDate(entity.getCreateDate());
        return dto;
    }

    public ArticleShortInfoDto toShortDto(ArticleEntity entity){
        ArticleShortInfoDto dto=new ArticleShortInfoDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setImageId(entity.getImageId());
        return dto;
    }

    public ArticleEntity get(String id) {
        return articleRepository.findByIdAndVisibleTrue(id).orElseThrow(() -> {
            throw new AppBadException("Article not found");
        });
    }



}
