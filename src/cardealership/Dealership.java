package cardealership;

import cardealership.business.Transaction;
import cardealership.vehicles.Vehicle;
import cardealership.users.User;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the car dealership and implements its processes.
 * 
 * @author Alfredo Yebra Jr.
 */
public class Dealership implements Serializable {
    final private List<Vehicle> inventory;
    final private List<Transaction> transactions;
    final private List<User> users;
    
    /**
     * This default constructor checks if a file named <i>dealership.ser</i>,
     * and de-serialize the serialized file and stores the data found into the
     * private members.
     */
    public Dealership() {
        Dealership dealership = null;
        File data = new File("./dealership.ser");

        if (data.exists()) {
            try {
                FileInputStream fileIn = new FileInputStream("./dealership.ser");
                ObjectInputStream in = new ObjectInputStream(fileIn);
                dealership = (Dealership) in.readObject();
                in.close();
                fileIn.close();
            } catch (java.io.IOException | ClassNotFoundException i) {
                i.printStackTrace(System.out);
            }
        }

        if (dealership != null) {
            this.inventory = dealership.inventory;
            this.transactions = dealership.transactions;
            this.users = dealership.users;
        } else {
            inventory = new ArrayList<>();
            transactions = new ArrayList<>();
            users = new ArrayList<>();
        }
    }
    
    /**
     * This method returns all existing vehicles in dealerships inventory.
     *
     * @return  an unmodifiable list of vehicles in dealerships inventory.
     */
    public List<Vehicle> getVehicles() {
        return Collections.unmodifiableList(inventory);
    }
    
    /**
     * This methods returns all existing transactions in dealerships database.
     *
     * @return  an unmodifiable list of transactions the dealership has made.
     */
    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }
    
    /**
     * This methods returns all existing users in dealerships database.
     *
     * @return  an unmodifiable list of dealership users. This includes customers
     *          and employees.
     */
    public List<User> getUsers() {
        return Collections.unmodifiableList(users);
    }
    
    /**
     * This method adds a vehicle entry to the dealerships database inventory.
     * 
     * @param newVehicle    a new {@code Vehicle} object.
     * @return              <b>{@code true}</b> if vehicle successfully added to db.
     */
    public boolean addVehicle(Vehicle newVehicle) {
        return inventory.add(newVehicle);
    }
    
    /**
     * Add user to dealership users database.
     * 
     * @param newUser   User object to add to database.
     * @return          <b>{@code true}</b> if user is successfully added into database,
     *                  otherwise <b>{@code false}</b>.
     */
    public boolean addUser(User newUser) {
        return users.add(newUser);
    }
    
    /**
     * Add a transaction the dealership has made along with transactions info.
     * 
     * @param newTransaction    new <b>{@code Transaction}</b> object to be added.
     * @return                  <b>{@code true}</b> if transaction entry successfully added
     *                          to dealerships database.
     */
    public boolean addTransaction(Transaction newTransaction) {
        return transactions.add(newTransaction);
    }
    
    /**
     * Remove a car from the dealership inventory.
     *
     * @param vin   Vehicle VIN to lookup and match to be deleted.
     * @return      <b>{@code true}</b> if the car has been removed from inventory.
     */
    public boolean removeCar(String vin) {
        return inventory.removeIf((Vehicle i) -> {
            return i.getVin().equals(vin);
        });
    }
    
    /**
     * Check if car is in inventory by searching for VIN.
     *
     * @param vin   vehicle identification number.
     * @return      <b>{@code true}</b> car is found, otherwise <b>{@code false}</b>
     */
    public boolean hasCarInstock(String vin) {
        return inventory
                .stream()
                .anyMatch(vehicle -> {
                    return vehicle.getVin().contains(vin);
                });
    }
    
    /**
     * Display a list of vehicles between a given min and max price range.
     * 
     * @param min   minimum price in U.S. dollars of users desire.
     * @param max   maximum price in U.S. dollars of users desire.
     * @return      a <b>{@code List}</b> of <b>{@code Vehicle}</b> filtered by price range.
     */
    public List<Vehicle> getVehiclesInPriceRange(float min, float max) {
        return getVehicles()
                .stream()
                .filter(vehicle -> vehicle.getPrice() >= min && vehicle.getPrice() <= max)
                .collect(Collectors.toList());
    }
    
    /**
     * Find a vehicle in the dealerships list of vehicles given a VIN.
     * 
     * @param vin   the VIN that is to be searched for in inventory.
     * @return      new <b>{@code Vehicle}</b> if vehicle found, otherwise <b>{@code null}</b>
     */
    public Vehicle findVehicleByVin(String vin) {
        return getVehicles()
                .stream()
                .filter(vehicle -> vin.equals(vehicle.getVin()))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Search for a user given an id.
     * 
     * @param id    a users id to search for.
     * @return      a new <b>{@code User}</b> if user found, otherwise <b>{@code null}</b>
     */
    public User findUserById(int id) {
        return getUsers()
                .stream()
                .filter(user -> id == user.getId())
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Serialize, save, and write to <i>dealership.ser</i>.
     */
    public void flush() {
        try {
            FileOutputStream fileOut = new FileOutputStream("./dealership.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
        } catch (java.io.IOException i) {
            i.printStackTrace(System.out);
        }
    }
}
