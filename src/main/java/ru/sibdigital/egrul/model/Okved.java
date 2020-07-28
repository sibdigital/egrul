package ru.sibdigital.egrul.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "okved", schema = "public")
public class Okved {

    private UUID id;
    private String classCode;
    private String subclassCode;
    private String groupCode;
    private String subgroupCode;
    private String kindCode;
    private Short typeCode;
    private String path;
    private Short status;
    private String kindName;
    private String description;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Basic
    @Column(name = "class_code")
    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    @Basic
    @Column(name = "subclass_code")
    public String getSubclassCode() {
        return subclassCode;
    }

    public void setSubclassCode(String subclassCode) {
        this.subclassCode = subclassCode;
    }

    @Basic
    @Column(name = "group_code")
    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    @Basic
    @Column(name = "subgroup_code")
    public String getSubgroupCode() {
        return subgroupCode;
    }

    public void setSubgroupCode(String subgroupCode) {
        this.subgroupCode = subgroupCode;
    }

    @Basic
    @Column(name = "kind_code")
    public String getKindCode() {
        return kindCode;
    }

    public void setKindCode(String kindCode) {
        this.kindCode = kindCode;
    }

    @Basic
    @Column(name = "type_code")
    public Short getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(Short typeCode) {
        this.typeCode = typeCode;
    }

    @Basic
    @Column(name = "path", columnDefinition = "ltree")
    @Type(type = "ru.sibdigital.okved.model.Ltree")
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Basic
    @Column(name = "status")
    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    @Basic
    @Column(name = "kind_name")
    public String getKindName() {
        return kindName;
    }

    public void setKindName(String kindName) {
        this.kindName = kindName;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Okved okved = (Okved) o;
        return Objects.equals(classCode, okved.classCode) &&
                Objects.equals(subclassCode, okved.subclassCode) &&
                Objects.equals(groupCode, okved.groupCode) &&
                Objects.equals(subgroupCode, okved.subgroupCode) &&
                Objects.equals(kindCode, okved.kindCode) &&
                Objects.equals(typeCode, okved.typeCode) &&
                Objects.equals(path, okved.path) &&
                Objects.equals(status, okved.status) &&
                Objects.equals(kindName, okved.kindName) &&
                Objects.equals(description, okved.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(classCode, subclassCode, groupCode, subgroupCode, kindCode, typeCode, path, status, kindName, description);
    }
}
