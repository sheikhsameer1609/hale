//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-833 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.08.16 at 11:40:47 AM MESZ 
//


package eu.esdihumboldt.generated.oml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for propValueRestrictionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="propValueRestrictionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.omwg.org/TR/d7/ontology/alignment}comparator"/>
 *         &lt;choice>
 *           &lt;element name="value" type="{http://www.omwg.org/TR/d7/ontology/alignment}valueExprType" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element ref="{http://www.esdi-humboldt.eu/goml}ValueClass" minOccurs="0"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "propValueRestrictionType", propOrder = {
    "comparator",
    "value",
    "valueClass"
})
public class PropValueRestrictionType {

    @XmlElement(required = true)
    protected ComparatorEnumType comparator;
    protected List<ValueExprType> value;
    @XmlElement(name = "ValueClass", namespace = "http://www.esdi-humboldt.eu/goml")
    protected ValueClassType valueClass;

    /**
     * Gets the value of the comparator property.
     * 
     * @return
     *     possible object is
     *     {@link ComparatorEnumType }
     *     
     */
    public ComparatorEnumType getComparator() {
        return comparator;
    }

    /**
     * Sets the value of the comparator property.
     * 
     * @param value
     *     allowed object is
     *     {@link ComparatorEnumType }
     *     
     */
    public void setComparator(ComparatorEnumType value) {
        this.comparator = value;
    }

    /**
     * Gets the value of the value property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the value property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getValue().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ValueExprType }
     * 
     * 
     */
    public List<ValueExprType> getValue() {
        if (value == null) {
            value = new ArrayList<ValueExprType>();
        }
        return this.value;
    }

    /**
     * Gets the value of the valueClass property.
     * 
     * @return
     *     possible object is
     *     {@link ValueClassType }
     *     
     */
    public ValueClassType getValueClass() {
        return valueClass;
    }

    /**
     * Sets the value of the valueClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link ValueClassType }
     *     
     */
    public void setValueClass(ValueClassType value) {
        this.valueClass = value;
    }

}
