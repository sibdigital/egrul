package ru.sibdigital.egrul.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sibdigital.egrul.model.ClsMigration;

import java.util.List;

@Repository
public interface ClsMigrationRepo extends JpaRepository<ClsMigration, Long> {
    ClsMigration findClsMigrationByFilenameAndType(String filename, Short type);
    List<ClsMigration> findAllByTypeAndStatus(Short type, Short status);
}
