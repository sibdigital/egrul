package ru.sibdigital.egrul.model;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "reg_egrul_okved", schema = "public")
public class RegEgrulOkved {

    @OneToOne
    @JoinColumn(name = "id_egrul", referencedColumnName = "id")
    private RegEgrul regEgrul;

    @OneToOne
    @JoinColumn(name = "id_okved", referencedColumnName = "id")
    private Okved okved;

    public RegEgrul getRegEgrul() {
        return regEgrul;
    }

    public void setRegEgrul(RegEgrul regEgrul) {
        this.regEgrul = regEgrul;
    }

    public Okved getOkved() {
        return okved;
    }

    public void setOkved(Okved okved) {
        this.okved = okved;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegEgrulOkved that = (RegEgrulOkved) o;
        return regEgrul.equals(that.regEgrul) &&
                okved.equals(that.okved);
    }

    @Override
    public int hashCode() {
        return Objects.hash(regEgrul, okved);
    }
}
