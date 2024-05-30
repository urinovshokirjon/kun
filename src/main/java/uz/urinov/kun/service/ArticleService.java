package uz.urinov.kun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.urinov.kun.dto.ArticleCreateDto;
import uz.urinov.kun.entity.*;
import uz.urinov.kun.enums.ArticleStatus;
import uz.urinov.kun.enums.Result;
import uz.urinov.kun.repository.*;

import java.util.List;
import java.util.Optional;

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

        articleEntity.setTitle(createDto.getTitle());
        articleEntity.setDescription(createDto.getDescription());
        articleEntity.setContent(createDto.getContent());

        articleEntity.setRegion(regionEntityOptional.get());
        articleEntity.setCategory(categoryEntityOptional.get());
        articleEntity.setModerator(profileEntityOptional.get());
        articleEntity.setStatus(ArticleStatus.NOT_PUBLISHED);
        articleEntity.setArticleType(allById);


        articleRepository.save(articleEntity);



    }
}
