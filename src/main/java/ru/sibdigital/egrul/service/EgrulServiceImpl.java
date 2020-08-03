package ru.sibdigital.egrul.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sibdigital.egrul.model.RegEgrip;
import ru.sibdigital.egrul.model.RegEgrul;
import ru.sibdigital.egrul.repository.RegEgripRepo;
import ru.sibdigital.egrul.repository.RegEgrulRepo;

@Service
public class EgrulServiceImpl implements EgrulService {

    @Autowired
    private RegEgrulRepo regEgrulRepo;

    @Autowired
    private RegEgripRepo regEgripRepo;

    public String getEgrul(String inn) {
        RegEgrul regEgrul = regEgrulRepo.findByInn(inn);
        if (regEgrul == null) {
            return null;
        }
        return regEgrul.getData();
    }

    public String getEgrip(String inn) {
        RegEgrip regEgrip = regEgripRepo.findByInn(inn);
        if (regEgrip == null) {
            return null;
        }
        return regEgrip.getData();
    }
}
