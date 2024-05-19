package uz.urinov.kun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.urinov.kun.dto.ArticleTypeDto;
import uz.urinov.kun.dto.ArticleTypeLangDto;
import uz.urinov.kun.dto.Result;
import uz.urinov.kun.entity.ArticleTypeEntity;
import uz.urinov.kun.repository.ArticleTypeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleTypeService {
    @Autowired
    private ArticleTypeRepository articleTypeRepository;

    // 1. ArticleType created
    public Result createType(ArticleTypeDto articleTypeDto) {
        ArticleTypeEntity articleTypeEntity = getArticleTypeEntity(articleTypeDto);
        articleTypeRepository.save(articleTypeEntity);
        return new Result("ArticleTypeEntity saqlandi",true);
    }

    // 2. Update ArticleType
    public Result updateType(int id, ArticleTypeDto articleTypeDto) {
        Optional<ArticleTypeEntity> articleTypeEntityOptional = articleTypeRepository.findById(id);
        if (articleTypeEntityOptional.isEmpty()) {
            return new Result("ArticleTypeEntity not found",false);
        }
        ArticleTypeEntity articleTypeEntity = articleTypeEntityOptional.get();
        articleTypeEntity.setOrderNumber(articleTypeDto.getOrderNumber());
        articleTypeEntity.setNameUz(articleTypeDto.getNameUz());
        articleTypeEntity.setNameRu(articleTypeDto.getNameRu());
        articleTypeEntity.setNameEn(articleTypeDto.getNameEn());
        articleTypeEntity.setVisible(articleTypeDto.getVisible());
        articleTypeRepository.save(articleTypeEntity);
        return new Result("ArticleTypeEntity tahrirlandi",true);
    }

    // 3. List ArticleType
    public PageImpl<ArticleTypeDto> getArticleTypeList(int page, int size) {

        Pageable pageable= PageRequest.of(page, size);

        Page<ArticleTypeEntity> articleTypeEntityList = articleTypeRepository.findAllByVisibleTrueOrderByOrderNumber(pageable);

        List<ArticleTypeDto> articleTypeDtoList = articleTypeEntityList.stream().map(this::getArticleTypeDto).toList();
        return new PageImpl<>(articleTypeDtoList,pageable,articleTypeEntityList.getTotalElements());
    }

    // 4. Delete ArticleType
    public Result deleteArticleType(int id) {
        Optional<ArticleTypeEntity> articleTypeEntityOptional = articleTypeRepository.findById(id);
        if (articleTypeEntityOptional.isEmpty()) {
            return new Result("ArticleTypeEntity not found",false);
        }
        articleTypeEntityOptional.get().setVisible(false);
        articleTypeRepository.save(articleTypeEntityOptional.get());
        return new Result("ArticleTypeEntity o'chirildi",true);
    }

    // 5. Get By Lang ArticleType
    public PageImpl<ArticleTypeLangDto> getArticleTypePage(int page, int size, String lang) {

        List<ArticleTypeLangDto> articleTypeLangDtoList=new ArrayList<>();

        Pageable pageable= PageRequest.of(page, size);

        Page<ArticleTypeEntity> articleTypeEntityList = articleTypeRepository.findAllByVisibleTrueOrderByOrderNumber(pageable);

        for (ArticleTypeEntity articleTypeEntity : articleTypeEntityList.getContent()) {

            ArticleTypeLangDto articleTypeLangDto=new ArticleTypeLangDto();

            articleTypeLangDto.setId(articleTypeEntity.getId());

            articleTypeLangDto.setOrderNumber(articleTypeEntity.getOrderNumber());

            if (lang.equals("uz")) {
               articleTypeLangDto.setName(articleTypeEntity.getNameUz());
            }
            if (lang.equals("ru")) {
                articleTypeLangDto.setName(articleTypeEntity.getNameRu());
            }
            if (lang.equals("en")) {
                articleTypeLangDto.setName(articleTypeEntity.getNameEn());
            }

            articleTypeLangDtoList.add(articleTypeLangDto);
        }
        return new PageImpl<>(articleTypeLangDtoList,pageable,articleTypeEntityList.getTotalElements());

    }




    public ArticleTypeDto getArticleTypeDto(ArticleTypeEntity articleTypeEntity) {
        ArticleTypeDto articleTypeDto = new ArticleTypeDto();
        articleTypeDto.setId(articleTypeEntity.getId());
        articleTypeDto.setNameUz(articleTypeEntity.getNameUz());
        articleTypeDto.setNameRu(articleTypeEntity.getNameRu());
        articleTypeDto.setNameEn(articleTypeEntity.getNameEn());
        articleTypeDto.setOrderNumber(articleTypeEntity.getOrderNumber());
        articleTypeDto.setCreateDate(articleTypeEntity.getCreateDate());
        return articleTypeDto;
    }

    public ArticleTypeEntity getArticleTypeEntity(ArticleTypeDto articleTypeDto) {
        ArticleTypeEntity articleTypeEntity = new ArticleTypeEntity();
        articleTypeEntity.setOrderNumber(articleTypeDto.getOrderNumber());
        articleTypeEntity.setNameUz(articleTypeDto.getNameUz());
        articleTypeEntity.setNameRu(articleTypeDto.getNameRu());
        articleTypeEntity.setNameEn(articleTypeDto.getNameEn());
        return articleTypeEntity;
    }



}
