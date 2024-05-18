package uz.urinov.kun.service;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.urinov.kun.dto.ArticleTypeDto;
import uz.urinov.kun.dto.Result;
import uz.urinov.kun.entity.ArticleTypeEntity;
import uz.urinov.kun.repository.ArticleTypeRepository;

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
    public List<ArticleTypeDto> getArticleTypeList() {

        List<ArticleTypeEntity> articleTypeEntityList = articleTypeRepository.findAllByVisibleTrue();

        return articleTypeEntityList.stream().map(entity -> getArticleTypeDto(entity)).toList();
    }

    // 4. Delete ArticleType
    public Result deleteArticleType(int id) {
        Optional<ArticleTypeEntity> articleTypeEntityOptional = articleTypeRepository.findById(id);
        if (articleTypeEntityOptional.isEmpty()) {
            return new Result("ArticleTypeEntity not found",false);
        }
        articleTypeRepository.delete(articleTypeEntityOptional.get());
        return new Result("ArticleTypeEntity o'chirildi",true);
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
