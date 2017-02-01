package cardealership.vehicles;

public class Motorcycle extends Vehicle {
    private final int engineDisplacement;
    private final String type;

    public Motorcycle(String VIN, String make, String model, int year, 
            int mileage, float price, String type, int engineDisplacement) {
        super(VIN, make, model, year, mileage, price);
        this.engineDisplacement = engineDisplacement;
        this.type = type;
    }
    
    public String getType() {
        return type;
    }
    
    public int getEngineDisplacement() {
        return engineDisplacement;
    }
    
    public String toString() {
        return super.toString() + type + " " + engineDisplacement;
    }
}
