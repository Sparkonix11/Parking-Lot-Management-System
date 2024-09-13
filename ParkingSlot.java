/*
 * This class represents a parking slot within a car park. Each slot has an ID, a type 
 * (either STAFF or VISITOR), and can either be occupied by a vehicle or unoccupied.
 * The class provides methods to park and remove vehicles, check compatibility with vehicle types, 
 * and retrieve slot and vehicle information.
 *  author - Rakshit Dhanda 105101199
 *  version - jdk 22
 */

public class ParkingSlot {

    // Attributes of the parking slot
    private String id;          // Unique ID for the parking slot (e.g., S01 for staff, V01 for visitors)
    private Vehicle vehicle;    // The vehicle currently parked in the slot, or null if unoccupied
    private String type;        // Type of slot: "STAFF" or "VISITOR"

    // Constructor to initialize the parking slot with its ID and type
    public ParkingSlot(String id, String type) {
        // Validate the ID format (e.g., S01 or V01) using a regex
        if (!id.matches("^[A-Z]{1}[0-9]{2}$")) {
            throw new IllegalArgumentException("Incorrect format for slot ID, please enter the correct slot format e.g. S01");
        }
        // Validate the type of the slot as either "STAFF" or "VISITOR"
        if (!type.equalsIgnoreCase("STAFF") && !type.equalsIgnoreCase("VISITOR")) {
            throw new IllegalArgumentException("Incorrect slot type. Please enter either 'STAFF' or 'VISITOR'.");
        }
        this.id = id;
        this.type = type.toUpperCase(); // Ensure the type is stored consistently in uppercase
        this.vehicle = null; // Slot is initialized as unoccupied
    }

    // Getter for the slot ID
    public String getId() {
        return id;
    }

    // Checks if the slot is currently occupied by a vehicle
    public boolean isOccupied() {
        return vehicle != null;
    }

    // Getter for the vehicle parked in the slot, returns null if the slot is unoccupied
    public Vehicle getVehicle() {
        return vehicle;
    }

    // Parks a vehicle in the slot if the slot is unoccupied and compatible with the vehicle type
    public void parkVehicle(Vehicle vehicle) {
        if (vehicle != null && !isOccupied() && isSlotCompatible(vehicle)) {
            this.vehicle = vehicle; // Assign the vehicle to the slot
        } else {
            throw new IllegalStateException("Slot is either occupied or incompatible with the vehicle.");
        }
    }

    // Removes the vehicle from the slot, throws an exception if the slot is already empty
    public void removeVehicle() {
        if (isOccupied()) {
            this.vehicle = null; // Free the slot
        } else {
            throw new IllegalStateException("Slot is not occupied.");
        }
    }

    // Checks if the slot is compatible with the vehicle (i.e., staff vehicles in staff slots, visitors in visitor slots)
    public boolean isSlotCompatible(Vehicle vehicle) {
        return (this.type.equals("STAFF") && vehicle.isStaff()) || (this.type.equals("VISITOR") && !vehicle.isStaff());
    }

    // Returns a string representation of the parking slot, including its ID, type, and occupancy status
    @Override
    public String toString() {
        String slotInfo = String.format("Slot ID: %s, Type: %s, ", id, type);
        if (isOccupied()) {
            slotInfo += String.format("Occupied by %s (Owner: %s)", vehicle.getRegistrationNumber(), vehicle.getName());
        } else {
            slotInfo += "Unoccupied";
        }
        return slotInfo;
    }

    // Additional helper methods

    // Alias for checking if the slot is occupied
    public boolean isSlotOccupied() {
        return isOccupied();
    }

    // Checks if the given slot ID matches the current slot's ID
    public boolean isSlotExists(String slotId) {
        return this.id.equals(slotId);
    }

    // Checks if the slot is a staff slot
    public boolean isStaffSlot() {
        return this.type.equals("STAFF");
    }

    // Getter for the slot type (STAFF or VISITOR)
    public String getType() {
        return type;
    }

    // Manually sets the vehicle for this slot (useful for testing or management purposes)
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}
