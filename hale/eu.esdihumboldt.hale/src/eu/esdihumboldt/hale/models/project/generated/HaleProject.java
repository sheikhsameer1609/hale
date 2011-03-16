//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.1-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.03.14 at 12:01:36 PM MEZ 
//


package eu.esdihumboldt.hale.models.project.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HaleProject complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HaleProject">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="haleVersion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dateCreated" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dateModified" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="omlPath" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="instanceData" type="{}InstanceData"/>
 *         &lt;element name="sourceSchema" type="{}MappedSchema"/>
 *         &lt;element name="targetSchema" type="{}MappedSchema"/>
 *         &lt;element name="taskStatus" type="{}TaskStatus"/>
 *         &lt;element name="styles" type="{}Styles" minOccurs="0"/>
 *         &lt;element name="configSchema" type="{}configSchema" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HaleProject", propOrder = {
    "name",
    "haleVersion",
    "dateCreated",
    "dateModified",
    "omlPath",
    "instanceData",
    "sourceSchema",
    "targetSchema",
    "taskStatus",
    "styles",
    "configSchema"
})
public class HaleProject {

    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected String haleVersion;
    @XmlElement(required = true)
    protected String dateCreated;
    @XmlElement(required = true)
    protected String dateModified;
    @XmlElement(required = true)
    protected String omlPath;
    @XmlElement(required = true)
    protected InstanceData instanceData;
    @XmlElement(required = true)
    protected MappedSchema sourceSchema;
    @XmlElement(required = true)
    protected MappedSchema targetSchema;
    @XmlElement(required = true)
    protected TaskStatus taskStatus;
    protected Styles styles;
    protected List<ConfigSchema> configSchema;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the haleVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHaleVersion() {
        return haleVersion;
    }

    /**
     * Sets the value of the haleVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHaleVersion(String value) {
        this.haleVersion = value;
    }

    /**
     * Gets the value of the dateCreated property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateCreated() {
        return dateCreated;
    }

    /**
     * Sets the value of the dateCreated property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateCreated(String value) {
        this.dateCreated = value;
    }

    /**
     * Gets the value of the dateModified property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateModified() {
        return dateModified;
    }

    /**
     * Sets the value of the dateModified property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateModified(String value) {
        this.dateModified = value;
    }

    /**
     * Gets the value of the omlPath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOmlPath() {
        return omlPath;
    }

    /**
     * Sets the value of the omlPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOmlPath(String value) {
        this.omlPath = value;
    }

    /**
     * Gets the value of the instanceData property.
     * 
     * @return
     *     possible object is
     *     {@link InstanceData }
     *     
     */
    public InstanceData getInstanceData() {
        return instanceData;
    }

    /**
     * Sets the value of the instanceData property.
     * 
     * @param value
     *     allowed object is
     *     {@link InstanceData }
     *     
     */
    public void setInstanceData(InstanceData value) {
        this.instanceData = value;
    }

    /**
     * Gets the value of the sourceSchema property.
     * 
     * @return
     *     possible object is
     *     {@link MappedSchema }
     *     
     */
    public MappedSchema getSourceSchema() {
        return sourceSchema;
    }

    /**
     * Sets the value of the sourceSchema property.
     * 
     * @param value
     *     allowed object is
     *     {@link MappedSchema }
     *     
     */
    public void setSourceSchema(MappedSchema value) {
        this.sourceSchema = value;
    }

    /**
     * Gets the value of the targetSchema property.
     * 
     * @return
     *     possible object is
     *     {@link MappedSchema }
     *     
     */
    public MappedSchema getTargetSchema() {
        return targetSchema;
    }

    /**
     * Sets the value of the targetSchema property.
     * 
     * @param value
     *     allowed object is
     *     {@link MappedSchema }
     *     
     */
    public void setTargetSchema(MappedSchema value) {
        this.targetSchema = value;
    }

    /**
     * Gets the value of the taskStatus property.
     * 
     * @return
     *     possible object is
     *     {@link TaskStatus }
     *     
     */
    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    /**
     * Sets the value of the taskStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaskStatus }
     *     
     */
    public void setTaskStatus(TaskStatus value) {
        this.taskStatus = value;
    }

    /**
     * Gets the value of the styles property.
     * 
     * @return
     *     possible object is
     *     {@link Styles }
     *     
     */
    public Styles getStyles() {
        return styles;
    }

    /**
     * Sets the value of the styles property.
     * 
     * @param value
     *     allowed object is
     *     {@link Styles }
     *     
     */
    public void setStyles(Styles value) {
        this.styles = value;
    }

    /**
     * Gets the value of the configSchema property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the configSchema property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConfigSchema().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ConfigSchema }
     * 
     * 
     */
    public List<ConfigSchema> getConfigSchema() {
        if (configSchema == null) {
            configSchema = new ArrayList<ConfigSchema>();
        }
        return this.configSchema;
    }

}
