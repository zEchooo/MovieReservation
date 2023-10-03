import java.util.Scanner;

public class MovieReservationSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MovieSchedule movieSchedule = new MovieSchedule("C:\\Users\\kier.magday\\Downloads\\Test.csv");

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
                    Movie selectedMovie = movieSchedule.getMovieByIndex(dateToReserve,choice);
                    System.out.println(selectedMovie.toString());
                    
                    SeatReservation.reserveSeats(selectedMovie);


                } catch (Exception e) {
                    // TODO: handle exception
                }
                    break;
                case 2:
                    // Cancel Reservation
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}