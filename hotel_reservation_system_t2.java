import java.io.*;
import java.util.*;

class Room {
    String category;
    int roomNumber;
    boolean isBooked;

    Room(String category, int roomNumber) {
        this.category = category;
        this.roomNumber = roomNumber;
        this.isBooked = false;
    }
}

class Booking {
    String name;
    String category;
    int roomNumber;

    Booking(String name, String category, int roomNumber) {
        this.name = name;
        this.category = category;
        this.roomNumber = roomNumber;
    }

    @Override
    public String toString() {
        return name + "," + category + "," + roomNumber;
    }

    public static Booking fromString(String line) {
        String[] parts = line.split(",");
        return new Booking(parts[0], parts[1], Integer.parseInt(parts[2]));
    }
}

public class hotel_reservation_system_t2 {
    static Scanner sc = new Scanner(System.in);
    static List<Room> rooms = new ArrayList<>();
    static List<Booking> bookings = new ArrayList<>();
    static final String FILE_NAME = "bookings.txt";

    public static void main(String[] args) {
        loadRooms();
        loadBookings();
        mainMenu();
        saveBookings(); // save on exit
    }

    static void loadRooms() {
        for (int i = 1; i <= 5; i++) rooms.add(new Room("Standard", i));
        for (int i = 6; i <= 10; i++) rooms.add(new Room("Deluxe", i));
        for (int i = 11; i <= 15; i++) rooms.add(new Room("Suite", i));
    }

    static void loadBookings() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Booking b = Booking.fromString(line);
                bookings.add(b);
                for (Room r : rooms) {
                    if (r.roomNumber == b.roomNumber) {
                        r.isBooked = true;
                    }
                }
            }
        } catch (IOException e) {
            // File may not exist, ignore
        }
    }

    static void saveBookings() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Booking b : bookings) {
                writer.write(b.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving bookings.");
        }
    }

    static void mainMenu() {
        while (true) {
            System.out.println("\n--- Hotel Reservation System ---");
            System.out.println("1. View Available Rooms");
            System.out.println("2. Book Room");
            System.out.println("3. Cancel Booking");
            System.out.println("4. View My Bookings");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // clear newline

            switch (choice) {
                case 1: viewRooms(); break;
                case 2: bookRoom(); break;
                case 3: cancelBooking(); break;
                case 4: viewBookings(); break;
                case 5: return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    static void viewRooms() {
        System.out.println("\nAvailable Rooms:");
        for (Room r : rooms) {
            if (!r.isBooked)
                System.out.println("Room " + r.roomNumber + " (" + r.category + ")");
        }
    }

    static void bookRoom() {
        System.out.print("\nEnter your name: ");
        String name = sc.nextLine();
        System.out.print("Enter room category (Standard/Deluxe/Suite): ");
        String category = sc.nextLine();

        for (Room r : rooms) {
            if (!r.isBooked && r.category.equalsIgnoreCase(category)) {
                r.isBooked = true;
                Booking b = new Booking(name, category, r.roomNumber);
                bookings.add(b);
                simulatePayment();
                System.out.println("Booking successful! Room " + r.roomNumber + " booked for " + name);
                return;
            }
        }
        System.out.println("No rooms available in this category.");
    }

    static void cancelBooking() {
        System.out.print("\nEnter your name to cancel: ");
        String name = sc.nextLine();
        boolean found = false;

        Iterator<Booking> it = bookings.iterator();
        while (it.hasNext()) {
            Booking b = it.next();
            if (b.name.equalsIgnoreCase(name)) {
                it.remove();
                for (Room r : rooms) {
                    if (r.roomNumber == b.roomNumber) {
                        r.isBooked = false;
                    }
                }
                found = true;
                System.out.println("Booking for " + name + " has been canceled.");
            }
        }

        if (!found) System.out.println("No booking found under your name.");
    }

    static void viewBookings() {
        System.out.print("\nEnter your name: ");
        String name = sc.nextLine();
        boolean found = false;

        for (Booking b : bookings) {
            if (b.name.equalsIgnoreCase(name)) {
                System.out.println("Booking - Room: " + b.roomNumber + ", Category: " + b.category);
                found = true;
            }
        }

        if (!found) System.out.println("No bookings found.");
    }

    static void simulatePayment() {
        System.out.println("Processing payment...");
        try {
            Thread.sleep(1000); // simulate delay
        } catch (InterruptedException e) {}
        System.out.println("Payment successful!");
    }
}