package ru.sibdigital.egrul.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.sibdigital.egrul.repository.RegEgripRepo;
import ru.sibdigital.egrul.repository.RegEgrulRepo;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ImportServiceImplTests {

    @TestConfiguration
    static class ImportServiceImplTestContextConfiguration {
        @Bean
        public ImportService importService() {
            return new ImportServiceImpl();
        }
    }

    @Autowired
    private ImportService importService;

    @MockBean
    private RegEgrulRepo regEgrulRepo;

    @MockBean
    private RegEgripRepo regEgripRepo;

    @Test
    public void importData() {
        importService.importData();
    }
}
