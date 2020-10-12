package ru.sibdigital.egrul.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.sibdigital.egrul.service.ImportService;

@Controller
public class ImportController {

    @Autowired
    private ImportService importService;

    @GetMapping("/import")
    public ResponseEntity<String> importData() {
        importService.importData();
        return ResponseEntity.ok().body("Ok");
    }
}
