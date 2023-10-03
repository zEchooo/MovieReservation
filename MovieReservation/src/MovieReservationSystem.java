import java.util.List;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MovieReservationSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MovieSchedule movieSchedule = new MovieSchedule("C:\\Users\\jericho.martin\\Downloads\\Test.csv");

        while (true) {
            System.out.println("1. Reserve Seats");
            System.out.println("2. Cancel Reservation");
            System.out.print("Select an option: ");

            int option = readNumericInput(scanner);

            switch (option) {
                case 1:
                    try {
                        // Get available dates
                        List<String> availableDates = movieSchedule.getAvailableDates();

                        System.out.println("Available dates:");
                        for (int i = 0; i < availableDates.size(); i++) {
                            System.out.println((i + 1) + ". " + availableDates.get(i));
                        }

                        int dateChoice;
                        String dateToReserve;

                        do {
                            System.out.print("Select a date: ");
                            dateChoice = readNumericInput(scanner);

                            if (dateChoice < 1 || dateChoice > availableDates.size()) {
                                System.out.println("Invalid date choice. Please try again.");
                            } else {
                                dateToReserve = availableDates.get(dateChoice - 1);
                                break;
                            }
                        } while (true);

                        movieSchedule.displayMovieSchedule(dateToReserve);

                        System.out.println("Select movie: ");
                        int choice = readNumericInput(scanner);
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

    // Method to read numeric input and handle exceptions
    private static int readNumericInput(Scanner scanner) {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a numeric value.");
                scanner.nextLine();
                System.out.print("Select an option: ");
            }
        }
    }
}
