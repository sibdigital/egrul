package ru.sibdigital.egrul.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ru.sibdigital.egrul.dto.EgripResponse;
import ru.sibdigital.egrul.dto.EgrulResponse;
import ru.sibdigital.egrul.dto.egrip.EGRIP;
import ru.sibdigital.egrul.dto.egrul.EGRUL;
import ru.sibdigital.egrul.model.*;
import ru.sibdigital.egrul.repository.ClsMigrationRepo;
import ru.sibdigital.egrul.service.EgrulService;

import java.util.List;

@RestController
public class MainController {

    private static ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private EgrulService egrulService;

    @Autowired
    private ClsMigrationRepo clsMigrationRepo;

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

    @GetMapping("/migration_data")
    public List<ClsMigration> getMigrationData() {
        return clsMigrationRepo.findAll(Sort.by("filename"));
    }

}
