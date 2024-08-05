package uz.urinov.kun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.urinov.kun.dto.ArticleCreateDto;
import uz.urinov.kun.dto.ArticleResponseDto;
import uz.urinov.kun.dto.ArticleShortInfoDto;
import uz.urinov.kun.dto.ProfileResponseDTO;
import uz.urinov.kun.entity.ArticleEntity;
import uz.urinov.kun.entity.ArticleTagEntity;
import uz.urinov.kun.entity.ProfileEntity;
import uz.urinov.kun.enums.ArticleStatus;
import uz.urinov.kun.enums.LanguageEnum;
import uz.urinov.kun.enums.Result;
import uz.urinov.kun.exp.AppBadException;
import uz.urinov.kun.mapper.ArticleShortInfoMapper;
import uz.urinov.kun.repository.*;
import uz.urinov.kun.util.SecurityUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    RegionRepository regionRepository;

    @Autowired
    RegionService regionService;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryService categoryService;

    @Autowired
    TypesRepository typesRepository;

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    private ArticleTypesService articleTypesService;

    @Autowired
    private ArticleTagRepository articleTagRepository;

    @Autowired
    private AttachService attachService;

    @Autowired
    private ArticleLikeRepository articleLikeRepository;

    // 1 . CREATE (Moderator) status(NotPublished)
    public ArticleResponseDto createArticle(ArticleCreateDto createDto) {

        ProfileEntity moderator = SecurityUtil.getProfile();


        ArticleEntity articleEntity = new ArticleEntity();

        for (String tag : createDto.getTagList()) {
            if (!articleTagRepository.existsByTag(tag)) {
                ArticleTagEntity articleTagEntity = new ArticleTagEntity();
                articleTagEntity.setTag(tag);
                articleTagRepository.save(articleTagEntity);
            }
        }

        Set<ArticleTagEntity> allTagEntity = articleTagRepository.findAllByTagIn(createDto.getTagList());

        articleEntity.setArticleTag(allTagEntity);

        articleEntity.setTitle(createDto.getTitle());
        articleEntity.setDescription(createDto.getDescription());
        articleEntity.setContent(createDto.getContent());
        articleEntity.setImageId(createDto.getImageId());
//        articleEntity.setRegion(region);
        articleEntity.setRegionId(createDto.getRegionId());
//        articleEntity.setCategory(category);
        articleEntity.setCategoryId(createDto.getCategoryId());
//        articleEntity.setModerator(profile);
        articleEntity.setModeratorId(moderator.getId());
//        articleEntity.setStatus(ArticleStatus.NOT_PUBLISHED);
        articleRepository.save(articleEntity);

        articleTypesService.create(articleEntity.getId(), createDto.getTypesList());
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
        return new Result(articleEntity.getTitle() + ". (Shu title maqola o'chirildi)", true);
    }

    // 4. Change status by id (PUBLISHER) (publish,not_publish)
    public Result publisherArticle(String articleId) {

        ProfileEntity profileEntity = SecurityUtil.getProfile();

        ArticleEntity articleEntity = get(articleId);

        articleEntity.setStatus(ArticleStatus.PUBLISHED);
        articleEntity.setPublishedDate(LocalDateTime.now());
        articleEntity.setPublisherId(profileEntity.getId());
        articleRepository.save(articleEntity);
        return new Result("Maqolaga ruxsad berildi", true);
    }

    // 5. Get Last 5 Article By Types  ordered_by_created_date
    public List<ArticleResponseDto> getLast5ByTypes(Integer typesId) {
        // id(uuid),title,description,image(id,url),published_date
        List<ArticleShortInfoMapper> mapperList = articleRepository.getByTypesId(typesId, 5);
        List<ArticleResponseDto> dtoList = new LinkedList<>();
        for (ArticleShortInfoMapper mapper : mapperList) {
            dtoList.add(toDTO(mapper));
        }
        return dtoList;
    }

    // 6. Get Last 3 Article By Types  ordered_by_created_date
    public List<ArticleResponseDto> getLast3ArticleByTypes(Integer typesId) {
        // id(uuid),title,description,image(id,url),published_date
        List<ArticleShortInfoMapper> mapperList = articleRepository.getByTypesId(typesId, 3);
        return mapperList.stream()
                .map(mapper -> toDTO(mapper))
                .collect(Collectors.toList());
    }

    // 7. Get Last 8  Articles witch id not included in given list.([1,2,3])
    public List<ArticleResponseDto> getLast8ArticleByTypes(List<String> ids) {

        List<ArticleResponseDto> articleResponseDtoList = new ArrayList<>();
        for (ArticleShortInfoMapper mapper : articleRepository.getLast8(ids)) {
            articleResponseDtoList.add(toDTO(mapper));
        }
        return articleResponseDtoList;
    }

    // 8. Get Article By Id And Lang ArticleFullInfo
    public ArticleResponseDto getArticleByIdAndLang(String id, LanguageEnum lang) {

        ArticleEntity articleEntity = get(id);
        if (!articleEntity.getStatus().equals(ArticleStatus.PUBLISHED)) {
            throw new AppBadException("Article not published");
        }
        ArticleResponseDto dto = toFullDto(articleEntity);
        dto.setId(articleEntity.getId());
        dto.setTitle(articleEntity.getTitle());
        dto.setDescription(articleEntity.getDescription());
        dto.setContent(articleEntity.getContent());
        dto.setSharedCount(articleEntity.getSharedCount());
        dto.setRegion(regionService.getRegion(articleEntity.getRegionId(), lang));
        dto.setCategory(categoryService.getCategory(articleEntity.getCategoryId(), lang));
        dto.setPublishedDate(articleEntity.getPublishedDate());
        dto.setViewCount(articleEntity.getViewCount());
//        dto.setLikeCount(articleLikeRepository.getArticleLikeCount(id));  // TODO
        dto.setLikeCount(articleEntity.getLikeCount());
        dto.setDislikeCount(articleEntity.getDislikeCount());


        return dto;
    }

    //  9. Get Last 4 Article By Types and except given article id. ArticleShortInfo
    public List<ArticleShortInfoDto> getLast4ArticleByTypesExceptId(Integer typesId, String articleId) {
        List<ArticleShortInfoDto> articleShortInfoDtoList = new ArrayList<>();

        for (ArticleEntity articleEntity : articleRepository.getLast4ArticleByTypesExceptId(typesId, articleId)) {
            articleShortInfoDtoList.add(toShortDto(articleEntity));
        }
        return articleShortInfoDtoList;
    }

    // 10. Get 4 most read articles ArticleShortInfo
    public List<ArticleShortInfoDto> get4MostReadArticles(Integer typesId) {
        List<ArticleShortInfoDto> articleShortInfoDtoList = new ArrayList<>();
        for (ArticleEntity articleEntity : articleRepository.get4MostReadArticles(typesId)) {
            articleShortInfoDtoList.add(toShortDto(articleEntity));
        }
        return articleShortInfoDtoList;

    }

    // 12. Get Last 5 Article By Types  And By Region Key ArticleShortInfo
    public List<ArticleResponseDto> getLast5ArticleTypesAndRegion(Integer typesId, Integer regionId) {

        List<ArticleResponseDto> articleResponseDtoList = new ArrayList<>();
        for (ArticleShortInfoMapper mapper : articleRepository.getLast5ArticleTypesAndRegion(typesId, regionId)) {
            articleResponseDtoList.add(toDTO(mapper));
        }
        return articleResponseDtoList;
    }

    //   13. Get Article list by Region Key (Pagination) ArticleShortInfo
    public PageImpl<ArticleResponseDto> getArticleListRegionPage(int page, int size, int regionId) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ArticleShortInfoMapper> articleShortInfoMapperPage = articleRepository.getArticleListRegionPage(regionId, pageable);

        List<ArticleResponseDto> articleResponseDtoList = articleShortInfoMapperPage.stream().map(this::toDTO).toList();
        return new PageImpl<>(articleResponseDtoList, pageable, articleShortInfoMapperPage.getTotalElements());

    }

    // 14. Get Last 5 Article Category Key ArticleShortInfo
    public List<ArticleResponseDto> getLast5ArticleCategory(Integer categoryId) {
        List<ArticleResponseDto> articleResponseDtoList = new ArrayList<>();
        for (ArticleShortInfoMapper mapper : articleRepository.getLast5ArticleCategory(categoryId)) {
            articleResponseDtoList.add(toDTO(mapper));
        }
        return articleResponseDtoList;
    }

    //  15. Get Article By Category Key (Pagination) ArticleShortInfo
    public PageImpl<ArticleResponseDto> getArticleListCategoryPage(int page, int size, int categoryId) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ArticleShortInfoMapper> articleShortInfoMapperPage = articleRepository.getArticleListCategoryPage(categoryId, pageable);

        List<ArticleResponseDto> articleResponseDtoList = articleShortInfoMapperPage.stream().map(this::toDTO).toList();
        return new PageImpl<>(articleResponseDtoList, pageable, articleShortInfoMapperPage.getTotalElements());
    }


    public ArticleResponseDto toFullDto(ArticleEntity entity) {
        ArticleResponseDto dto = new ArticleResponseDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setContent(entity.getContent());
        dto.setImageId(entity.getImageId());
        dto.setViewCount(entity.getViewCount());
        dto.setCreateDate(entity.getCreateDate());
        return dto;
    }

    public ArticleShortInfoDto toShortDto(ArticleEntity entity) {
        ArticleShortInfoDto dto = new ArticleShortInfoDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setImageId(entity.getImageId());
        return dto;
    }

    public ArticleResponseDto toDTO(ArticleShortInfoMapper mapper) {
        ArticleResponseDto dto = new ArticleResponseDto();
        dto.setId(mapper.getId());
        dto.setTitle(mapper.getTitle());
        dto.setDescription(mapper.getDescription());
        dto.setPublishedDate(mapper.getPublishedDate());
        dto.setImage(attachService.getDTOWithURL(mapper.getImageId()));
        return dto;
    }

    public ArticleEntity get(String id) {
        return articleRepository.findByIdAndVisibleTrue(id).orElseThrow(() -> {
            throw new AppBadException("Article not found");
        });
    }


}
