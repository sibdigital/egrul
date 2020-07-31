package ru.sibdigital.egrul.model;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RegEgripOkvedId implements Serializable {

    @ManyToOne
    @JoinColumn(name="id_egrip")
    private RegEgrip regEgrip;

    @ManyToOne
    @JoinColumn(name="id_okved")
    private Okved okved;

    public RegEgripOkvedId() {

    }

    public RegEgripOkvedId(RegEgrip regEgrip, Okved okved) {
        this.regEgrip = regEgrip;
        this.okved = okved;
    }

    public RegEgrip getRegEgrip() {
        return regEgrip;
    }

    public void setRegEgrip(RegEgrip regEgrip) {
        this.regEgrip = regEgrip;
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
        RegEgripOkvedId that = (RegEgripOkvedId) o;
        return Objects.equals(regEgrip, that.regEgrip) &&
                Objects.equals(okved, that.okved);
    }

    @Override
    public int hashCode() {
        return Objects.hash(regEgrip, okved);
    }
}
