/*
 * This file contains a simple command-line vehicle parking system application.
 * Users can manage parking slots for staff and visitors, park vehicles, 
 * and search or remove parked vehicles. The system ensures correct slot and vehicle details.
 * Author- Rakshit Dhanda 105101199
 * Version- Jdk 22
 */

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class VehicleHireApp {

    public static void main(String[] args) {
        // Initialize a scanner for user input
        Scanner sc = new Scanner(System.in);

        // Get number of staff and visitor parking slots from user input
        int staffSlots = getValidIntegerInput(sc, "Enter the number of parking slots for staff: ");
        int visitorSlots = getValidIntegerInput(sc, "Enter the number of parking slots for visitors: ");

        // Create a new car park object with the specified number of staff and visitor slots
        CarPark carPark = new CarPark(staffSlots, visitorSlots);

        // Welcome message for users
        System.out.println("Welcome to the Best Car Parking System!");

        int option;

        // Loop to display menu options and handle user input until the user selects the exit option
        do {
            displayMenu(); // Display the menu of options
            option = getValidIntegerInput(sc, "Please select an option (1-8): ");

            // Process the selected menu option
            switch (option) {
                case 1:
                    addParkingSlot(sc, carPark); // Add a new parking slot
                    break;
                case 2:
                    deleteParkingSlot(sc, carPark); // Delete an existing parking slot
                    break;
                case 3:
                    carPark.listSlots(); // List all parking slots
                    break;
                case 4:
                    carPark.deleteUnoccupiedSlots(); // Delete unoccupied parking slots
                    System.out.println("Unoccupied slots deleted.");
                    break;
                case 5:
                    parkVehicle(sc, carPark); // Park a vehicle in a parking slot
                    break;
                case 6:
                    findVehicle(sc, carPark); // Find a vehicle by registration number
                    break;
                case 7:
                    removeVehicle(sc, carPark); // Remove a vehicle by registration number
                    break;
                case 8:
                    System.out.println("Program end!"); // End the program
                    break;
                default:
                    System.out.println("Invalid option. Please try again."); // Handle invalid option input
            }
        } while (option != 8); // Repeat until the user selects the exit option

        sc.close(); // Close the scanner
    }

    // Displays the menu of available options
    public static void displayMenu() {
        System.out.println("\nMenu:");
        System.out.println("1: Add a parking slot");
        System.out.println("2: Delete a parking slot");
        System.out.println("3: List all slots");
        System.out.println("4: Delete all unoccupied parking slots");
        System.out.println("5: Park a car");
        System.out.println("6: Find a car");
        System.out.println("7: Remove a car");
        System.out.println("8: Exit");
    }

    // Gets a valid integer input from the user, ensuring it is a positive number
    public static int getValidIntegerInput(Scanner sc, String message) {
        int input = -1;
        while (input < 0) {
            System.out.print(message);
            if (sc.hasNextInt()) {
                input = sc.nextInt();
                if (input < 0) {
                    System.out.println("Please enter a positive number.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                sc.next(); // Clear invalid input
            }
        }
        sc.nextLine(); // Clear newline character
        return input;
    }

    // Adds a new parking slot to the car park
    public static void addParkingSlot(Scanner sc, CarPark carPark) {
        String slotId;
        do {
            System.out.print("Enter slot ID (format: A01): ");
            slotId = sc.nextLine().toUpperCase();

            // Validate slot ID format using regex and check if slot already exists
            if (!slotId.matches("^[A-Z]{1}[0-9]{2}$")) {
                System.out.println("Incorrect format, please enter the correct slot format e.g. S01");
            } else if (carPark.isSlotExists(slotId)) {
                System.out.println("Slot ID already exists. Please enter a unique slot ID.");
            }
        } while (!slotId.matches("^[A-Z]{1}[0-9]{2}$") || carPark.isSlotExists(slotId)); 

        System.out.print("Enter slot type (STAFF/VISITOR): ");
        String type = sc.nextLine().toUpperCase();

        // Try to add the slot and handle any exceptions
        try {
            if (!carPark.addSlot(new ParkingSlot(slotId, type))) {
                System.out.println("Failed to add slot. Ensure slot ID is unique and correctly formatted.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    // Deletes a parking slot from the car park
    public static void deleteParkingSlot(Scanner sc, CarPark carPark) {
        System.out.print("Enter slot ID to delete: ");
        String slotId = sc.nextLine().toUpperCase();
        if (carPark.isSlotOccupied(slotId)) {
            System.out.println("Cannot delete an occupied slot. Please remove the vehicle first.");
        } else if (!carPark.deleteSlot(slotId)) {
            System.out.println("Failed to delete slot. Ensure slot exists.");
        } else {
            System.out.println("Slot " + slotId + " deleted successfully.");
        }
    }

    // Parks a vehicle in a parking slot
    public static void parkVehicle(Scanner sc, CarPark carPark) {
        String slotId;
        do {
            System.out.print("Enter slot ID (format: A01): ");
            slotId = sc.nextLine().toUpperCase();

            // Validate slot ID format and check if slot is already occupied
            if (!slotId.matches("^[A-Z]{1}[0-9]{2}$")) {
                System.out.println("Incorrect slot ID format. Please use the correct format, e.g., A01.");
            } else if (carPark.isSlotOccupied(slotId)) {
                System.out.println("This parking slot is already occupied.");
                return;
            }
        } while (!slotId.matches("^[A-Z]{1}[0-9]{2}$"));

        String regNo;
        do {
            System.out.print("Enter car registration number (format: A1234): ");
            regNo = sc.nextLine().toUpperCase();

            // Validate car registration number format
            if (!regNo.matches("^[A-Z]{1}[0-9]{4}$")) {
                System.out.println("Incorrect registration number format. Please use the correct format, e.g., A1234.");
            }
        } while (!regNo.matches("^[A-Z]{1}[0-9]{4}$"));

        // Get additional details about the vehicle
        System.out.print("Enter car owner's name: ");
        String owner = sc.nextLine();

        System.out.print("Is the owner a staff member? (yes/no): ");
        boolean isStaff = sc.nextLine().equalsIgnoreCase("yes");

        // Ensure staff and visitors park in the correct slots
        if (isStaff && !carPark.isStaffSlot(slotId)) {
            System.out.println("Staff cars must be parked in staff slots. Please choose the correct slot.");
            return;
        }

        if (!isStaff && carPark.isStaffSlot(slotId)) {
            System.out.println("Visitors cannot park in staff slots. Please choose a visitor slot.");
            return;
        }

        // Create a new vehicle object and park it
        VehicleType type = new VehicleType("CAR", "Default Car Type", 4);
        Vehicle vehicle = new Vehicle(owner + "'s Vehicle", "Unknown", type, 2024, regNo, isStaff);

        if (carPark.parkVehicle(slotId, vehicle)) {
            System.out.println("Vehicle parked successfully in slot " + slotId);
            System.out.println("Parking Time: " + vehicle.getParkingTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        } else {
            System.out.println("Failed to park the vehicle. Ensure slot ID is correct and slot is available.");
        }
    }

    // Finds a vehicle in the car park by registration number
    public static void findVehicle(Scanner sc, CarPark carPark) {
        System.out.print("Enter car registration number: ");
        String regNo = sc.nextLine();
        carPark.findVehicle(regNo);
    }

    // Removes a vehicle from the car park by registration number
    public static void removeVehicle(Scanner sc, CarPark carPark) {
        System.out.print("Enter car registration number: ");
        String regNo = sc.nextLine();
        if (!carPark.removeVehicle(regNo)) {
            System.out.println("Failed to remove vehicle. Ensure registration number is correct.");
        } else {
            System.out.println("Vehicle removed successfully.");
        }
    }
}
