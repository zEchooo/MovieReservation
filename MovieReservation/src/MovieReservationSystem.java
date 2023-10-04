import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MovieReservationSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        MovieSchedule movieSchedule = new MovieSchedule("C:\\Users\\sophia.sansan\\Downloads\\Test.csv");

        while (true) {
            System.out.println("1. Reserve Seats");
            System.out.println("2. Cancel Reservation");
            System.out.print("Select an option: ");

            int option = readNumericInput(scanner);
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
                        do{
                        System.out.print("Select a date: ");
                        int dateChoice = readNumericInput(scanner);

                        scanner.nextLine(); // Consume newline
                       
                            if (dateChoice < 1 || dateChoice > availableDates.size()) {
                            System.out.println("Invalid date choice. Please try again");
                            }
                            else{
                            String dateToReserve = availableDates.get(dateChoice - 1);
                            movieSchedule.displayMovieSchedule(dateToReserve);
                                
                            do{
                                System.out.println("Select movie: ");

                                int choice = readNumericInput(scanner);
                                if (choice < 1 || choice > movieSchedule.getNumberOfElements(dateToReserve))
                                {
                                    System.out.println("Invalid input. Please try again");
                                }
                                    else{
                                        Movie selectedMovie = movieSchedule.getMovieByIndex(dateToReserve, choice);
                                        System.out.println(selectedMovie.toString());
                            
                                        SeatReservation.reserveSeats(selectedMovie);
                                        
                                        break;
                                    }
                            
                            //SeatReservation.updateSeatAvailability(selectedMovie);
                                }while(true);
                            }
                        }while(true);
                       

                        
                    } catch (Exception e) {
                        // Handle exception
                        //System.err.println("Error: " + e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("Cancel Reservation");
                    System.out.println("Input Ticket Number: ");
                    
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