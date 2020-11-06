package ru.sibdigital.egrul.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.sibdigital.egrul.model.Okved;

@Repository
public interface OkvedRepo extends JpaRepository<Okved, Long> {

    Okved findByKindCode(String кодОКВЭД);

    @Query(nativeQuery = true, value = "select * from okved where kind_code = :kind_code and version = :version")
    Okved findByKindCodeAndVersion(String kind_code, String version);
}
