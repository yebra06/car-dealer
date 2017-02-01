package cardealership.users;

import java.io.Serializable;

/**
 * This class represents a customer at a dealership.
 * 
 * @author Alfredo Yebra Jr.
 */
public class Customer extends User implements Serializable {
    private String phoneNum;
    private int driversLicenseNum;

    /**
     * Customer parameterized constructor. This is the sole constructor.
     * 
     * @param id                Customer id
     * @param firstName         Customers first name.
     * @param lastName          Customers last name.
     * @param phoneNum          Customers phone number.
     * @param driversLicenseNum Customers driver license number.
     */
    public Customer(int id, String firstName, String lastName, String phoneNum, int driversLicenseNum) {
        super(id, firstName, lastName);
        this.phoneNum = phoneNum;
        this.driversLicenseNum = driversLicenseNum;
    }
    
    /**
     * Mutator to set or update users phone number.
     * 
     * @param phoneNum new phone number.
     */
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
    
    /**
     * Mutator to set or update users driver license number.
     * 
     * @param driversLicenseNum drivers license number.
     */
    public void setDriversLicenseNum(int driversLicenseNum) {
        this.driversLicenseNum = driversLicenseNum;
    }

    /**
     * Phone number access.
     * 
     * @return user phone number.
     */
    public String getPhoneNumber() {
        return phoneNum;
    }
    
    /**
     * Access to users driver license.
     * 
     * @return users drivers license.
     */
    public int getDriversLicenseNumber() {
        return driversLicenseNum;
    }
    
    /**
     * Return a customer as a string representation.
     * 
     * @return customer as string.
     */
    public String toString() {
        return super.toString() + phoneNum + " " + driversLicenseNum;
    }
}
