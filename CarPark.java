/*
 * This class represents a car park with parking slots for staff and visitors.
 * It supports adding, deleting, and listing parking slots, as well as parking, finding, and removing vehicles.
 * The car park is initialized with a specified number of staff and visitor parking slots.
 * Author - Rakshit dhanda 105101199
 * version - jdk 22
 */

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CarPark {

    // List to store all parking slots in the car park
    private ArrayList<ParkingSlot> slots;

    // Constructor that initializes the car park with staff and visitor parking slots
    public CarPark(int staffSlots, int visitorSlots) {
        slots = new ArrayList<>();

        // Generate parking slots for staff, labeled S01, S02, etc.
        for (int i = 1; i <= staffSlots; i++) {
            slots.add(new ParkingSlot("S" + String.format("%02d", i), "STAFF"));
        }
        // Generate parking slots for visitors, labeled V01, V02, etc.
        for (int i = 1; i <= visitorSlots; i++) {
            slots.add(new ParkingSlot("V" + String.format("%02d", i), "VISITOR"));
        }
    }

    // Adds a new parking slot if it does not already exist and the ID is in the correct format
    public boolean addSlot(ParkingSlot slot) {
        if (findSlotById(slot.getId()) == null && slot.getId().matches("^[A-Z]{1}[0-9]{2}$")) {
            slots.add(slot);
            return true;
        }
        System.out.println("Slot ID already exists or incorrect format.");
        return false;
    }

    // Deletes a parking slot if it exists and is unoccupied
    public boolean deleteSlot(String slotId) {
        ParkingSlot slot = findSlotById(slotId);
        if (slot != null && !slot.isOccupied()) {
            slots.remove(slot);
            return true;
        }
        return false;
    }

    // Lists all parking slots in the car park
    public void listSlots() {
        for (ParkingSlot slot : slots) {
            System.out.println(slot);
            if (slot.isOccupied()) {
                Vehicle vehicle = slot.getVehicle();
                System.out.println("  Parking Duration: " + vehicle.getParkingDuration());
                System.out.println("  Parking Fee: $" + String.format("%.2f", vehicle.getParkingFee()));
            }
        }
    }

    // Deletes all unoccupied parking slots from the car park
    public void deleteUnoccupiedSlots() {
        slots.removeIf(slot -> !slot.isOccupied());
    }

    // Parks a vehicle in a specific parking slot if the slot is compatible and unoccupied
    public boolean parkVehicle(String slotId, Vehicle vehicle) {
        ParkingSlot slot = findSlotById(slotId);
        if (slot != null && !slot.isOccupied() && slot.isSlotCompatible(vehicle)) {
            vehicle.setParkingTime(LocalDateTime.now());
            slot.parkVehicle(vehicle);
            System.out.println("Vehicle parked at: " + vehicle.getParkingTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            return true;
        }
        return false;
    }


    // Finds a vehicle by its registration number and prints its slot and owner's name
    public void findVehicle(String regNo) {
        for (ParkingSlot slot : slots) {
            if (slot.isOccupied() && slot.getVehicle().getRegistrationNumber().equals(regNo)) {
                Vehicle vehicle = slot.getVehicle();
                System.out.println("Vehicle found in slot: " + slot.getId());
                System.out.println("Owner: " + vehicle.getName());
                System.out.println("Parking Time: " + vehicle.getParkingTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                System.out.println("Parking Duration: " + vehicle.getParkingDuration());
                System.out.println("Parking Fee: $" + String.format("%.2f", vehicle.getParkingFee()));
                return;
            }
        }
        System.out.println("Vehicle not found.");
    }

    // Removes a vehicle from the car park by its registration number
    public boolean removeVehicle(String regNo) {
        for (ParkingSlot slot : slots) {
            if (slot.isOccupied() && slot.getVehicle().getRegistrationNumber().equals(regNo)) {
                slot.removeVehicle();
                return true;
            }
        }
        return false;
    }

    // Helper methods

    // Checks if a parking slot exists based on its ID
    public boolean isSlotExists(String slotId) {
        return findSlotById(slotId) != null;
    }

    // Checks if a parking slot is occupied based on its ID
    public boolean isSlotOccupied(String slotId) {
        ParkingSlot slot = findSlotById(slotId);
        return slot != null && slot.isOccupied();
    }

    // Checks if a specific slot is a staff slot
    public boolean isStaffSlot(String slotId) {
        ParkingSlot slot = findSlotById(slotId);
        return slot != null && slot.isStaffSlot();
    }

    // Finds and returns a parking slot by its ID, or null if not found
    private ParkingSlot findSlotById(String slotId) {
        for (ParkingSlot slot : slots) {
            if (slot.getId().equals(slotId)) {
                return slot;
            }
        }
        return null;
    }
}
