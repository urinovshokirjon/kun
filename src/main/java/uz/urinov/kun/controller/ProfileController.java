package uz.urinov.kun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.urinov.kun.dto.ProfileCreateDTO;
import uz.urinov.kun.dto.ProfileFilterDTO;
import uz.urinov.kun.dto.ProfileResponseDTO;
import uz.urinov.kun.enums.Result;
import uz.urinov.kun.service.ProfileService;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    //  1. Create profile (ADMIN)
    @PostMapping("/create")
    public ResponseEntity<Result> createProfile(@RequestBody ProfileCreateDTO profileCreateDTO){
        Result result =profileService.createProfile(profileCreateDTO);
        return ResponseEntity.status(result.isSuccess()? HttpStatus.CREATED:HttpStatus.BAD_REQUEST).body(result);
    }

    // 3. Update Profile Detail (ANY) (Profile updates own details)
    @PutMapping("/update-own/{id}")
    public ResponseEntity<Result> updateProfileOwe(@PathVariable int id,@RequestBody ProfileCreateDTO profileCreateDTO){
        Result result =profileService.updateProfileOwe(id,profileCreateDTO);
        return ResponseEntity.status(result.isSuccess()? HttpStatus.OK:HttpStatus.CONFLICT).body(result);
    }

   // 4. Profile List (ADMIN) (Pagination)
    @GetMapping("/page")
    public ResponseEntity<PageImpl<ProfileResponseDTO>> getProfilePage(@RequestParam int page,
                                                                       @RequestParam int size){
        PageImpl<ProfileResponseDTO> profileResponseDTOPage=profileService.getProfilePage(page-1,size);
        return ResponseEntity.status(HttpStatus.OK).body(profileResponseDTOPage);
    }

    // 5. Delete Profile By Id (ADMIN)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Result> deleteProfile(@PathVariable int id){
        Result result =profileService.deleteProfile(id);
        return ResponseEntity.status(result.isSuccess()? HttpStatus.OK:HttpStatus.CONFLICT).body(result);
    }

    //  7. Filter (name,surname,phone,role,created_date_from,created_date_to)
    @PostMapping("page-filter")
    public ResponseEntity<PageImpl<ProfileResponseDTO>> getProfilePageFilter(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestBody ProfileFilterDTO profileFilterDTO){
      PageImpl<ProfileResponseDTO> profileResponseDTOPage= profileService.getProfilePageFilter(page-1,size,profileFilterDTO);
      return ResponseEntity.ok().body(profileResponseDTOPage);
    }




}
