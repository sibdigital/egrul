package ru.sibdigital.egrul.model;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "reg_egrul", schema = "public")
@TypeDefs({
        @TypeDef(name = "JsonbType", typeClass = Jsonb.class)
})
public class RegEgrul {

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
    @Type(type = "JsonbType")
    private String data;
    @Basic
    @Column(name = "file_path", nullable = true)
    private String filePath;

    @OneToMany(mappedBy = "regEgrulOkvedId.regEgrul")
    private Set<RegEgrulOkved> regEgrulOkveds;

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

    public Set<RegEgrulOkved> getRegEgrulOkveds() {
        return regEgrulOkveds;
    }

    public void setRegEgrulOkveds(Set<RegEgrulOkved> regEgrulOkveds) {
        this.regEgrulOkveds = regEgrulOkveds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegEgrul regEgrul = (RegEgrul) o;
        return Objects.equals(id, regEgrul.id) &&
                Objects.equals(loadDate, regEgrul.loadDate) &&
                Objects.equals(inn, regEgrul.inn) &&
                Objects.equals(filePath, regEgrul.filePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, loadDate, inn, filePath);
    }
}
