package uz.urinov.kun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.urinov.kun.service.EmailHistoryService;

@RestController
@RequestMapping("/email-history")
public class EmailHistoryController {
    @Autowired
    private EmailHistoryService emailHistoryService;


}
