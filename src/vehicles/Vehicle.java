package cardealership.vehicles;

import java.io.Serializable;

/**
 * This class represents a single vehicle that a car dealerships inventory may
 * consist of.
 * 
 * @author Alfredo Yebra Jr.
 */
public abstract class Vehicle implements Serializable {
    private final String vin;
    private final String make;
    private final String model;
    private final int year;
    private final int mileage;
    private final float price;

    /**
     * Parameterized constructor.
     * 
     * @param vin       5 digit <CODE>String</CODE> <CODE>Vehicle</CODE> id number.
     * @param make      Make of vehicle.
     * @param model     Model of vehicle.
     * @param year      Year of vehicle.
     * @param mileage   Miles driven by vehicle used.
     * @param price     Price of vehicle in dollars.
     */
    public Vehicle(String vin, String make, String model, 
            int year, int mileage, float price) {
        this.vin = vin;
        this.make = make;
        this.model = model;
        this.year = year;
        this.mileage = mileage;
        this.price = price;
    }
    
    /**
     * Return vehicle's VIN.
     * 
     * @return a 5 digit <b>{@code String}</b>
     */
    public String getVin() {
        return vin;
    }

    
    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public int getMileage() {
        return mileage;
    }

    /**
     * Return vehicle's current price.
     * 
     * @return <b>{@code float}</b> of vehicle's price in U.S. dollars.
     */
    public float getPrice() {
        return price;
    }
    
    /**
     * Return vehicle object as <code>String</code>.
     * 
     * @return <code>String</code> representation of vehicle.
     */
    public String toString() {
        return String.format("%s %s %s %d %d 1%.2f ", vin, make, model, year, mileage, price);
    }
}
