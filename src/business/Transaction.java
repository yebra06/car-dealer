package cardealership.business;

import cardealership.users.User;
import cardealership.vehicles.Vehicle;
import java.util.Date;

/**
 * This class represents a transaction for a car dealership. A single transaction
 * consists of an employee, customer, vehicle, finalSalePrice, and date of
 * transaction.
 * 
 * @author Alfredo Yebra Jr.
 */
public class Transaction {
    private final User customer;
    private final User employee;
    private final Vehicle vehicle;
    private final float finalSalePrice;
    private final Date date;

    /**
     * Transaction parameterized constructor. This is the <b>{@code Transaction}</b>
     * classes sole constructor.
     * 
     * @param customer          <b>{@code Customer}</b> making the purchase.
     * @param employee          <b>{@code Employee}</b> making the sell.
     * @param vehicle           <b>{@code Vehicle}</b> being sold.
     * @param finalSalePrice    the final price of the vehicle sold in U.S. dollars.
     * @param date              the date the vehicle was sold.
     */
    public Transaction(User customer, User employee, Vehicle vehicle, float finalSalePrice, Date date) {
        this.customer = customer;
        this.employee = employee;
        this.vehicle = vehicle;
        this.finalSalePrice = finalSalePrice;
        this.date = date;
    }
    
    public String toString() {
        return "Customer: " + customer.getFirstName() + customer.getLastName() + " Employee: " + employee.getFirstName() + employee.getLastName() + " Vin: " + vehicle.getVin() + " Sold for: " + finalSalePrice;
    }
}