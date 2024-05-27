package uz.urinov.kun.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.urinov.kun.dto.LoginDto;
import uz.urinov.kun.dto.ProfileCreateDTO;
import uz.urinov.kun.enums.Result;
import uz.urinov.kun.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    // Profile registration Email
    @PostMapping("/registrationEmail")
    public ResponseEntity<Result> registrationEmail(@Valid @RequestBody ProfileCreateDTO dto) {
        Result result = authService.registrationEmail(dto);
        return ResponseEntity.ok().body(result);
    }

    // Profile verifyEmail
    @GetMapping("/verifyEmail")
    public ResponseEntity<Result> verifyEmail(@RequestParam String emailCode, @RequestParam String email) {
        Result result = authService.verifyEmail(emailCode, email);
        return ResponseEntity.status(result.isSuccess() ? 200 : 409).body(result);
    }

    // Resent Email code
    @GetMapping("/verification/resendEmail/{email}")
    public ResponseEntity<Result> verificationResendEmail(@PathVariable String email) {
        Result result = authService.verificationResendEmail(email);
        return ResponseEntity.status(result.isSuccess() ? 200 : 409).body(result);
    }

    // Profile registration Sms
    @PostMapping("/registrationSms")
    public ResponseEntity<Result> registrationSms(@RequestBody ProfileCreateDTO dto) {
        Result result = authService.registrationSms(dto);
        return ResponseEntity.ok().body(result);
    }

    // Profile verifySms
    @GetMapping("/verifySms")
    public ResponseEntity<Result> verifySms(@RequestParam String smsCode, @RequestParam String phone) {
        Result result = authService.verifySms(smsCode, phone);
        return ResponseEntity.status(result.isSuccess() ? 200 : 409).body(result);
    }
    // Resent sms code
    @GetMapping("/verification/resendSma/{phone}")
    public ResponseEntity<Result> verificationResendSms(@PathVariable String phone) {
        Result result = authService.verificationResendSms(phone);
        return ResponseEntity.status(result.isSuccess() ? 200 : 409).body(result);
    }

    // Profile login
    @PostMapping("/login")
    public HttpEntity<?> loginUser(@RequestBody LoginDto loginDto) {
        Result result = authService.loginProfile(loginDto);
        return ResponseEntity.status(result.isSuccess() ? 200 : 409).body(result);
    }




}
