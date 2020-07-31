package ru.sibdigital.egrul.model;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RegEgrulOkvedId implements Serializable {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="id_egrul")
    private RegEgrul regEgrul;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="id_okved")
    private Okved okved;

    public RegEgrulOkvedId() {

    }

    public RegEgrulOkvedId(RegEgrul regEgrul, Okved okved) {
        this.regEgrul = regEgrul;
        this.okved = okved;
    }

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
        RegEgrulOkvedId that = (RegEgrulOkvedId) o;
        return Objects.equals(regEgrul, that.regEgrul) &&
                Objects.equals(okved, that.okved);
    }

    @Override
    public int hashCode() {
        return Objects.hash(regEgrul, okved);
    }
}
