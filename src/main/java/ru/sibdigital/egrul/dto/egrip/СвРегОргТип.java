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
 * Сведения о регистрирующем органе
 * 
 * <p>Java class for СвРегОргТип complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="СвРегОргТип"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="КодНО" use="required" type="{}СОНОТип" /&gt;
 *       &lt;attribute name="НаимНО" use="required"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *             &lt;minLength value="10"/&gt;
 *             &lt;maxLength value="250"/&gt;
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
@XmlType(name = "\u0421\u0432\u0420\u0435\u0433\u041e\u0440\u0433\u0422\u0438\u043f")
public class СвРегОргТип {

    @XmlAttribute(name = "\u041a\u043e\u0434\u041d\u041e", required = true)
    protected String кодНО;
    @XmlAttribute(name = "\u041d\u0430\u0438\u043c\u041d\u041e", required = true)
    protected String наимНО;

    /**
     * Gets the value of the кодНО property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getКодНО() {
        return кодНО;
    }

    /**
     * Sets the value of the кодНО property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setКодНО(String value) {
        this.кодНО = value;
    }

    /**
     * Gets the value of the наимНО property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getНаимНО() {
        return наимНО;
    }

    /**
     * Sets the value of the наимНО property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setНаимНО(String value) {
        this.наимНО = value;
    }

}
