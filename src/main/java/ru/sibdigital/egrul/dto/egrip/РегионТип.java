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
 * Сведения об адресообразующем элементе регион
 * 
 * <p>Java class for РегионТип complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="РегионТип"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="ТипРегион" use="required"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *             &lt;minLength value="1"/&gt;
 *             &lt;maxLength value="30"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="НаимРегион" use="required"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *             &lt;minLength value="1"/&gt;
 *             &lt;maxLength value="40"/&gt;
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
@XmlType(name = "\u0420\u0435\u0433\u0438\u043e\u043d\u0422\u0438\u043f")
public class РегионТип {

    @XmlAttribute(name = "\u0422\u0438\u043f\u0420\u0435\u0433\u0438\u043e\u043d", required = true)
    protected String типРегион;
    @XmlAttribute(name = "\u041d\u0430\u0438\u043c\u0420\u0435\u0433\u0438\u043e\u043d", required = true)
    protected String наимРегион;

    /**
     * Gets the value of the типРегион property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getТипРегион() {
        return типРегион;
    }

    /**
     * Sets the value of the типРегион property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setТипРегион(String value) {
        this.типРегион = value;
    }

    /**
     * Gets the value of the наимРегион property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getНаимРегион() {
        return наимРегион;
    }

    /**
     * Sets the value of the наимРегион property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setНаимРегион(String value) {
        this.наимРегион = value;
    }

}
