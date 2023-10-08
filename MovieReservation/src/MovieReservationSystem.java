import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MovieReservationSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        MovieSchedule movieSchedule = new MovieSchedule("MovieSchedule.csv");

        while (true) {
        	  System.out.println("\n-------------------------------------------------------");
            System.out.println("\t\tWelcome to Cinema World!");
            System.out.println("-------------------------------------------------------");
            System.out.println("    [1] Reserve Seats");
            System.out.println("    [2] Cancel Reservation");
            System.out.println("    [3] Exit");
            System.out.println("-------------------------------------------------------");
            System.out.print("Select an option: ");

            int option = readNumericInput(scanner);
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    try {
                        // Get available dates
                        List<String> availableDates = movieSchedule.getAvailableDates();

                        System.out.println("\nAvailable dates:");
                        for (int i = 0; i < availableDates.size(); i++) {
                            System.out.println((i + 1) + ". " + availableDates.get(i));
                        }
                        int dateChoice;
                        do {
                            System.out.print("Select a date: ");
                            dateChoice = readNumericInput(scanner);

                            if (dateChoice < 1 || dateChoice > availableDates.size()) {
                                System.out.println("Invalid date choice. Please try again.");
                            }
                        } while (dateChoice < 1 || dateChoice > availableDates.size());

                        String dateToReserve = availableDates.get(dateChoice - 1);
                        movieSchedule.displayMovieSchedule(dateToReserve);

                        int choice;
                        do {
                            System.out.print("Select movie: ");
                            choice = readNumericInput(scanner);

                            if (choice < 1 || choice > movieSchedule.getNumberOfElements(dateToReserve)) {
                                System.out.println("Invalid movie choice. Please try again.");
                            }
                        } while (choice < 1 || choice > movieSchedule.getNumberOfElements(dateToReserve));

                        Movie selectedMovie = movieSchedule.getMovieByIndex(dateToReserve, choice);
                        System.out.println(selectedMovie.toString());
                      
                        SeatReservation.reserveSeats(selectedMovie);
                        //SeatReservation.updateSeatAvailability(selectedMovie);
                    } catch (Exception e) {
                        // Handle exception
                        //System.err.println("Error: " + e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("Cancel Reservation");
                    System.out.println("Input Ticket Number: ");
                    String ticketNumber = scanner.nextLine().trim();
                    SeatReservation.cancelReservation(movieSchedule, ticketNumber);;
                    break;
                case 3:
                    System.out.println("Exiting the program.");
                    System.exit(0); // Exit the program
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    
    private static int readNumericInput(Scanner scanner) {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please try again.");
                scanner.nextLine();
                System.out.print("Select an option: ");
            }
        }
    }
}