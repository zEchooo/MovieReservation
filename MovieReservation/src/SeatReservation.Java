import java.util.ArrayList;
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

        // Save reservation to CSV (implement this part)
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
}
