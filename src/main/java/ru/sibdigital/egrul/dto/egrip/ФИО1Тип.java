//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.07.27 at 05:20:15 PM IRKT 
//


package ru.sibdigital.egrul.dto.egrip;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * Фамилия, имя, отчество
 * 
 * <p>Java class for ФИО1Тип complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ФИО1Тип"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="Фамилия"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *             &lt;minLength value="1"/&gt;
 *             &lt;maxLength value="60"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="Имя"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *             &lt;minLength value="1"/&gt;
 *             &lt;maxLength value="60"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="Отчество"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *             &lt;minLength value="1"/&gt;
 *             &lt;maxLength value="60"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "\u0424\u0418\u041e1\u0422\u0438\u043f")
public class ФИО1Тип {

    @XmlAttribute(name = "\u0424\u0430\u043c\u0438\u043b\u0438\u044f")
    protected String фамилия;
    @XmlAttribute(name = "\u0418\u043c\u044f")
    protected String имя;
    @XmlAttribute(name = "\u041e\u0442\u0447\u0435\u0441\u0442\u0432\u043e")
    protected String отчество;

    /**
     * Gets the value of the фамилия property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getФамилия() {
        return фамилия;
    }

    /**
     * Sets the value of the фамилия property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setФамилия(String value) {
        this.фамилия = value;
    }

    /**
     * Gets the value of the имя property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getИмя() {
        return имя;
    }

    /**
     * Sets the value of the имя property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setИмя(String value) {
        this.имя = value;
    }

    /**
     * Gets the value of the отчество property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getОтчество() {
        return отчество;
    }

    /**
     * Sets the value of the отчество property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setОтчество(String value) {
        this.отчество = value;
    }

}
