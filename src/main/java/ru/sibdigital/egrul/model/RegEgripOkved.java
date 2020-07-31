package ru.sibdigital.egrul.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "reg_egrip_okved", schema = "public")
public class RegEgripOkved {

    @EmbeddedId
    private RegEgripOkvedId regEgripOkvedId;
    @Column(name = "is_main")
    private Boolean isMain;

    public RegEgripOkved() {
    }

    public RegEgripOkved(RegEgripOkvedId regEgripOkvedId, Boolean isMain) {
        this.regEgripOkvedId = regEgripOkvedId;
        this.isMain = isMain;
    }

    public RegEgripOkvedId getRegEgripOkvedId() {
        return regEgripOkvedId;
    }

    public void setRegEgripOkvedId(RegEgripOkvedId regEgripOkvedId) {
        this.regEgripOkvedId = regEgripOkvedId;
    }

    public Boolean getMain() {
        return isMain;
    }

    public void setMain(Boolean main) {
        isMain = main;
    }
}
