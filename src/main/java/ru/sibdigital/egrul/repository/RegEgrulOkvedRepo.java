package ru.sibdigital.egrul.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.sibdigital.egrul.model.RegEgrulOkved;

import java.util.UUID;

@Repository
public interface RegEgrulOkvedRepo extends JpaRepository<RegEgrulOkved, Long> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM reg_egrul_okved\n" +
            "WHERE id_egrul = :id_egrul",
            nativeQuery = true)
    void deleteRegEgrulOkved(@Param("id_egrul") UUID id_egrul);
}
