package cardealership;

import cardealership.business.Transaction;
import cardealership.users.Customer;
import cardealership.users.Employee;
import cardealership.vehicles.Vehicle;
import cardealership.vehicles.Passenger;
import cardealership.vehicles.Motorcycle;
import cardealership.vehicles.Truck;
import cardealership.users.User;

import java.util.List;
import java.util.Scanner;
import java.util.Date;

/**
 * Driver program for a car dealership.
 * 
 * @author Alfredo Yebra Jr.
 * @version 2.0
 * @since October 3, 2016
 */
public class DealershipMain {
    static final private Scanner input  = new Scanner(System.in);
    
    public static void main(String[] args) {
        int choice = 0;
        Dealership dealership = new Dealership();

        while (choice != 11) {
            showMenu();
            choice = getChoice();
            
            switch (choice) {
                case 1:
                    showVehicles(dealership.getVehicles());
                    break;
                case 2:
                    System.out.println(dealership.addVehicle(newVehicle()) 
                            ? "Vehicle added to inventory" : "Vehicle not added to inventory.");
                    break;
                case 3:
                    System.out.println(dealership.removeCar(inputVIN())
                            ? "Vehicle deleted." : "Vehicle not found, inventory unchanged.");
                    break;
                case 4:
                    System.out.println("Enter VIN of vehicle to lookup.");
                    System.out.println(dealership.hasCarInstock(inputVIN())
                            ? "Vehicle found!" : "Vehicle not found.");
                    break;
                case 5:
                    System.out.print("Enter minimum price range: ");
                    float minPrice = inputPrice();
                    System.out.print("Enter maximum price range: ");
                    float maxPrice = inputPrice();
                    
                    System.out.printf("Vehicles priced between $%1$.2f and $%1$.2f:\n", minPrice, maxPrice);
                    showVehicles(dealership.getVehiclesInPriceRange(minPrice, maxPrice));
                    break;
                case 6:
                    displayUsers(dealership.getUsers());
                    break;
                case 7:
                    System.out.println(
                            dealership.addUser(newUser(dealership.getUsers().size() + 1))
                                    ? "New user added." : "Unable to add new user.");
                    break;
                case 8:
                    System.out.print("Enter id of user to lookup: ");
                    User user = dealership.findUserById(inputUserId());
                    
                    if (user != null) {
                        System.out.print("Enter new user first name: ");
                        String newFirstName = input.nextLine();
                        System.out.print("Enter user last name: ");
                        String newLastName = input.nextLine();
                        user.setFirstName(newFirstName);
                        user.setLastName(newLastName);
                        System.out.println("Found user!\n" + user.toString());
                        System.out.println("");
                    }
                    
                    System.out.println(user.toString());
                    break;
                case 9:
                    /* Sell vehicle.
                    This case retrieves the employee making the sell, and the customer
                    who is purchasing the car. If either the user or the employee is not
                    found by their ids, the user is prompted to try again or quit.
                    If vehicle to be bought is found a transaction is made and
                    vehicle is deleted from database.*/
                    
                    System.out.print("Enter employee id: ");
                    User employee = dealership.findUserById(inputUserId());
                    
                    while (employee == null) {
                        System.out.println("Employee not found.");
                        System.out.print("Enter employee id: ");
                        employee = dealership.findUserById(inputUserId());
                    }
                    
                    System.out.print("Enter customer id or enter 0 to add new customer: ");                    
                    int id = inputUserId();
                    User customer = id == 0 ? newUser(dealership.getUsers().size() + 1) : dealership.findUserById(id);
                    
                    while (customer == null) {
                        System.out.println("Customer not found.");
                        System.out.print("Enter customer id: ");
                        id = inputUserId();
                        customer = dealership.findUserById(id);
                    }
                    
                    String vin = inputVIN();
                    Vehicle vehicle = dealership.findVehicleByVin(vin);

                    while (vehicle == null) {
                        System.out.println("Vehicle not found.");
                        vin = inputVIN();
                        vehicle = dealership.findVehicleByVin(vin);
                    }

                    System.out.println("Vehicle found!\n" + vehicle.toString());

                    if (yesOrNo("Are your sure you want to sell and remove vehicle? (y/n)").startsWith("y")) {
                        dealership.addTransaction(new Transaction(customer, employee, vehicle, 2323.32f, new Date()));
                        dealership.removeCar(vin);
                        System.out.println("Car sold! Nice work!");
                    } else {
                        System.out.println("Transaction cancelled.");
                    }
                    break;
                case 10:
                    dealership
                            .getTransactions()
                            .stream()
                            .map(Transaction::toString)
                            .forEach(System.out::println);
                    break;
                case 11:
                    System.out.println("See ya later!");
                    System.out.println("Have a great day! :)");
                    break;
                default:
                    System.out.println();
                    System.out.println("Unknown menu option.");
                    System.out.println("Try again.");
                    break;
            }
        }
        
        input.close();
        dealership.flush();
    }
    
    /**
     * Prompt a user with a yes or no question and then get their answer.
     * 
     * @param yesOrNoPrompt a prompt that the user will read and respond with yes or no.
     * @return              a <b>{@code String}</b> value of <i>yes</i> or <i>no</i>.
     */
    public static String yesOrNo(String yesOrNoPrompt) {
        System.out.print(yesOrNoPrompt + "\nEnter choice: ");
        String yesOrNo = input.nextLine();
        
        while (!yesOrNo.matches("(?i)y|yes|n|no")) {
            System.out.print("Enter choice: ");
            yesOrNo = input.nextLine();
        }
        
        return yesOrNo;
    }

    /**
     * Display menu options to user.
     */
    public static void showMenu() {
        System.out.println();
        System.out.println("Car Dealership Menu");
        System.out.println("1. Show all existing vehicle records in the database.");
        System.out.println("2. Add a new vehicle record to the database.");
        System.out.println("3. Delete a vehicle record from a database.");
        System.out.println("4. Search for a vehicle (given its VIN).");
        System.out.println("5. Show a list of vehicles within a given price range.");
        System.out.println("6. Show list of users in the database.");
        System.out.println("7. Add a new user to the database.");
        System.out.println("8. Update user info (given their id).");
        System.out.println("9. Sell a vehicle.");
        System.out.println("10. Show a list of completed sale transactions.");
        System.out.println("11. Exit program.");
        System.out.println();
    }

    /**
     * Get users menu choice. Continue to ask user for another choice if choice
     * is not on the menu.
     * 
     * @return an item from menu options.
     */
    public static int getChoice() {
        boolean isValid = false;
        int choice = 0;

        while (!isValid) {
            System.out.print("Enter your choice: ");
            if (input.hasNextInt()) {
                choice = input.nextInt();
                while (choice < 0) {
                    System.out.println();
                    System.out.println("Invalid menu option.");
                    System.out.println("Please re-enter valid menu option.");
                    System.out.print("Enter your choice: ");
                    choice = input.nextInt();
                }
                isValid = true;
            } else {
                isValid = false;
                input.next();
                System.out.println("Invalid menu option.");
                System.out.println("Menu option must be an integer value from menu.");
            }
        }
        
        return choice;
    }
    
    /**
     * Function to create a new <b>{@code Vehicle}</b>.
     * 
     * @return a new <b>{@code Vehicle}</b>.
     */
    public static Vehicle newVehicle() {
        String vehicleType = input.nextLine();
        while (!vehicleType.matches ("(?i)motorcycle|truck|passenger")) {
            System.out.println("Enter one of the following vehicle types\nmotorcycle\tpassenger\ttruck");
            System.out.print("Enter vehicle type: ");
            vehicleType = input.nextLine();
        }
        
        System.out.print("Enter vehicles VIN: ");
        String VIN = inputVIN();
        String make = inputMake();
        String model = inputModel();
        int year = inputYear();
        int mileage = inputMileage();
        float price = inputPrice();
        Vehicle newVehicle = null;
        
        switch (vehicleType.toUpperCase()) {
            case "PASSENGER":
                input.nextLine();
                System.out.print("Enter body style of vehicle: ");
                String bodyStyle = input.nextLine();
                newVehicle = new Passenger(VIN, make, model, year, mileage, price, bodyStyle);
                break;
            case "MOTORCYCLE":
                int engineDisplacement = inputEngineDisplacement();
                String typeOfMotorcycle = inputMotorcycleType();
                newVehicle = new Motorcycle(VIN, make, model, year, mileage, price, typeOfMotorcycle, engineDisplacement);
                break;
            case "TRUCK":
                float length = inputTruckLength();
                float maxLoadWeight = inputMaxLoadWeight();
                newVehicle = new Truck(VIN, make, model, year, mileage, price, maxLoadWeight, length);
                break;
        }
        
        return newVehicle;
    }
    
    /**
     * Create a new user to operate on.
     * 
     * @param id value to assign to user.
     * @return a new <b>{@code User}</b>.
     */
    public static User newUser(int id) {
        String userType = input.nextLine();
        
        while (!userType.matches("(?i)customer|employee")) {
            System.out.println("The following user types are available:");
            System.out.println("customer\temployee");
            System.out.print("Enter user type: ");
            userType = input.nextLine();
        }

        System.out.print("Enter first name: ");
        String firstName = input.nextLine();
        System.out.print("Enter last name: ");
        String lastName = input.nextLine();

        User newUser = null;

        switch (userType.toUpperCase()) {
            case "CUSTOMER":
                System.out.print("Enter customer phone number: ");
                String phoneNum = input.nextLine();
                int driverLicenseNum = inputDriverLicense();
                newUser = new Customer(id, firstName, lastName, phoneNum, driverLicenseNum);
                break;
            case "EMPLOYEE":
                float monthlySalary = inputMonthlySalary();
                System.out.print("Enter employee's bank account number: ");
                int bankAccountNum = input.nextInt();
                newUser = new Employee(id, firstName, lastName, monthlySalary, bankAccountNum);
                break;
        }

        return newUser;
    }
        
    public static float inputMonthlySalary() {
        float salary = -1f;
        boolean isValid = false;

        while (!isValid) {
            System.out.print("Enter employees monthly salary: ");
            if (input.hasNextFloat()) {
                salary = input.nextFloat();
                while (salary < 0f) {
                    System.out.println("Enter a monthly salary greater than 0.");
                    System.out.print("Enter monthly salary: ");
                    salary = input.nextFloat();
                }
                isValid = true;
            } else {
                System.out.println("Monthly salary must be a positive floating point value.");
                isValid = false;
                input.next();
            }
        }
        return salary;
    }
    
    public static String inputMotorcycleType() {
        input.nextLine();
        System.out.print("Enter motorcycle type: ");
        String motorcycleType = input.nextLine();

        while (motorcycleType.matches(".*\\s+.*")) {
            System.out.println("Motorcycle type may not contain spaces.");
            System.out.print("Enter motorcycle type: ");
            motorcycleType = input.nextLine();
        }

        return motorcycleType;
    }
    
    public static int inputEngineDisplacement() {
        int engineDisplacement = 0;
        boolean isValid = false;

        while (!isValid) {
            System.out.print("Enter motorcycle engine displacement: ");
            if (input.hasNextInt()) {
                engineDisplacement = input.nextInt();
                while (engineDisplacement <= 0) {
                    System.out.println("Motorcycle's engine displacement must greater than 0.");
                    System.out.print("Enter motorcycle's engine displacement: ");
                    input.nextInt();
                }
                isValid = true;
            } else {
                isValid = false;
                input.next();
                System.out.println("Invalid engine displacement.");
                System.out.println("Engine displacement must be a positive value.");
            }
        }

        return engineDisplacement;
    }

    /**
     * Prompt the user to input their drivers license, and get their drivers license
     * through keyboard input.
     * 
     * @return customer's drivers license.
     */
    public static int inputDriverLicense() {
        int driverLicenseNum = -1;
        boolean isValid = false;
        
        while (!isValid) {
            System.out.print("Enter customers driver license: ");
            if (input.hasNextInt()) {
                driverLicenseNum = input.nextInt();
                while (driverLicenseNum < 0) {
                    System.out.println("License number must be a positive integer value.");
                    System.out.print("Enter driver license number: ");
                    driverLicenseNum = input.nextInt();
                }
                isValid = true;
            } else {
                System.out.println("Driver license number must be a positive integer value.");
                isValid = false;
                input.next();
            }
        }
        return driverLicenseNum;
    }
    
    /**
     * Prompt user to input id and get id through keyboard input.
     * 
     * @return user id.
     */
    public static int inputUserId() {
        int id = -1;
        boolean isValid = false;

        while (!isValid) {
            if (input.hasNextInt()) {
                id = input.nextInt();
                while (id < 0) {
                    System.out.println("Invalid user id.");
                    System.out.println("id must be a positive integer value.");
                    System.out.print("Enter valid user id: ");
                    id = input.nextInt();
                }
                isValid = true;
            } else {
                isValid = false;
                System.out.println("Invalid user id.");
                System.out.println("User id must be an integer value.");
                input.next();
            }
        }

        return id;
    }
    
    /**
     * Get vehicles vin from user.
     * 
     * @return vehicle vin.
     */
    public static String inputVIN() {
        String VIN = input.nextLine();
        
        while (VIN.length() != 5) {
            System.out.print("Enter vehicles 5 digit vin: ");
            VIN = input.nextLine();
        }
        
        return VIN;
    }
    
    /**
     * Input vehicle make from keyboard input.
     * 
     * @return vehicles make.
     */
    public static String inputMake() {
        System.out.print("Enter vehicle make: ");
        String make = input.nextLine();
        
        while (make.matches(".*\\s+.*")) {
            System.out.println("Vehicle make must not contain any space.");
            System.out.print("Enter vehicle make: ");
            make = input.nextLine();
        }
        
        return make;
    }

    /**
     * Input vehicles model.
     * 
     * @return vehicles model.
     */
    public static String inputModel() {
        System.out.print("Enter vehicle model: ");
        String model = input.nextLine();

        while (model.matches(".*\\s+.*")) {
            System.out.println("Vehicle make must not contain any space.");
            System.out.print("Enter vehicle make: ");
            model = input.nextLine();
        }
        
        return model;
    }

    /**
     * Get the year the vehicle was manufactured.
     *
     * @return vehicles year.
     */
    public static int inputYear() {
        int year = 0;
        boolean isValid = false;

        while (!isValid) {
            System.out.print("Enter vehicle year: ");
            if (input.hasNextInt()) {
                year = input.nextInt();
                while (year < 1900) {
                    System.out.println("Invalid vehicle year.");
                    System.out.println("Year must be a positive integer value between 1900 and present.");
                    System.out.print("Enter valid vehicle year: ");
                    year = input.nextInt();
                }
                isValid = true;
            } else {
                isValid = false;
                System.out.println("Invalid vehicle year.");
                System.out.println("Year must be an integer value between 1900 and present");
                input.next();
            }
        }

        return year;
    }

    /**
     * Input the total miles traveled on a vehicle that will be bought or sold.
     * 
     * @return number of miles.
     */
    public static int inputMileage() {
        int mileage = -1;
        boolean isValid = false;

        while (!isValid) {
            System.out.print("Enter vehicle mileage: ");
            if (input.hasNextInt()) {
                mileage = input.nextInt();
                
                while (mileage < 0) {
                    System.out.println("Vehicle's mileage must be a positive integer.");
                    input.nextInt();
                }
                isValid = true;
            } else {
                isValid = false;
                input.next();
                System.out.println("Invalid vehicle mileage.");
                System.out.println("Vehicle mileage must be a positive integer value.");
            }
        }

        return mileage;
    }

    /**
     * Get a price value in U.S. dollars or vehicle or sale price.
     * 
     * @return {@code float} price in U.S. dollars.
     */
    public static float inputPrice() {
        float price = -1f;
        boolean isValid = false;

        while (!isValid && price < 0) {
            System.out.print("Enter vehicle price: ");
            
            if (input.hasNextFloat()) {
                price = input.nextFloat();
                isValid = true;
            } else {
                isValid = false;
                input.next();
                System.out.println("Invalid vehicle price.");
                System.out.println("Vehicle price must be a positive floating point value.");
            }
        }

        return price;
    }
    
    /**
     * The total length of a truck in ft.
     * 
     * @return a length in ft.
     */
    public static float inputTruckLength() {
        float truckLength = 0f;
        boolean isValid = false;

        while (!isValid) {
            System.out.print("Enter truck lenght in feet: ");
            if (input.hasNextFloat()) {
                truckLength = input.nextFloat();
                while (truckLength < 1) {
                    System.out.println("Invalid truck length.");
                    System.out.println("Truck length must be a positive floating point value.");
                    System.out.print("Enter valid truck length in feet: ");
                    truckLength = input.nextFloat();
                }
                isValid = true;
            } else {
                isValid = false;
                input.next();
                System.out.println("Invalid truck length.");
                System.out.println("Truck's length must be a positive floating point value.");
            }
        }

        return truckLength;
    }

    /**
     * Get a trucks maximum weight it can load in its bed in pounds.
     * 
     * @return a weight in pounds.
     */
    public static float inputMaxLoadWeight() {
        float maxLoadWeight = 0f;
        boolean isValid;

        do {
            System.out.print("Enter trucks max load weight: ");
            if (input.hasNextFloat()) {
                maxLoadWeight = input.nextFloat();
                while (maxLoadWeight < 0) {
                    System.out.println("Invalid max load weight.");
                    System.out.println("Trucks max load weight must be a positive floating point value.");
                    System.out.print("Enter valid max load weight: ");
                    maxLoadWeight = input.nextFloat();
                }
                isValid = true;
            } else {
                isValid = false;
                input.next();
                System.out.println("Invalid max load weight.");
                System.out.println("Max load weight must be a positive floating point value.");
            }
        } while (!isValid);

        return maxLoadWeight;
    }
    
    /**
     * This method displays a list of all the vehicles in the dealerships inventory.
     * <p>
     * Note: The variable v is short for vehicle.
     * 
     * @param vehicles a <b>{@code List}</b> of all the dealerships vehicles.
     */
    public static void showVehicles(List<Vehicle> vehicles) {
        String formatter;

        for (Vehicle v : vehicles) {
            if (v instanceof Motorcycle) {
                formatter = "| %-6s | %-15s | %-15s | %-5d | %-9d | $%-14.2f | %-14d | %-10s |%n";
                System.out.format("+--------+-----------------+-----------------+-------+-----------+-----------------+----------------+------------+%n");
                System.out.printf("| VIN    | Make            | Model           | Year  | Mileage   | Price           | Engine Disp    | Type       |%n");
                System.out.format("+--------+-----------------+-----------------+-------+-----------+-----------------+----------------+------------+%n");
                System.out.format(formatter, v.getVin(), v.getMake(), v.getModel(), v.getYear(), v.getMileage(), v.getPrice(), ((Motorcycle) v).getEngineDisplacement(), ((Motorcycle) v).getType());
                System.out.format("+--------+-----------------+-----------------+-------+-----------+-----------------+----------------+------------+%n");
            }
            
            if (v instanceof Passenger) {
                formatter = "| %-6s | %-15s | %-15s | %-5d | %-9d | $%-14.2f | %-12s |%n";
                System.out.format("+--------+-----------------+-----------------+-------+-----------+-----------------+--------------+%n");
                System.out.printf("| VIN    | Make            | Model           | Year  | Mileage   | Price           | Body Style   |%n");
                System.out.format("+--------+-----------------+-----------------+-------+-----------+-----------------+--------------+%n");
                System.out.format(formatter, v.getVin(), v.getMake(), v.getModel(), v.getYear(), v.getMileage(), v.getPrice(), ((Passenger) v).getBodyStyle());
                System.out.format("+--------+-----------------+-----------------+-------+-----------+-----------------+--------------+%n");
            }
            
            if (v instanceof Truck) {
                formatter = "| %-6s | %-15s | %-15s | %-5d | %-9d | $%-14.2f | %-10.2f | %-15.2f |%n";
                System.out.format("+--------+-----------------+-----------------+-------+-----------+-----------------+------------+-----------------+%n");
                System.out.printf("| VIN    | Make            | Model           | Year  | Mileage   | Price           | Length     | Max Load Weight |%n");
                System.out.format("+--------+-----------------+-----------------+-------+-----------+-----------------+------------+-----------------+%n");
                System.out.format(formatter, v.getVin(), v.getMake(), v.getModel(), v.getYear(), v.getMileage(), v.getPrice(), ((Truck) v).getLength(), ((Truck) v).getMaxLoadWeight());
                System.out.format("+--------+-----------------+-----------------+-------+-----------+-----------------+------------+-----------------+%n");
            }
            
            System.out.println();
        }
    }
    
    /**
     * Display a table of dealership customers including employees and customers.
     * 
     * @param users <b>{@code List}</b> of all the dealerships users.
     */
    public static void displayUsers(List<User> users) {
        String formatter;
        
        for (User user : users) {
            if (user instanceof Employee) {
                formatter = "| %-6d | %-17s | %-15s | %-10s | %-14d |%n";
                System.out.format("+--------+-------------------+-----------------+------------+----------------+%n");
                System.out.printf("| ID     | First             | Last            | Salary     | Bank Accnt     |%n");
                System.out.format("+--------+-------------------+-----------------+------------+----------------+%n");
                System.out.format(formatter, user.getId(), user.getFirstName(), user.getLastName(), ((Employee) user).getSalary(), ((Employee) user).getBankAccountNumber());
                System.out.format("+--------+-------------------+-----------------+------------+----------------+%n");
            }
            
            if (user instanceof Customer) {
                formatter = "| %-6d | %-17s | %-15s | %-17s | %-14d |%n";
                System.out.format("+--------+-------------------+-----------------+-------------------+----------------+%n");
                System.out.printf("| ID     | First             | Last            | Phone             | Driver License |%n");
                System.out.format("+--------+-------------------+-----------------+-------------------+----------------+%n");
                System.out.format(formatter, user.getId(), user.getFirstName(), user.getLastName(), ((Customer) user).getPhoneNumber(), ((Customer) user).getDriversLicenseNumber());
                System.out.format("+--------+-------------------+-----------------+-------------------+----------------+%n");
            }
            
            System.out.println();
        }
    }
}
