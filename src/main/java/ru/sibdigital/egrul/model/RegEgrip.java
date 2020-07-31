package ru.sibdigital.egrul.model;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "reg_egrip", schema = "public")
@TypeDef(name = "Jsonb", typeClass = Jsonb.class)
public class RegEgrip {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Basic
    @Column(name = "load_date", nullable = true)
    private Timestamp loadDate;
    @Basic
    @Column(name = "inn", nullable = true, length = 20)
    private String inn;
    @Basic
    @Column(name = "data", nullable = true, columnDefinition = "jsonb")
    @Type(type = "Jsonb")
    private String data;
    @Basic
    @Column(name = "file_path", nullable = true)
    private String filePath;

    @OneToMany(mappedBy = "regEgripOkvedId.regEgrip")
    private Set<RegEgripOkved> regEgripOkveds;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Timestamp getLoadDate() {
        return loadDate;
    }

    public void setLoadDate(Timestamp loadDate) {
        this.loadDate = loadDate;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Set<RegEgripOkved> getRegEgripOkveds() {
        return regEgripOkveds;
    }

    public void setRegEgripOkveds(Set<RegEgripOkved> regEgripOkveds) {
        this.regEgripOkveds = regEgripOkveds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegEgrip regEgrip = (RegEgrip) o;
        return Objects.equals(id, regEgrip.id) &&
                Objects.equals(loadDate, regEgrip.loadDate) &&
                Objects.equals(inn, regEgrip.inn) &&
                Objects.equals(filePath, regEgrip.filePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, loadDate, inn, filePath);
    }
}
