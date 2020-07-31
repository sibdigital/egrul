package ru.sibdigital.egrul.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sibdigital.egrul.model.RegEgripOkved;

@Repository
public interface RegEgripOkvedRepo extends JpaRepository<RegEgripOkved, Long> {

}
