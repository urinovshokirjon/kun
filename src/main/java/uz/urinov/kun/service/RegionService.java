package uz.urinov.kun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.urinov.kun.dto.RegionDto;
import uz.urinov.kun.dto.Result;
import uz.urinov.kun.entity.RegionEntity;
import uz.urinov.kun.repository.RegionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;

    // 1. Region create
    public Result createRegion(RegionDto regionDto) {
        RegionEntity regionEntity = getRegionEntity(regionDto);
        regionRepository.save(regionEntity);
        return new Result("Region saqlandi",true);
    }

    // 2. Region update
    public Result updateRegion(RegionDto regionDto, int id) {
        Optional<RegionEntity> regionEntityOptional = regionRepository.findById(id);
        if (regionEntityOptional.isEmpty()) {
            return new Result("Region not found",false);
        }
        RegionEntity regionEntity = regionEntityOptional.get();
        regionEntity.setVisible(regionDto.getVisible());
        regionEntity.setOrderNumber(regionDto.getOrderNumber());
        regionEntity.setNameUz(regionDto.getNameUz());
        regionEntity.setNameRu(regionDto.getNameRu());
        regionEntity.setNameEn(regionDto.getNameEn());
        regionRepository.save(regionEntity);
        return new Result("Region o'zgartirildi",true);
    }

    // 3. Region list
    public List<RegionDto> getRegionList() {
        List<RegionDto> regionDtoList = new ArrayList<>();
        for (RegionEntity regionEntity : regionRepository.findAllByVisibleTrue()) {
            regionDtoList.add(getRegionDto(regionEntity));
        }
        return regionDtoList;
    }

    //4. Region delete
    public Result deleteRegion(int id) {
        Optional<RegionEntity> regionEntityOptional = regionRepository.findById(id);
        if (regionEntityOptional.isEmpty()) {
            return new Result("Region not found",false);
        }
        RegionEntity regionEntity = regionEntityOptional.get();
        regionEntity.setVisible(false);
        regionRepository.save(regionEntity);
        return new Result("Region o'chirildi",true);
    }





    public RegionDto getRegionDto(RegionEntity regionEntity){
        RegionDto regionDto = new RegionDto();
        regionDto.setId(regionEntity.getId());
        regionDto.setOrderNumber(regionEntity.getOrderNumber());
        regionDto.setNameUz(regionEntity.getNameUz());
        regionDto.setNameRu(regionEntity.getNameRu());
        regionDto.setNameEn(regionEntity.getNameEn());
        regionDto.setVisible(regionEntity.getVisible());
        regionDto.setCreateDate(regionEntity.getCreateDate());
        return regionDto;
    }

    public RegionEntity getRegionEntity(RegionDto regionDto){
        RegionEntity regionEntity = new RegionEntity();
        regionEntity.setId(regionDto.getId());
        regionEntity.setOrderNumber(regionDto.getOrderNumber());
        regionEntity.setNameUz(regionDto.getNameUz());
        regionEntity.setNameRu(regionDto.getNameRu());
        regionEntity.setNameEn(regionDto.getNameEn());
        return regionEntity;
    }



}
