package ru.sibdigital.egrul.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.sibdigital.egrul.service.EgrulService;

@RestController
public class MainController {

    @Autowired
    private EgrulService egrulService;

    @GetMapping("/egrul")
    public String getEgrul(@RequestParam(name = "inn") String inn) {
        if (inn == null || inn.isBlank()) {
            return null;
        }
        return egrulService.getEgrul(inn); //TODO переделать,чтобы возвращался не пустойответ, а обертка
    }

    @GetMapping("/egrip")
    public String getEgrip(@RequestParam(name = "inn") String inn) {
        //TODO сделать единообразно с /egrul
        return egrulService.getEgrip(inn); //TODO переделать,чтобы возвращался не пустойответ, а обертка
    }
}
