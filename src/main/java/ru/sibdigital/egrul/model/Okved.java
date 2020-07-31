package ru.sibdigital.egrul.model;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "okved", schema = "public")
@TypeDef(name = "Ltree", typeClass = Ltree.class)
public class Okved {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Basic
    @Column(name = "class_code")
    private String classCode;
    @Basic
    @Column(name = "subclass_code")
    private String subclassCode;
    @Basic
    @Column(name = "group_code")
    private String groupCode;
    @Basic
    @Column(name = "subgroup_code")
    private String subgroupCode;
    @Basic
    @Column(name = "kind_code")
    private String kindCode;
    @Basic
    @Column(name = "type_code")
    private Short typeCode;
    @Basic
    @Column(name = "path", columnDefinition = "ltree")
    @Type(type = "Ltree")
    private String path;
    @Basic
    @Column(name = "status")
    private Short status;
    @Basic
    @Column(name = "kind_name")
    private String kindName;
    @Basic
    @Column(name = "description")
    private String description;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getSubclassCode() {
        return subclassCode;
    }

    public void setSubclassCode(String subclassCode) {
        this.subclassCode = subclassCode;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getSubgroupCode() {
        return subgroupCode;
    }

    public void setSubgroupCode(String subgroupCode) {
        this.subgroupCode = subgroupCode;
    }

    public String getKindCode() {
        return kindCode;
    }

    public void setKindCode(String kindCode) {
        this.kindCode = kindCode;
    }

    public Short getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(Short typeCode) {
        this.typeCode = typeCode;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getKindName() {
        return kindName;
    }

    public void setKindName(String kindName) {
        this.kindName = kindName;
    }

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
