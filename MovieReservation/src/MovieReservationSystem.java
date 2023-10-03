import java.util.ArrayList;
import java.util.Scanner;
public class MovieReservationSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MovieSchedule movieSchedule = new MovieSchedule(
                "C:\\Users\\iramonroe.tabora\\MovieReservation\\MovieReservation\\Test.csv");

        // Load movies from a CSV file using CSVHandler

        while (true) {
            System.out.println("1. Reserve Seats");
            System.out.println("2. Cancel Reservation");
            System.out.print("Select an option: ");

            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    try {
                        System.out.println("Input date: ");
                        String dateToReserve = scanner.next();
                        movieSchedule.displayMovieSchedule(dateToReserve);

                        System.out.println("Select movie: ");
                        int choice = scanner.nextInt();
                        Movie selectedMovie = movieSchedule.getMovieByIndex(dateToReserve, choice);

                        // Call the SeatReservation class and pass the selected movie
                        SeatReservation.reserveSeats(selectedMovie);
                    } catch (Exception e) {
                        // Handle exception
                        System.err.println("Error: " + e.getMessage());
                    }
                    break;
                case 2:
                    // Cancel Reservation
                    break;
                case 3:
                    // Additional options
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
