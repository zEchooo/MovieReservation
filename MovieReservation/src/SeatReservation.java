import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SeatReservation {
    public static void reserveSeats(Movie selectedMovie) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of seats to reserve:");
        int numSeatsToReserve = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Check seat availability and reserve seats
        ArrayList<String> reservedSeats = new ArrayList<>();

        for (int i = 0; i < numSeatsToReserve; i++) {
            System.out.print("Enter seat code (e.g., 1A, 2B): ");
            String seatCode = scanner.nextLine().trim().toUpperCase();

            // Check if the seat code is valid and available
            if (isValidSeatCode(seatCode) && isSeatAvailable(selectedMovie, seatCode, reservedSeats)) {
                reservedSeats.add(seatCode);
            } else {
                System.out.println("Seat " + seatCode + " is already reserved or invalid.");
                i--; // Decrement to re-enter seat code
            }
        }

        // Calculate total price based on movie type and senior citizen discount
        double totalPrice = calculateTotalPrice(selectedMovie, reservedSeats);

        // Display reservation details
        System.out.println("Reservation successful for " + selectedMovie.getTitle());
        System.out.println("Reserved Seats: " + String.join(", ", reservedSeats));
        System.out.println("Total Price: " + totalPrice + " PHP");

        // Save reservation to CSV
        saveReservationToCSV(selectedMovie, reservedSeats);
    }

    private static boolean isValidSeatCode(String seatCode) {
        // Validate that the seat code is within the specified range (A1 to A5 and H1 to H5)
        return seatCode.matches("[AH][1-5]");
    }

    private static boolean isSeatAvailable(Movie movie, String seatCode, ArrayList<String> reservedSeats) {
        try {
            String fileName = movie.getTitle() + "_reservations.csv";
            BufferedReader reader = new BufferedReader(new FileReader(fileName));

            String line;
            while ((line = reader.readLine()) != null) {
                // Split the CSV line into parts
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String reservedSeat = parts[1].trim().toUpperCase();
                    if (reservedSeats.contains(seatCode) || seatCode.equals(reservedSeat)) {
                        // Seat is either already reserved or reserved in the CSV file
                        reader.close();
                        return false;
                    }
                }
            }

            reader.close();
        } catch (IOException e) {
            System.err.println("Error reading reservation details from CSV file.");
        }

        return true;
    }

    private static double calculateTotalPrice(Movie movie, ArrayList<String> reservedSeats) {
        // Calculate total price based on movie type and selected seats
        // Apply senior citizen discount if necessary
        return 0; // Placeholder, implement the logic
    }

    private static void saveReservationToCSV(Movie selectedMovie, ArrayList<String> reservedSeats) {
        try {
            String fileName = selectedMovie.getTitle() + "_reservations.csv";
            FileWriter writer = new FileWriter(fileName, true); // Append to the existing CSV file

            // Write reservation details to the CSV file
            String reservedSeatsStr = String.join(", ", reservedSeats);
            double totalPrice = calculateTotalPrice(selectedMovie, reservedSeats);
            String reservationDetails = String.format("%s,%s,%.2f PHP%n", selectedMovie.getTitle(), reservedSeatsStr,
                    totalPrice);
            writer.write(reservationDetails);

            writer.close();
            System.out.println("Reservation details saved to " + fileName);
        } catch (IOException e) {
            System.err.println("Error saving reservation details to CSV file.");
        }
    }
}
