package ru.sibdigital.egrul.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "reg_egrip", schema = "public")
public class RegEgrip {

    private UUID id;
    private Timestamp loadDate;
    private String inn;
    private String data;
    private String filePath;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Basic
    @Column(name = "load_date", nullable = true)
    public Timestamp getLoadDate() {
        return loadDate;
    }

    public void setLoadDate(Timestamp loadDate) {
        this.loadDate = loadDate;
    }

    @Basic
    @Column(name = "inn", nullable = true, length = 20)
    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    @Basic
    @Column(name = "data", nullable = true)
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Basic
    @Column(name = "file_path", nullable = true)
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
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
