//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.07.27 at 05:01:36 PM IRKT 
//


package ru.sibdigital.egrul.dto.egrul;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Идентификатор записи, ГРН (при наличии) и дата внесения записи в ЕГРЮЛ
 * 
 * <p>Java class for ИдГРНДатаТип complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ИдГРНДатаТип"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="ИдЗап" use="required"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer"&gt;
 *             &lt;totalDigits value="19"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="ГРН" type="{}ОГРНТип" /&gt;
 *       &lt;attribute name="ДатаЗап" use="required"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}date"&gt;
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
@XmlType(name = "\u0418\u0434\u0413\u0420\u041d\u0414\u0430\u0442\u0430\u0422\u0438\u043f")
public class ИдГРНДатаТип {

    @XmlAttribute(name = "\u0418\u0434\u0417\u0430\u043f", required = true)
    protected BigInteger идЗап;
    @XmlAttribute(name = "\u0413\u0420\u041d")
    protected String грн;
    @XmlAttribute(name = "\u0414\u0430\u0442\u0430\u0417\u0430\u043f", required = true)
    protected XMLGregorianCalendar датаЗап;

    /**
     * Gets the value of the идЗап property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getИдЗап() {
        return идЗап;
    }

    /**
     * Sets the value of the идЗап property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setИдЗап(BigInteger value) {
        this.идЗап = value;
    }

    /**
     * Gets the value of the грн property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getГРН() {
        return грн;
    }

    /**
     * Sets the value of the грн property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setГРН(String value) {
        this.грн = value;
    }

    /**
     * Gets the value of the датаЗап property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getДатаЗап() {
        return датаЗап;
    }

    /**
     * Sets the value of the датаЗап property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setДатаЗап(XMLGregorianCalendar value) {
        this.датаЗап = value;
    }

}
