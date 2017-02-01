package cardealership.users;

import java.io.Serializable;

/**
 * Abstract user base class.
 * 
 * @author yebra06
 */
public abstract class User implements Serializable {
    private final int id;
    private String firstName;
    private String lastName;

    /**
     * User default constructor.
     */
    public User() {
        id = 0;
        firstName = "";
        lastName = "";
    }

    /**
     * Parameterized constructor.
     * 
     * @param id
     * @param firstName
     * @param lastName 
     */
    public User(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    /**
     * This method returns the users id.
     * 
     * @return user id.
     */
    public int getId() {
        return id;
    }
    
    /**
     * Return the users first name.
     * 
     * @return first name
     */
    public String getFirstName() {
        return firstName;
    }
    
    /**
     * Return users last name.
     * 
     * @return last name
     */
    public String getLastName() {
        return lastName;
    }
    
    /**
     * Access to users full name (first and last name)
     * 
     * @return string concatenation of users first and last name.
     */
    public String getFullName() {
        return firstName + lastName;
    }
    
    /**
     * First name mutator method.
     * 
     * @param firstName users first name.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    /**
     * Last name mutator.
     * 
     * @param lastName users last name.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Make a user object to string.
     * 
     * @return string of user.
     */
    public String toString() {
        return id + " " + firstName + " " + lastName + " ";
    }
    
}