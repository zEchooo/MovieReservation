import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class SeatReservation {
    private static final int NUM_ROWS = 8;
    private static final int NUM_COLUMNS = 5;

    public static void reserveSeats(Movie selectedMovie) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of seats to reserve:");
        int numSeatsToReserve = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Check seat availability and reserve seats
        ArrayList<String> reservedSeats = new ArrayList<>();

        displayAvailableSeats(selectedMovie);

        for (int i = 0; i < numSeatsToReserve; i++) {
            System.out.print("Enter seat code (e.g., A1, B3): ");
            String seatCode = scanner.nextLine().trim().toUpperCase();

            // Check if the seat code is valid (within the rows and columns of the movie)
            if (isValidSeatCode(seatCode)) {
                int row = seatCode.charAt(0) - 'A';
                int column = Integer.parseInt(seatCode.substring(1)) - 1;

                if (isSeatAvailable(selectedMovie, row, column)) {
                    selectedMovie.getSeats()[row][column] = true; // Mark the seat as reserved
                    reservedSeats.add(seatCode);
                } else {
                    System.out.println("Seat " + seatCode + " is already reserved or invalid.");
                    i--; // Decrement to re-enter seat code
                }
            } else {
                System.out.println("Invalid seat code format. Please enter a valid seat code (e.g., A1, B3).");
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

    private static boolean isSeatAvailable(Movie movie, int row, int column) {
        if (row >= 0 && row < NUM_ROWS && column >= 0 && column < NUM_COLUMNS) {
            return !movie.getSeats()[row][column];
        }
        return false;
    }

    private static double calculateTotalPrice(Movie movie, ArrayList<String> reservedSeats) {
        // Calculate total price based on movie type and selected seats
        double totalPrice = 0;
        if (!movie.isPremiere()) {
            int regularTicketPrice = 350;
            totalPrice = regularTicketPrice * reservedSeats.size();
            // Apply senior citizen discount if necessary
        }
        return totalPrice; // Placeholder, implement the logic
    }

    private static void displayAvailableSeats(Movie movie) {
        System.out.println("Available Seats for " + movie.getTitle() + ":");
        boolean[][] seats = movie.getSeats();

        for (int row = 0; row < NUM_ROWS; row++) {
            for (int column = 0; column < NUM_COLUMNS; column++) {
                String seatCode = String.format("%c%d", 'A' + row, column + 1);

                if (seats[row][column]) {
                    System.out.print("X "); // Seat is reserved
                } else {
                    System.out.print(seatCode + " ");
                }
            }
            System.out.println(); // Move to the next row
        }
    }

    private static boolean isValidSeatCode(String seatCode) {
        // Check if the seat code matches the pattern of a valid seat
        if (seatCode.length() != 2) {
            return false;
        }
        char rowChar = seatCode.charAt(0);
        int column = Character.getNumericValue(seatCode.charAt(1));

        // Check if the row character is within 'A' to 'H' (corresponding to rows A to H)
        if (rowChar < 'A' || rowChar > 'H') {
            return false;
        }

        // Check if the column is within 1 to 5 (corresponding to columns 1 to 5)
        if (column < 1 || column > 5) {
            return false;
        }

        return true;
    }

    private static void saveReservationToCSV(Movie selectedMovie, ArrayList<String> reservedSeats) {
    try {
        // Generate a unique reservation number (you can implement your logic for this)
        String reservationNumber = generateReservationNumber();

        // Get the current date and time
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        String reservationDate = dateFormat.format(currentDate);

        // Cinema number and time of the movie (you can get these from the selected movie)
        int cinemaNumber = selectedMovie.getCinemaNumber();
        String movieTime = selectedMovie.getTime();

        String fileName = "reservations.csv";
        
        boolean fileExists = new File(fileName).exists();
        FileWriter writer = new FileWriter(fileName, true);

        // Add column headers if the file is empty (for the first row)
        if (!fileExists) {
            writer.write("Reservation Number,Reservation Date,Cinema Number,Movie Time,Reserved Seats,Total Price,Movie Title\n");
        }

        // Concatenate reserved seats into a single string
        String reservedSeatsStr = String.join(", ", reservedSeats);

        // Write reservation details to the CSV file
        String reservationDetails = String.format("%s,%s,%d,%s,\"%s\",%.2f,%s%n",
                reservationNumber, reservationDate, cinemaNumber, movieTime,
                reservedSeatsStr, calculateTotalPrice(selectedMovie, reservedSeats), selectedMovie.getTitle());
        writer.write(reservationDetails);

        writer.close();
        System.out.println("Reservation details saved to " + fileName);
    } catch (IOException e) {
        System.err.println("Error saving reservation details to CSV file.");
    }
}

    

    private static String generateReservationNumber() {
        // Implement your logic to generate a unique reservation number (e.g., using timestamps)
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date currentDate = new Date();
        return dateFormat.format(currentDate);
    }

    public static ArrayList<String> loadReservationsFromCSV() {
        ArrayList<String> reservations = new ArrayList<>();
        String fileName = "reservations.csv";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;

            // Skip the header row
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String reservedSeats = parts[4]; // Index 4 corresponds to the reserved seats column
                reservations.add(reservedSeats);
            }

            reader.close();
        } catch (IOException e) {
            System.err.println("Error loading reservations from CSV file.");
        }

        return reservations;
    }

    public static void updateSeatAvailability(Movie selectedMovie) {
        ArrayList<String> reservations = loadReservationsFromCSV();
        boolean[][] seats = selectedMovie.getSeats();

        for (String reservedSeats : reservations) {
            String[] seatCodes = reservedSeats.split(", ");
            for (String seatCode : seatCodes) {
                int row = seatCode.charAt(0) - 'A';
                int column = Integer.parseInt(seatCode.substring(1)) - 1;
                if (row >= 0 && row < NUM_ROWS && column >= 0 && column < NUM_COLUMNS) {
                    seats[row][column] = true; // Mark the seat as reserved
                }
            }
        }
    }
}
