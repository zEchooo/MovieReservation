import java.util.List;
import java.util.Scanner;

public class MovieReservationSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MovieSchedule movieSchedule = new MovieSchedule("C:\\Users\\iramonroe.tabora\\Downloads\\Test.csv");

        while (true) {
            System.out.println("1. Reserve Seats");
            System.out.println("2. Cancel Reservation");
            System.out.print("Select an option: ");

            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    try {
                        // Get available dates
                        List<String> availableDates = movieSchedule.getAvailableDates();

                        System.out.println("Available dates:");
                        for (int i = 0; i < availableDates.size(); i++) {
                            System.out.println((i + 1) + ". " + availableDates.get(i));
                        }

                        System.out.print("Select a date: ");
                        int dateChoice = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        if (dateChoice < 1 || dateChoice > availableDates.size()) {
                            System.out.println("Invalid date choice.");
                            break;
                        }
                        
                        String dateToReserve = availableDates.get(dateChoice - 1);

                        movieSchedule.displayMovieSchedule(dateToReserve);

                        System.out.println("Select movie: ");
                        int choice = scanner.nextInt();
                        Movie selectedMovie = movieSchedule.getMovieByIndex(dateToReserve, choice);
                        System.out.println(selectedMovie.toString());

                        SeatReservation.reserveSeats(selectedMovie);

                    } catch (Exception e) {
                        // Handle exception
                        System.err.println("Error: " + e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("Thank you for booking!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
