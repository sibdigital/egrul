package ru.sibdigital.egrul.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sibdigital.egrul.model.RegEgrul;

@Repository
public interface RegEgrulRepo extends JpaRepository<RegEgrul, Long> {

    RegEgrul findByInn(String inn);
}
