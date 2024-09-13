/*
 * This class represents a type of vehicle, characterized by a unique code, description,
 * and the number of seats. It includes getter and setter methods for each property
 * and enforces uppercase formatting for the vehicle type code.
 * author - Rakshit Dhanda 105101199
 * version - jdk 22
 */

public class VehicleType {

    // Attributes of the vehicle type
    private String code;          // Code to uniquely identify the vehicle type (e.g., CAR, TRUCK)
    private String description;   // Description of the vehicle type (e.g., Sedan, SUV)
    private Integer seats;        // Number of seats available in the vehicle type

    // Constructor to initialize the vehicle type's code, description, and number of seats
    public VehicleType(String code, String description, Integer seats) {
        this.code = code;
        this.description = description;
        this.seats = seats;
    }

    // Getter for the vehicle type code, converting it to uppercase for consistency
    public String getCode() {
        return code.toUpperCase();
    }

    // Setter for the vehicle type code
    public void setCode(String code) {
        this.code = code;
    }

    // Getter for the vehicle type description
    public String getDescription() {
        return description;
    }

    // Setter for the vehicle type description
    public void setDescription(String description) {
        this.description = description;
    }

    // Getter for the number of seats in the vehicle
    public Integer getSeats() {
        return seats;
    }

    // Setter for the number of seats in the vehicle
    public void setSeats(Integer seats) {
        this.seats = seats;
    }
}
