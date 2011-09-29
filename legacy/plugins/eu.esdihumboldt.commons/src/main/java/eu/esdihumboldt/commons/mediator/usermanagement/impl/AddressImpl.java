/*
 * This class was automatically generated with
 * <a href="http://www.castor.org">Castor 1.1.2.1</a>, using an XML
 * Schema.
 * $Id: AddressImpl.java,v 1.1 2007-10-19 10:03:07 pitaeva Exp $
 */

package eu.esdihumboldt.commons.mediator.usermanagement.impl;

/**
 * It describes the address information used in the
 *  HUMBOLDT-System.
 *
 *
 * @version $Revision: 1.1 $ $Date: 2007-10-19 10:03:07 $
 */
public abstract class AddressImpl implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**.
     * Internal choice value storage
     */
    private java.lang.Object _choiceValue;

    /**
     * Field _userAddress.
     */
    private eu.esdihumboldt.commons.mediator.usermanagement.impl.UserAddressImpl _userAddress;

    /**
     * Field _organizationAddress.
     */
    private eu.esdihumboldt.specification.mediator.usermanagement.OrganizationAddress _organizationAddress;


      //----------------/
     //- Constructors -/
    //----------------/

    public AddressImpl() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'choiceValue'. The field
     * 'choiceValue' has the following description: Internal choice
     * value storage
     *
     * @return the value of field 'ChoiceValue'.
     */
    public java.lang.Object getChoiceValue(
    ) {
        return this._choiceValue;
    }

    /**
     * Returns the value of field 'organizationAddress'.
     * 
     * @return the value of field 'OrganizationAddress'.
     */
    public eu.esdihumboldt.specification.mediator.usermanagement.OrganizationAddress getOrganizationAddress(
    ) {
        return this._organizationAddress;
    }

    /**
     * Returns the value of field 'userAddress'.
     * 
     * @return the value of field 'UserAddress'.
     */
    public eu.esdihumboldt.commons.mediator.usermanagement.impl.UserAddressImpl getUserAddress(
    ) {
        return this._userAddress;
    }

    /**
     * Method isValid.
     * 
     * @return true if this object is valid according to the schema
     */
    public boolean isValid(
    ) {
        try {
            validate();
        } catch (org.exolab.castor.xml.ValidationException vex) {
            return false;
        }
        return true;
    }

    /**
     * Sets the value of field 'organizationAddress'.
     * 
     * @param organizationAddress the value of field
     * 'organizationAddress'.
     */
    public void setOrganizationAddress(
            final eu.esdihumboldt.specification.mediator.usermanagement.OrganizationAddress organizationAddress) {
        this._organizationAddress = organizationAddress;
        this._choiceValue = organizationAddress;
    }

    /**
     * Sets the value of field 'userAddress'.
     * 
     * @param userAddress the value of field 'userAddress'.
     */
    public void setUserAddress(
            final eu.esdihumboldt.commons.mediator.usermanagement.impl.UserAddressImpl userAddress) {
        this._userAddress = userAddress;
        this._choiceValue = userAddress;
    }

    /**
     * 
     * 
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     */
    public void validate(
    )
    throws org.exolab.castor.xml.ValidationException {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    }

}