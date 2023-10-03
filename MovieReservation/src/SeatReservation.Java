import java.util.ArrayList;
import java.util.Scanner;

public class SeatReservation {
    public static void reserveSeats(Movie selectedMovie) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of seats to reserve:");
        int numSeatsToReserve = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Check seat availability and reserve seats
        ArrayList<String> reservedSeats = new ArrayList<>();

        for (int i = 0; i < numSeatsToReserve; i++) {
            System.out.print("Enter seat code (e.g., A1, B3): ");
            String seatCode = scanner.nextLine().trim().toUpperCase();

            // Check if the seat is available
            if (isSeatAvailable(selectedMovie, seatCode, reservedSeats)) {
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

        // Save reservation to CSV (implement this part)
    }

    private static boolean isSeatAvailable(Movie movie, String seatCode, ArrayList<String> reservedSeats) {
        // Check if the seat is within valid range and not already reserved
        // Implement the logic to check seat availability
        return true; // Placeholder, implement the actual logic
    }

    private static double calculateTotalPrice(Movie movie, ArrayList<String> reservedSeats) {
        // Calculate total price based on movie type and selected seats
        double totalPrice = 0;
        if(!movie.isPremiere()){
            int regularTicketPrice = 350;

            totalPrice = regularTicketPrice * reservedSeats.size();

            // Apply senior citizen discount if necessary

        }
        return totalPrice; // Placeholder, implement the logic
    }
    


}