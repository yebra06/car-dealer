package cardealership.users;

public class Employee extends User {
    private float monthlySalary;
    private int bankAccountNum;
    
    public Employee(int id, String firstName, String lastName, float monthlySalary, int bankAccountNum) {
        super(id, firstName, lastName);
        this.monthlySalary = monthlySalary;
        this.bankAccountNum = bankAccountNum;
    }
    
    public void setBankAccount(int bankAccountNum) {
        this.bankAccountNum = bankAccountNum;
    }
    
    public void setMonthlySalary(float monthlySalary) {
        this.monthlySalary = monthlySalary;
    }
    
    public float getSalary() {
        return monthlySalary;
    }
    
    public int getBankAccountNumber() {
        return bankAccountNum;
    }
    
    public String toString() {
        return super.toString() + monthlySalary + " " + bankAccountNum;
    }    
}
