/*
 * This class represents a Vehicle object with properties such as name, color, type, year,
 * registration number, and whether the vehicle belongs to a staff member or not.
 * It includes getters and setters for each property and an override for the toString method
 * to provide a formatted description of the vehicle.
 * Author - Rakshit Dhanda 10510199
 * version - jdk 22
 */
import java.time.LocalDateTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

public class Vehicle {

    // Attributes of the vehicle
    private String name;
    private String colour;
    private VehicleType type;
    private Integer year;
    private String registrationNumber;
    private boolean isStaff;
    private LocalDateTime parkingTime;

    // Constructor to initialize all attributes of the Vehicle object
    public Vehicle(String name, String colour, VehicleType type, Integer year, String registrationNumber, boolean isStaff) {
        this.name = name;
        this.colour = colour;
        this.type = type;
        this.year = year;
        this.registrationNumber = registrationNumber;
        this.isStaff = isStaff;
    }

    // Getter and setter methods for the vehicle's color
    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    // Getter and setter methods for the vehicle's name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and setter methods for the vehicle type (e.g., Car, Truck, etc.)
    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    // Getter and setter methods for the manufacturing year of the vehicle
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    // Getter for the vehicle's registration number (no setter, assuming registration number doesn't change)
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    // Getter for whether the vehicle belongs to a staff member
    public boolean isStaff() {
        return isStaff;
    }

    public void setParkingTime(LocalDateTime parkingTime) {
        this.parkingTime = parkingTime;
    }

    public LocalDateTime getParkingTime() {
        return parkingTime;
    }

    public String getParkingDuration() {
        if (parkingTime == null) {
            return "Not parked";
        }
        Duration duration = Duration.between(parkingTime, LocalDateTime.now());
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();
        return String.format("%d hours %d minutes and %d seconds", hours, minutes, seconds);
    }

    public double getParkingFee() {
        if (parkingTime == null) {
            return 0.0;
        }
        Duration duration = Duration.between(parkingTime, LocalDateTime.now());
        long hours = duration.toHours();
        return Math.max(5.0, (hours + 1) * 5.0); // Minimum 1 hour charge
    }

    // Override of the toString method to provide a formatted description of the vehicle
    @Override
    public String toString() {
        String parkingInfo = parkingTime != null ? 
            String.format(" - Parked at: %s, Duration: %s, Fee: $%.2f", 
                parkingTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                getParkingDuration(), getParkingFee()) : 
            "";
        return String.format("%s %s (%s) %d - Reg: %s - %s%s", 
            this.getName(), this.getColour(), this.getType().getDescription(), 
            this.getYear(), this.registrationNumber, (isStaff ? "Staff" : "Visitor"), parkingInfo);
    }

}
