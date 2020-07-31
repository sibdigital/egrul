package ru.sibdigital.egrul.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "reg_egrul_okved", schema = "public")
public class RegEgrulOkved {

    @EmbeddedId
    private RegEgrulOkvedId regEgrulOkvedId;
    @Column(name = "is_main")
    private Boolean isMain;

    public RegEgrulOkved() {

    }

    public RegEgrulOkved(RegEgrulOkvedId regEgrulOkvedId, Boolean isMain) {
        this.regEgrulOkvedId = regEgrulOkvedId;
        this.isMain = isMain;
    }

    public RegEgrulOkvedId getRegEgrulOkvedId() {
        return regEgrulOkvedId;
    }

    public void setRegEgrulOkvedId(RegEgrulOkvedId regEgrulOkvedId) {
        this.regEgrulOkvedId = regEgrulOkvedId;
    }

    public Boolean getMain() {
        return isMain;
    }

    public void setMain(Boolean main) {
        isMain = main;
    }
}
