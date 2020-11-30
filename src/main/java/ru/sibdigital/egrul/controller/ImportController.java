package ru.sibdigital.egrul.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.sibdigital.egrul.model.ModelTypes;
import ru.sibdigital.egrul.service.ImportService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Controller
public class ImportController {

    @Autowired
    private ImportService importService;

    @GetMapping("/import")
    public String importData() {
        return "import";
    }

    @GetMapping("/processFiles")
    public @ResponseBody
    String processFiles() {
        try {
            importService.importData();
        } catch (Exception e) {
            return "Не удалось запустить загрузку";
        }
        return "Ok";
    }

}
