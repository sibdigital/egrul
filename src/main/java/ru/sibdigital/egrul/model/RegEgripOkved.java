package ru.sibdigital.egrul.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "reg_egrip_okved", schema = "public")
public class RegEgripOkved {

    @OneToOne
    @JoinColumn(name = "id_egrip", referencedColumnName = "id")
    private RegEgrip regEgrip;

    @OneToOne
    @JoinColumn(name = "id_okved", referencedColumnName = "id")
    private Okved okved;

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
        RegEgripOkved that = (RegEgripOkved) o;
        return Objects.equals(regEgrip, that.regEgrip) &&
                Objects.equals(okved, that.okved);
    }

    @Override
    public int hashCode() {
        return Objects.hash(regEgrip, okved);
    }
}
