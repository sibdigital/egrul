package ru.sibdigital.egrul.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.sibdigital.egrul.dto.egrip.EGRIP;
import ru.sibdigital.egrul.dto.egrul.EGRUL;
import ru.sibdigital.egrul.dto.egrul.СвОКВЭДТип;
import ru.sibdigital.egrul.model.*;
import ru.sibdigital.egrul.repository.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Service
public class ImportServiceImpl implements ImportService {

    private final static Logger egrulLogger = LoggerFactory.getLogger("egrulLogger");
    private final static Logger egripLogger = LoggerFactory.getLogger("egripLogger");

    private final ObjectMapper mapper = new ObjectMapper();

    @Value("${egrul.import.directory}")
    private String egrulPath;

    @Value("${egrip.import.directory}")
    private String egripPath;

    @Autowired
    private RegEgrulRepo regEgrulRepo;

    @Autowired
    private RegEgripRepo regEgripRepo;

    @Autowired
    private OkvedRepo okvedRepo;

    @Autowired
    private RegEgrulOkvedRepo regEgrulOkvedRepo;

    @Autowired
    private RegEgripOkvedRepo regEgripOkvedRepo;

    /**
     * Общий метод импорта ЕГРЮЛ/ЕГРИП
     */
    public void importData() {
        importEgrulData();
        importEgripData();
    }

    /**
     * Метод импорта данных ЕГРЮЛ
     */
    private void importEgrulData() {
        egrulLogger.info("Импорт ЕГРЮЛ начат");

        Collection<File> zipFiles = null;
        try {
            zipFiles = FileUtils.listFiles(new File(egrulPath),
                    new RegexFileFilter("^(.*?)"), DirectoryFileFilter.DIRECTORY);
        } catch (Exception e) {
            egrulLogger.info("Не удалось получить доступ к " + egrulPath);
            e.printStackTrace();
        }

        if (zipFiles != null && !zipFiles.isEmpty()) {
            for (File zipFile : zipFiles) {
                Collection<EGRUL> egruls = getEgrulData(zipFile);
                if (egruls != null && !egruls.isEmpty()) {
                    try {
                        saveEgrulDatas(egruls, zipFile.getPath());
                    } catch (Exception e) {
                        egrulLogger.info("Не удалось сохранить данные файла " + zipFile.getName());
                        e.printStackTrace();
                    }
                }
            }
        } else {
            egrulLogger.info("Zip-файлы не найдены по пути " + egrulPath);
        }

        egrulLogger.info("Импорт ЕГРЮЛ окончен");
    }

    private void saveEgrulDatas(Collection<EGRUL> egruls, String filePath) {
        for (EGRUL egrul : egruls) {
            for (EGRUL.СвЮЛ свЮЛ : egrul.getСвЮЛ()) {

                RegEgrul newRegEgrul = new RegEgrul();
                newRegEgrul.setLoadDate(new Timestamp(System.currentTimeMillis()));
                newRegEgrul.setInn(свЮЛ.getИНН());
                try {
                    newRegEgrul.setData(mapper.writeValueAsString(свЮЛ));
                } catch (JsonProcessingException e) {
                    egrulLogger.info("Не удалось преобразовать данные к JSON для ИНН " + свЮЛ.getИНН());
                    e.printStackTrace();
                }
                newRegEgrul.setFilePath(filePath);

                RegEgrul regEgrul = regEgrulRepo.findByInn(свЮЛ.getИНН());
                if (regEgrul != null) {
                    newRegEgrul.setId(regEgrul.getId());
                    for (RegEgrulOkved regEgrulOkved: regEgrul.getRegEgrulOkveds()) {
                        regEgrulOkvedRepo.delete(regEgrulOkved);
                    }
                }

                regEgrulRepo.save(newRegEgrul);

                EGRUL.СвЮЛ.СвОКВЭД свОКВЭД = свЮЛ.getСвОКВЭД();
                if (свОКВЭД != null) {
                    Set<RegEgrulOkved> regEgrulOkveds = new HashSet<>();

                    Okved okved = okvedRepo.findByKindCode(свОКВЭД.getСвОКВЭДОсн().getКодОКВЭД());
                    if (okved != null) {
                        RegEgrulOkvedId regEgrulOkvedId = new RegEgrulOkvedId(newRegEgrul, okved);
                        regEgrulOkveds.add(new RegEgrulOkved(regEgrulOkvedId, true));
                    } else {
                        egrulLogger.info("ОКВЭД не найден для ИНН " + свЮЛ.getИНН());
                    }
                    if (свОКВЭД.getСвОКВЭДДоп() != null) {
                        for (СвОКВЭДТип свОКВЭДТип : свОКВЭД.getСвОКВЭДДоп()) {
                            okved = okvedRepo.findByKindCode(свОКВЭДТип.getКодОКВЭД());
                            if (okved != null) {
                                RegEgrulOkvedId regEgrulOkvedId = new RegEgrulOkvedId(newRegEgrul, okved);
                                regEgrulOkveds.add(new RegEgrulOkved(regEgrulOkvedId, false));
                            } else {
                                egrulLogger.info("ОКВЭД не найден для ИНН " + свЮЛ.getИНН());
                            }
                        }
                    }

                    regEgrulOkvedRepo.saveAll(regEgrulOkveds);
                }
            }
        }
    }

    private Collection<EGRUL> getEgrulData(File file) {
        egrulLogger.info("Обработка файла " + file.getName());

        ZipFile zipFile = getZipFile(file);
        if (zipFile == null) {
            egrulLogger.info("Не удалось прочитать zip-файл");
            return null;
        }

        Unmarshaller unmarshaller = getUnmarshaller(EGRUL.class);
        if (unmarshaller == null) {
            egrulLogger.info("Не удалось создать демаршаллизатор");
            return null;
        }

        Collection<EGRUL> egruls = new ArrayList<>();

        try {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry zipEntry = entries.nextElement();
                InputStream is = zipFile.getInputStream(zipEntry);
                EGRUL egrul = (EGRUL) unmarshaller.unmarshal(is);
                egruls.add(egrul);
            }
        } catch (IOException e) {
            egruls = null;
            egrulLogger.info("Не удалось прочитать xml-файл из zip-файла");
            e.printStackTrace();
        } catch (JAXBException e) {
            egruls = null;
            egrulLogger.info("Не удалось демаршализовать xml-файл из zip-файла");
            e.printStackTrace();
        } finally {
            try {
                zipFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return egruls;
    }

    private static Unmarshaller getUnmarshaller(Class clazz) {
        Unmarshaller unmarshaller = null;
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            unmarshaller = context.createUnmarshaller();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return unmarshaller;
    }

    private static ZipFile getZipFile(File file) {
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return zipFile;
    }

    /**
     * Метод импорта данных ЕГРИП
     */
    private void importEgripData() {
        egripLogger.info("Импорт ЕГРИП начат");

        Collection<File> zipFiles = null;
        try {
            zipFiles = FileUtils.listFiles(new File(egripPath),
                    new RegexFileFilter("^(.*?)"), DirectoryFileFilter.DIRECTORY);
        } catch (Exception e) {
            egripLogger.info("Не удалось получить доступ к " + egripPath);
            e.printStackTrace();
        }

        if (zipFiles != null && !zipFiles.isEmpty()) {
            for (File zipFile : zipFiles) {
                Collection<EGRIP> egrips = getEgripData(zipFile);
                if (egrips != null && !egrips.isEmpty()) {
                    try {
                        saveEgripDatas(egrips, zipFile.getPath());
                    } catch (Exception e) {
                        egripLogger.info("Не удалось сохранить данные файла " + zipFile.getName());
                        e.printStackTrace();
                    }
                }
            }
        } else {
            egripLogger.info("Zip-файлы не найдены по пути " + egripPath);
        }

        egripLogger.info("Импорт ЕГРИП окончен");
    }

    private void saveEgripDatas(Collection<EGRIP> egrips, String filePath) {
        for (EGRIP egrip : egrips) {
            for (EGRIP.СвИП свИП : egrip.getСвИП()) {

                RegEgrip newRegEgrip = new RegEgrip();
                newRegEgrip.setLoadDate(new Timestamp(System.currentTimeMillis()));
                newRegEgrip.setInn(свИП.getИННФЛ());
                try {
                    newRegEgrip.setData(mapper.writeValueAsString(свИП));
                } catch (JsonProcessingException e) {
                    egripLogger.info("Не удалось преобразовать данные к JSON для ИНН " + свИП.getИННФЛ());
                    e.printStackTrace();
                }
                newRegEgrip.setFilePath(filePath);

                RegEgrip regEgrip = regEgripRepo.findByInn(свИП.getИННФЛ());
                if (regEgrip != null) {
                    newRegEgrip.setId(regEgrip.getId());
                    for (RegEgripOkved regEgripOkved: regEgrip.getRegEgripOkveds()) {
                        regEgripOkvedRepo.delete(regEgripOkved);
                    }
                }

                regEgripRepo.save(newRegEgrip);

                EGRIP.СвИП.СвОКВЭД свОКВЭД = свИП.getСвОКВЭД();
                if (свОКВЭД != null) {
                    Set<RegEgripOkved> regEgripOkveds = new HashSet<>();

                    Okved okved = okvedRepo.findByKindCode(свОКВЭД.getСвОКВЭДОсн().getКодОКВЭД());
                    if (okved != null) {
                        RegEgripOkvedId regEgripOkvedId = new RegEgripOkvedId(newRegEgrip, okved);
                        regEgripOkveds.add(new RegEgripOkved(regEgripOkvedId, true));
                    } else {
                        egripLogger.info("ОКВЭД не найден для ИНН " + свИП.getИННФЛ());
                    }
                    if (свОКВЭД.getСвОКВЭДДоп() != null) {
                        for (ru.sibdigital.egrul.dto.egrip.СвОКВЭДТип свОКВЭДТип : свОКВЭД.getСвОКВЭДДоп()) {
                            okved = okvedRepo.findByKindCode(свОКВЭДТип.getКодОКВЭД());
                            if (okved != null) {
                                RegEgripOkvedId regEgrulOkvedId = new RegEgripOkvedId(newRegEgrip, okved);
                                regEgripOkveds.add(new RegEgripOkved(regEgrulOkvedId, false));
                            } else {
                                egripLogger.info("ОКВЭД не найден для ИНН " + свИП.getИННФЛ());
                            }
                        }
                    }

                    regEgripOkvedRepo.saveAll(regEgripOkveds);
                }
            }
        }
    }

    private Collection<EGRIP> getEgripData(File file) {
        egripLogger.info("Обработка файла " + file.getName());

        ZipFile zipFile = getZipFile(file);
        if (zipFile == null) {
            egripLogger.info("Не удалось прочитать zip-файл");
            return null;
        }

        Unmarshaller unmarshaller = getUnmarshaller(EGRIP.class);
        if (unmarshaller == null) {
            egripLogger.info("Не удалось создать демаршаллизатор");
            return null;
        }

        Collection<EGRIP> egrips = new ArrayList<>();

        try {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry zipEntry = entries.nextElement();
                InputStream is = zipFile.getInputStream(zipEntry);
                EGRIP egrip = (EGRIP) unmarshaller.unmarshal(is);
                egrips.add(egrip);
            }
        } catch (IOException e) {
            egripLogger.info("Не удалось прочитать xml-файл из zip-файла");
            egrips = null;
            e.printStackTrace();
        } catch (JAXBException e) {
            egripLogger.info("Не удалось демаршализовать xml-файл из zip-файла");
            egrips = null;
            e.printStackTrace();
        } finally {
            try {
                zipFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return egrips;
    }
}
