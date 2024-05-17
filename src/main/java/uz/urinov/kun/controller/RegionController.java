package uz.urinov.kun.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.urinov.kun.dto.RegionDto;
import uz.urinov.kun.dto.Result;
import uz.urinov.kun.service.RegionService;

import java.util.List;

@RestController
@RequestMapping("/region")
public class RegionController {
    @Autowired
    private RegionService regionService;

    // 1. Region create
    @PostMapping("/create")
    public ResponseEntity<Result> createRegion(@Valid @RequestBody RegionDto regionDto) {
        Result result = regionService.createRegion(regionDto);
        return ResponseEntity.status(result.isSuccess()? HttpStatus.CREATED: HttpStatus.CONFLICT).body(result);
    }

    // 2. Region update
    @PutMapping("/update/{id}")
    public ResponseEntity<Result> updateRegion(@Valid @RequestBody RegionDto regionDto, @PathVariable int id) {
        Result result = regionService.updateRegion(regionDto,id);
        return ResponseEntity.status(result.isSuccess()? HttpStatus.OK: HttpStatus.CONFLICT).body(result);
    }

    // 3. Region list
    @GetMapping("/list")
    public ResponseEntity<List<RegionDto>> getRegionList() {
        List<RegionDto> regionDtoList=regionService.getRegionList();
        return ResponseEntity.status(HttpStatus.OK).body(regionDtoList);
    }

    //4. Region delete
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Result> deleteRegion( @PathVariable int id) {
        Result result = regionService.deleteRegion(id);
        return ResponseEntity.status(result.isSuccess()? HttpStatus.OK: HttpStatus.CONFLICT).body(result);
    }



}
