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
import org.springframework.transaction.annotation.Transactional;
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
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Service
public class ImportServiceImpl implements ImportService {

    private final static Logger egrulLogger = LoggerFactory.getLogger("egrulLogger"); // Создание егрюл и оквэдов
    private final static Logger egripLogger = LoggerFactory.getLogger("egripLogger");

    private final static Logger egrulFilesLogger = LoggerFactory.getLogger("egrulDirLogger"); // По каким файлам прошлись
    private final static Logger egripFilesLogger = LoggerFactory.getLogger("egripDirLogger");

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

    @Autowired
    private ClsMigrationRepo clsMigrationRepo;

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

    private ClsMigration getClsMigration(String filename, Short type) {
        ClsMigration migration = clsMigrationRepo.findClsMigrationByFilenameAndType(filename, type);
        return migration;
    }

    private ClsMigration addMigrationRecord(ClsMigration migration, String filename, Short type, Short status, String error){
        if (migration == null) {
            migration = new ClsMigration();
        }

        migration.setFilename(filename);
        migration.setLoadDate(new Timestamp(System.currentTimeMillis()));
        migration.setType(type);
        migration.setStatus(status);
        migration.setError(error);
        clsMigrationRepo.save(migration);

        return migration;
    }

    private void changeMigrationStatus(ClsMigration migration, Short status, String error){
        migration.setStatus(status);
        migration.setError(error);
        clsMigrationRepo.save(migration);
    }

    public void importData() {
        importEgrulData();
        importEgripData();
    }

    private static Comparator<File> compareByFileName = new Comparator<File>() {
        @Override
        public int compare(File file1, File file2) {
            return file1.getName().compareTo(file2.getName());
        }
    };

    ////////////////////////////// ЕГРЮЛ /////////////////////////////
    /**
     * Метод импорта данных ЕГРЮЛ
     */
    private void importEgrulData() {
        egrulLogger.info("Импорт ЕГРЮЛ начат");
        egrulFilesLogger.info("Импорт ЕГРЮЛ начат");

        Collection<File> zipFiles = null;
        try {
            zipFiles = FileUtils.listFiles(new File(egrulPath),
                    new RegexFileFilter("^(.*?)"), DirectoryFileFilter.DIRECTORY);
        } catch (Exception e) {
            egrulFilesLogger.error("Не удалось получить доступ к " + egrulPath);
            e.printStackTrace();
        }

        // Загрузка ПОЛНОГО zip
        List<File> zipFullFiles = zipFiles.stream()
                .filter(s -> s.getName().toLowerCase().contains("full"))
                .collect(Collectors.toList());

        Collections.sort(zipFullFiles, compareByFileName);

        if (zipFiles != null && !zipFiles.isEmpty()) {
            loadEGRULFiles(zipFullFiles);
        }


        // Загрузка ОБНОВЛЕНИЙ
        List<File> zipUpdateFiles = zipFiles.stream()
                .filter(s -> ! s.getName().toLowerCase().contains("full"))
                .collect(Collectors.toList());

        Collections.sort(zipUpdateFiles, compareByFileName);

        if (zipFiles != null && !zipFiles.isEmpty()) {
            loadEGRULFiles(zipUpdateFiles);
        }

        egrulLogger.info("Импорт ЕГРЮЛ окончен");
        egrulFilesLogger.info("Импорт ЕГРЮЛ окончен");
    }

    private void loadEGRULFiles(List<File> zipFiles) {
        for (File zipFile : zipFiles) {
            ClsMigration migration = getClsMigration(zipFile.getName(), ModelTypes.EGRUL_LOAD.getValue());
            if (migration == null || migration.getStatus() != StatusLoadTypes.SUCCESSFULLY_LOADED.getValue()) {
                // Добавить запись об обработке файла
                migration = addMigrationRecord(migration, zipFile.getName(), ModelTypes.EGRUL_LOAD.getValue(), StatusLoadTypes.LOAD_START.getValue(), "");

                // Обработать данные zip
                processEgrulFile(zipFile, migration);

                // Изменить запись о статусе обработки файла
                if (migration.getStatus() == StatusLoadTypes.LOAD_START.getValue()) {
                    changeMigrationStatus(migration, StatusLoadTypes.SUCCESSFULLY_LOADED.getValue(), "");
                }
            }
        }
    }

    private void processEgrulFile(File file, ClsMigration migration) {
        egrulFilesLogger.info("Обработка файла " + file.getName());

        ZipFile zipFile = getZipFile(file);
        if (zipFile != null) {
            Unmarshaller unmarshaller = getUnmarshaller(EGRUL.class);
            if (unmarshaller != null) {
                try {
                    Enumeration<? extends ZipEntry> entries = zipFile.entries();
                    while (entries.hasMoreElements()) {
                        ZipEntry zipEntry = entries.nextElement();

                        egrulFilesLogger.info("Обработка xml файла " + zipEntry.getName());
                        egrulLogger.info("Обработка xml файла " + zipEntry.getName());

                        InputStream is = zipFile.getInputStream(zipEntry);
                        EGRUL egrul = (EGRUL) unmarshaller.unmarshal(is);
                        saveEgrulData(egrul, file.getPath(), migration);
                    }
                } catch (IOException e) {
                    egrulFilesLogger.error("Не удалось прочитать xml-файл из zip-файла");
                    changeMigrationStatus(migration, StatusLoadTypes.COMPLETED_WITH_ERRORS.getValue(), "Не удалось прочитать xml-файл из zip-файла");
                    e.printStackTrace();
                } catch (JAXBException e) {
                    egrulFilesLogger.error("Не удалось демаршализовать xml-файл из zip-файла");
                    changeMigrationStatus(migration, StatusLoadTypes.COMPLETED_WITH_ERRORS.getValue(), "Не удалось демаршализовать xml-файл из zip-файла");
                    e.printStackTrace();
                } finally {
                    try {
                        zipFile.close();
                    } catch (IOException e) {
                        changeMigrationStatus(migration, StatusLoadTypes.COMPLETED_WITH_ERRORS.getValue(), e.getMessage());
                        e.printStackTrace();
                    }
                }
            } else {
                egrulFilesLogger.error("Не удалось создать демаршаллизатор");
                changeMigrationStatus(migration, StatusLoadTypes.COMPLETED_WITH_ERRORS.getValue(), "Не удалось создать демаршаллизатор");
            }
        } else {
            egrulFilesLogger.error("Не удалось прочитать zip-файл");
            changeMigrationStatus(migration, StatusLoadTypes.COMPLETED_WITH_ERRORS.getValue(), "Не удалось прочитать zip-файл");
        }
    }

    @Transactional
    void saveEgrulData(EGRUL egrul, String filePath, ClsMigration migration) {
        egrulFilesLogger.info("Обработка xml файла " + filePath);
        egrulLogger.info("Обработка xml файла " + filePath);
        for (EGRUL.СвЮЛ свЮЛ : egrul.getСвЮЛ()) {
            egrulLogger.info("Обработка ЕГРЮЛ ИНН: " + свЮЛ.getИНН());

            RegEgrul newRegEgrul = new RegEgrul();
            newRegEgrul.setLoadDate(new Timestamp(System.currentTimeMillis()));
            newRegEgrul.setInn(свЮЛ.getИНН());
            try {
                newRegEgrul.setData(mapper.writeValueAsString(свЮЛ));
            } catch (JsonProcessingException e) {
                egrulLogger.error("Не удалось преобразовать данные к JSON для ИНН " + свЮЛ.getИНН());
                changeMigrationStatus(migration, StatusLoadTypes.COMPLETED_WITH_ERRORS.getValue(), "Не удалось преобразовать данные к JSON для ИНН " + свЮЛ.getИНН());
                e.printStackTrace();
            }
            newRegEgrul.setFilePath(filePath);

            RegEgrul regEgrul = regEgrulRepo.findByInn(свЮЛ.getИНН());
            if (regEgrul != null) {
                newRegEgrul.setId(regEgrul.getId());
                regEgrulOkvedRepo.deleteRegEgrulOkved(regEgrul.getId());
            }

            regEgrulRepo.save(newRegEgrul);

            EGRUL.СвЮЛ.СвОКВЭД свОКВЭД = свЮЛ.getСвОКВЭД();
            if (свОКВЭД != null) {
                Set<RegEgrulOkved> regEgrulOkveds = new HashSet<>();

                Okved okved = okvedRepo.findByKindCodeAndVersion(свОКВЭД.getСвОКВЭДОсн().getКодОКВЭД(), свОКВЭД.getСвОКВЭДОсн().getПрВерсОКВЭД() != null ? свОКВЭД.getСвОКВЭДОсн().getПрВерсОКВЭД() : "2001");
                if (okved != null) {
                    RegEgrulOkvedId regEgrulOkvedId = new RegEgrulOkvedId(newRegEgrul, okved);
                    regEgrulOkveds.add(new RegEgrulOkved(regEgrulOkvedId, true));
                } else {
                    egrulLogger.error("ОКВЭД" + свОКВЭД.getСвОКВЭДОсн().getКодОКВЭД() + " не найден для ИНН " + свЮЛ.getИНН());
                    changeMigrationStatus(migration, StatusLoadTypes.COMPLETED_WITH_ERRORS.getValue(), "ОКВЭД" + свОКВЭД.getСвОКВЭДОсн().getКодОКВЭД() + " не найден для ИНН " + свЮЛ.getИНН());
                }
                if (свОКВЭД.getСвОКВЭДДоп() != null) {
                    for (СвОКВЭДТип свОКВЭДТип : свОКВЭД.getСвОКВЭДДоп()) {
                        String version = свОКВЭДТип.getПрВерсОКВЭД() != null ? свОКВЭДТип.getПрВерсОКВЭД() : "2001";
                        okved = okvedRepo.findByKindCodeAndVersion(свОКВЭДТип.getКодОКВЭД(), version);
                        if (okved != null) {
                            RegEgrulOkvedId regEgrulOkvedId = new RegEgrulOkvedId(newRegEgrul, okved);
                            regEgrulOkveds.add(new RegEgrulOkved(regEgrulOkvedId, false));
                        } else {
                            egrulLogger.error("ОКВЭД" + свОКВЭДТип.getКодОКВЭД() + " не найден для ИНН " + свЮЛ.getИНН());
                            changeMigrationStatus(migration, StatusLoadTypes.COMPLETED_WITH_ERRORS.getValue(), "ОКВЭД" + свОКВЭДТип.getКодОКВЭД() + " не найден для ИНН " + свЮЛ.getИНН());
                        }
                    }

                }

                regEgrulOkvedRepo.saveAll(regEgrulOkveds);
            }
        }
    }

    ////////////////////////////// ЕГРИП /////////////////////////////

    /**
     * Метод импорта данных ЕГРИП
     */
    private void importEgripData() {
        egripLogger.info("Импорт ЕГРИП начат");
        egripFilesLogger.info("Импорт ЕГРИП начат");

        Collection<File> zipFiles = null;
        try {
            zipFiles = FileUtils.listFiles(new File(egripPath),
                    new RegexFileFilter("^(.*?)"), DirectoryFileFilter.DIRECTORY);
        } catch (Exception e) {
            egripFilesLogger.error("Не удалось получить доступ к " + egripPath);
            e.printStackTrace();
        }

        // Загрузка ПОЛНОГО zip
        List<File> zipFullFiles = zipFiles.stream()
                .filter(s -> s.getName().toLowerCase().contains("full"))
                .collect(Collectors.toList());

        Collections.sort(zipFullFiles, compareByFileName);
        loadEGRIPFiles(zipFullFiles);

        // Загрузка ОБНОВЛЕНИЙ
        List<File> zipUpdateFiles = zipFiles.stream()
                .filter(s -> ! s.getName().toLowerCase().contains("full"))
                .collect(Collectors.toList());

        Collections.sort(zipUpdateFiles, compareByFileName);
        loadEGRIPFiles(zipUpdateFiles);

        egripLogger.info("Импорт ЕГРИП окончен");
        egripFilesLogger.info("Импорт ЕГРИП окончен");
    }

    private void loadEGRIPFiles(List<File> zipFiles) {
        for (File zipFile : zipFiles) {
            ClsMigration migration = getClsMigration(zipFile.getName(), ModelTypes.EGRIP_LOAD.getValue());
            if (migration == null || migration.getStatus() != StatusLoadTypes.SUCCESSFULLY_LOADED.getValue()) {
                // Добавить запись об обработке файла
                migration = addMigrationRecord(migration, zipFile.getName(), ModelTypes.EGRIP_LOAD.getValue(), StatusLoadTypes.LOAD_START.getValue(), "");

                // Обработать данные zip
                processEgripFile(zipFile, migration);

                // Изменить запись о статусе обработки файла
                if (migration.getStatus() == StatusLoadTypes.LOAD_START.getValue()) {
                    changeMigrationStatus(migration, StatusLoadTypes.SUCCESSFULLY_LOADED.getValue(), "");
                }
            }
        }
    }

    private void processEgripFile(File file, ClsMigration migration) {
        egripFilesLogger.info("Обработка файла " + file.getName());

        ZipFile zipFile = getZipFile(file);
        if (zipFile != null) {
            Unmarshaller unmarshaller = getUnmarshaller(EGRIP.class);
            if (unmarshaller != null) {
                try {
                    Enumeration<? extends ZipEntry> entries = zipFile.entries();
                    while (entries.hasMoreElements()) {
                        ZipEntry zipEntry = entries.nextElement();

                        egripFilesLogger.info("Обработка xml файла " + zipEntry.getName());
                        egripLogger.info("Обработка xml файла " + zipEntry.getName());

                        InputStream is = zipFile.getInputStream(zipEntry);
                        EGRIP egrip = (EGRIP) unmarshaller.unmarshal(is);
                        saveEgripData(egrip, file.getPath(), migration);
                    }
                } catch (IOException e) {
                    egripFilesLogger.error("Не удалось прочитать xml-файл из zip-файла");
                    changeMigrationStatus(migration, StatusLoadTypes.COMPLETED_WITH_ERRORS.getValue(), "Не удалось прочитать xml-файл из zip-файла");
                    e.printStackTrace();
                } catch (JAXBException e) {
                    egripFilesLogger.error("Не удалось демаршализовать xml-файл из zip-файла");
                    changeMigrationStatus(migration, StatusLoadTypes.COMPLETED_WITH_ERRORS.getValue(), "Не удалось демаршализовать xml-файл из zip-файла");
                    e.printStackTrace();
                } finally {
                    try {
                        zipFile.close();
                    } catch (IOException e) {
                        changeMigrationStatus(migration, StatusLoadTypes.COMPLETED_WITH_ERRORS.getValue(), e.getMessage());
                        e.printStackTrace();
                    }
                }
            } else {
                egripFilesLogger.error("Не удалось создать демаршаллизатор");
                changeMigrationStatus(migration, StatusLoadTypes.COMPLETED_WITH_ERRORS.getValue(), "Не удалось создать демаршаллизатор");

            }
        } else {
            egripFilesLogger.error("Не удалось прочитать zip-файл");
            changeMigrationStatus(migration, StatusLoadTypes.COMPLETED_WITH_ERRORS.getValue(), "Не удалось прочитать zip-файл");

        }

    }

    @Transactional
    void saveEgripData(EGRIP egrip, String filePath, ClsMigration migration) {
        egripFilesLogger.info("Обработка xml файла " + filePath);
        egripLogger.info("Обработка xml файла " + filePath);

        for (EGRIP.СвИП свИП : egrip.getСвИП()) {
            egripLogger.info("Обработка ЕГРИП ИНН: " + свИП.getИННФЛ());

            RegEgrip newRegEgrip = new RegEgrip();
            newRegEgrip.setLoadDate(new Timestamp(System.currentTimeMillis()));
            newRegEgrip.setInn(свИП.getИННФЛ());
            try {
                newRegEgrip.setData(mapper.writeValueAsString(свИП));
            } catch (JsonProcessingException e) {
                egripLogger.error("Не удалось преобразовать данные к JSON для ИНН " + свИП.getИННФЛ());
                changeMigrationStatus(migration, StatusLoadTypes.COMPLETED_WITH_ERRORS.getValue(), "Не удалось преобразовать данные к JSON для ИНН " + свИП.getИННФЛ());
                e.printStackTrace();
            }
            newRegEgrip.setFilePath(filePath);

            RegEgrip regEgrip = regEgripRepo.findByInn(свИП.getИННФЛ());
            if (regEgrip != null) {
                newRegEgrip.setId(regEgrip.getId());
                regEgripOkvedRepo.deleteRegEgripOkved(regEgrip.getId());
            }

            regEgripRepo.save(newRegEgrip);

            EGRIP.СвИП.СвОКВЭД свОКВЭД = свИП.getСвОКВЭД();
            if (свОКВЭД != null) {
                Set<RegEgripOkved> regEgripOkveds = new HashSet<>();

                Okved okved = okvedRepo.findByKindCodeAndVersion(свОКВЭД.getСвОКВЭДОсн().getКодОКВЭД(), свОКВЭД.getСвОКВЭДОсн().getПрВерсОКВЭД() != null ? свОКВЭД.getСвОКВЭДОсн().getПрВерсОКВЭД() : "2001");
                if (okved != null) {
                    RegEgripOkvedId regEgripOkvedId = new RegEgripOkvedId(newRegEgrip, okved);
                    regEgripOkveds.add(new RegEgripOkved(regEgripOkvedId, true));
                } else {
                    egripLogger.error("ОКВЭД не найден для ИНН " + свИП.getИННФЛ());
                    changeMigrationStatus(migration, StatusLoadTypes.COMPLETED_WITH_ERRORS.getValue(), "ОКВЭД не найден для ИНН " + свИП.getИННФЛ());
                }
                if (свОКВЭД.getСвОКВЭДДоп() != null) {
                    for (ru.sibdigital.egrul.dto.egrip.СвОКВЭДТип свОКВЭДТип : свОКВЭД.getСвОКВЭДДоп()) {
                        String version = свОКВЭДТип.getПрВерсОКВЭД() != null ? свОКВЭДТип.getПрВерсОКВЭД() : "2001";
                        okved = okvedRepo.findByKindCodeAndVersion(свОКВЭДТип.getКодОКВЭД(), version);
                        if (okved != null) {
                            RegEgripOkvedId regEgrulOkvedId = new RegEgripOkvedId(newRegEgrip, okved);
                            regEgripOkveds.add(new RegEgripOkved(regEgrulOkvedId, false));
                        } else {
                            egripLogger.error("ОКВЭД не найден для ИНН " + свИП.getИННФЛ());
                            changeMigrationStatus(migration, StatusLoadTypes.COMPLETED_WITH_ERRORS.getValue(), "ОКВЭД не найден для ИНН " + свИП.getИННФЛ());
                        }
                    }
                }

                regEgripOkvedRepo.saveAll(regEgripOkveds);
            }
        }
    }

}
