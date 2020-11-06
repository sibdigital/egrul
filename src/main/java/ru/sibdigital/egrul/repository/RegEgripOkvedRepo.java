package ru.sibdigital.egrul.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.sibdigital.egrul.model.RegEgripOkved;

import java.util.UUID;

@Repository
public interface RegEgripOkvedRepo extends JpaRepository<RegEgripOkved, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM reg_egrip_okved\n" +
            "WHERE id_egrip = :id_egrip",
            nativeQuery = true)
    void deleteRegEgripOkved(@Param("id_egrip") UUID id_egrip);

}
