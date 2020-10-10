package ru.sibdigital.egrul.service;

import ru.sibdigital.egrul.model.RegEgrip;
import ru.sibdigital.egrul.model.RegEgrul;

public interface EgrulService {

    RegEgrul getEgrul(String inn);

    RegEgrip getEgrip(String inn);
}
