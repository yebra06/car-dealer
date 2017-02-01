package cardealership.vehicles;

public class Passenger extends Vehicle {
    private final String bodyStyle;
    
    public Passenger(String VIN, String make, String model,
            int year, int mileage, float price, String bodyStyle) {
        super(VIN, make, model, year, mileage, price);
        this.bodyStyle = bodyStyle;
    }
    
    public String getBodyStyle() {
        return bodyStyle;
    }
    
    public String toString() {
        return super.toString() + bodyStyle;
    }
}
