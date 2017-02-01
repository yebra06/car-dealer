package cardealership.vehicles;

/**
 * This class represents a truck vehicle type.
 * 
 * @author Alfredo Yebra Jr.
 */
public class Truck extends Vehicle {
    private final float maxLoadWeight;
    private final float length;
    
    /**
     * Parameterized constructor.
     * <p>
     * @see Vehicle.java
     */
    public Truck(String VIN, String make, String model, int year, 
            int mileage, float price, float maxLoadWeight, float length) {
        super(VIN, make, model, year, mileage, price);
        this.maxLoadWeight = maxLoadWeight;
        this.length = length;
    }
    
    public float getMaxLoadWeight() {
        return maxLoadWeight;
    }
    
    public float getLength() {
        return length;
    }

    public String toString() {
        return super.toString() + length + " " + maxLoadWeight;
    }
}
