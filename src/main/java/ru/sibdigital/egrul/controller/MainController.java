package ru.sibdigital.egrul.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.sibdigital.egrul.dto.EgripResponse;
import ru.sibdigital.egrul.dto.EgrulResponse;
import ru.sibdigital.egrul.dto.egrip.EGRIP;
import ru.sibdigital.egrul.dto.egrul.EGRUL;
import ru.sibdigital.egrul.model.RegEgrip;
import ru.sibdigital.egrul.model.RegEgrul;
import ru.sibdigital.egrul.service.EgrulService;

@RestController
public class MainController {

    private static ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private EgrulService egrulService;

    @CrossOrigin
    @GetMapping("/egrul")
    public EgrulResponse getEgrul(@RequestParam(name = "inn") String inn) {
        EgrulResponse response = new EgrulResponse();
        RegEgrul egrul = egrulService.getEgrul(inn);
        if (egrul != null) {
            try {
                response.setData(mapper.readValue(egrul.getData(), EGRUL.СвЮЛ.class));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    @CrossOrigin
    @GetMapping("/egrip")
    public EgripResponse getEgrip(@RequestParam(name = "inn") String inn) {
        EgripResponse response = new EgripResponse();
        RegEgrip egrip = egrulService.getEgrip(inn);
        if (egrip != null) {
            try {
                response.setData(mapper.readValue(egrip.getData(), EGRIP.СвИП.class));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return response;
    }
}
