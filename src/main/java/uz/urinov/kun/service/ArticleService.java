package uz.urinov.kun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import uz.urinov.kun.dto.ArticleCreateDto;
import uz.urinov.kun.dto.ArticleResponseDto;
import uz.urinov.kun.dto.RegionResponseDTO;
import uz.urinov.kun.entity.*;
import uz.urinov.kun.enums.ArticleStatus;
import uz.urinov.kun.enums.LanguageEnum;
import uz.urinov.kun.enums.Result;
import uz.urinov.kun.repository.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ArticleService {
    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    RegionRepository regionRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ArticleTypeRepository articleTypeRepository;

    @Autowired
    ProfileRepository profileRepository;

    // 1 . CREATE (Moderator) status(NotPublished)
    public Result createArticle(ArticleCreateDto createDto, Integer profileId) {

        Optional<RegionEntity> regionEntityOptional = regionRepository.findById(createDto.getRegionId());
        if (regionEntityOptional.isEmpty()) {
           return new Result("Bunday ragion mavjud emas",false);
        }
        //
        Optional<CategoryEntity> categoryEntityOptional = categoryRepository.findById(createDto.getCategoryId());
        if (categoryEntityOptional.isEmpty()) {
            return new Result("Bunday category mavjud emas",false);
        }
        //
        Optional<ProfileEntity> profileEntityOptional = profileRepository.findById(profileId);
        if (profileEntityOptional.isEmpty()) {
            return new Result("Bunday hodim mavjud emas",false);
        }

        List<ArticleTypeEntity> allById = articleTypeRepository.findAllByIdIn(createDto.getArticleTypeIds());


        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setId(UUID.randomUUID());
        articleEntity.setTitle(createDto.getTitle());
        articleEntity.setDescription(createDto.getDescription());
        articleEntity.setContent(createDto.getContent());
        articleEntity.setImageId(createDto.getImageId());

        articleEntity.setRegion(regionEntityOptional.get());
        articleEntity.setCategory(categoryEntityOptional.get());
        articleEntity.setModerator(profileEntityOptional.get());
        articleEntity.setStatus(ArticleStatus.NOT_PUBLISHED);
        articleEntity.setArticleType(allById);


        articleRepository.save(articleEntity);


    return new Result("Maqola saqlandi",true);
    }

    //   // 2. Update (Moderator (status to not publish)) (remove old image) (title,description,content,shared_count,image_id, region_id,category_id)
    public Result updateArticle(ArticleCreateDto createDto, Integer profileId, UUID articleId) {
        Optional<ArticleEntity> articleEntityOptional = articleRepository.findById(articleId);
        if (articleEntityOptional.isEmpty()) {
            return new Result("Bunday article mavjud emas",false);
        }

        Optional<RegionEntity> regionEntityOptional = regionRepository.findById(createDto.getRegionId());
        if (regionEntityOptional.isEmpty()) {
            return new Result("Bunday ragion mavjud emas",false);
        }

        //
        Optional<CategoryEntity> categoryEntityOptional = categoryRepository.findById(createDto.getCategoryId());
        if (categoryEntityOptional.isEmpty()) {
            return new Result("Bunday category mavjud emas",false);
        }
        //
        Optional<ProfileEntity> profileEntityOptional = profileRepository.findById(profileId);
        if (profileEntityOptional.isEmpty()) {
            return new Result("Bunday hodim mavjud emas",false);
        }

        List<ArticleTypeEntity> allById = articleTypeRepository.findAllByIdIn(createDto.getArticleTypeIds());

        ArticleEntity articleEntity = articleEntityOptional.get();

        articleEntity.setTitle(createDto.getTitle());
        articleEntity.setDescription(createDto.getDescription());
        articleEntity.setContent(createDto.getContent());
        articleEntity.setImageId(createDto.getImageId());

        articleEntity.setRegion(regionEntityOptional.get());
        articleEntity.setCategory(categoryEntityOptional.get());
        articleEntity.setModerator(profileEntityOptional.get());
        articleEntity.setArticleType(allById);
        return new Result("Maqola o'zgartirildi",true);
    }

    // 3. Delete Article (MODERATOR)
    public Result deleteArticle(Integer id, UUID articleId) {
        Optional<ArticleEntity> articleEntityOptional = articleRepository.findById(articleId);
        if (articleEntityOptional.isEmpty()) {
            return new Result("Bunday article mavjud emas",false);
        }
        Optional<ProfileEntity> profileEntityOptional = profileRepository.findById(id);
        if (profileEntityOptional.isEmpty()) {
            return new Result("Bunday hodim mavjud emas",false);
        }
        ArticleEntity articleEntity = articleEntityOptional.get();
//        articleRepository.delete(articleEntity);
        articleEntity.setVisible(false);
        articleRepository.save(articleEntity);
        return new Result("Maqola o'chirildi",true);
    }

    // 4. Change status by id (PUBLISHER) (publish,not_publish)
    public Result publisherArticle(Integer id, UUID articleId) {

        Optional<ArticleEntity> articleEntityOptional = articleRepository.findById(articleId);
        if (articleEntityOptional.isEmpty()) {
            return new Result("Bunday article mavjud emas",false);
        }
        Optional<ProfileEntity> profileEntityOptional = profileRepository.findById(id);
        if (profileEntityOptional.isEmpty()) {
            return new Result("Bunday hodim mavjud emas",false);
        }
        ArticleEntity articleEntity = articleEntityOptional.get();
        articleEntity.setStatus(ArticleStatus.PUBLISHED);
        articleEntity.setPublishedDate(LocalDateTime.now());
        articleEntity.setPublisher(profileEntityOptional.get());
        articleRepository.save(articleEntity);
        return new Result("Maqolaga ruxsad berildi",true);
    }

    // 5. Get Last 5 Article By Types  ordered_by_created_date
    public List<ArticleResponseDto> getLast5ArticleByTypes(UUID type, LanguageEnum lang) {

        List<ArticleResponseDto> articleResponseDtoList=new ArrayList<>();

        for (ArticleEntity articleEntity : articleRepository.findTop5ByIdAndVisibleTrueOrderByCreateDateDesc(type)) {
            ArticleResponseDto articleResponseDto=new ArticleResponseDto();
            articleResponseDto.setTitle(articleEntity.getTitle());
            articleResponseDto.setDescription(articleEntity.getDescription());
            articleResponseDto.setContent(articleEntity.getContent());
            articleResponseDto.setImageId(articleEntity.getImageId());
            articleResponseDto.setViewCount(articleEntity.getViewCount());
            articleResponseDto.setCreateDate(articleEntity.getCreateDate());
            articleResponseDto.setPublished_date(articleEntity.getPublishedDate());

            RegionResponseDTO regionDTO=new RegionResponseDTO();
            regionDTO.setId(articleEntity.getRegion().getId());
            switch (lang) {
                case UZ -> regionDTO.setName(articleEntity.getRegion().getNameUz());
                case RU -> regionDTO.setName(articleEntity.getRegion().getNameRu());
                case EN -> regionDTO.setName(articleEntity.getRegion().getNameEn());
            }

            articleResponseDto.setRegion(regionDTO);
            articleResponseDtoList.add(articleResponseDto);

        }

        return articleResponseDtoList;
    }
}
