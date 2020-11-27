package ru.sibdigital.egrul.service;

import ru.sibdigital.egrul.model.ModelTypes;

import java.util.Date;

public interface ImportService {

    void addToSchedule(ModelTypes type, Integer loadVersion, Date date);
    void deleteFromSchedule(ModelTypes type);
    void importEgrul(Integer loadVersion);
    void importEgrip(Integer loadVersion);
}
