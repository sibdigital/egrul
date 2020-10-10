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

    public RegEgrul getEgrul(String inn) {
        if (inn == null || inn.isBlank()) {
            return null;
        }
        RegEgrul regEgrul = regEgrulRepo.findByInn(inn);
        return regEgrul;
    }

    public RegEgrip getEgrip(String inn) {
        if (inn == null || inn.isBlank()) {
            return null;
        }
        RegEgrip regEgrip = regEgripRepo.findByInn(inn);
        return regEgrip;
    }
}
