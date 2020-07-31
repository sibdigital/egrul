package ru.sibdigital.egrul.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sibdigital.egrul.model.RegEgrulOkved;

@Repository
public interface RegEgrulOkvedRepo extends JpaRepository<RegEgrulOkved, Long> {

}
