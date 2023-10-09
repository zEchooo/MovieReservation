import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class SeatReservation {

    private static final int NUM_ROWS = 8;
    private static final int NUM_COLUMNS = 5;
    public static int numOfDiscount = 0;
    ArrayList<String> reservedSeats = new ArrayList<>();

    public static void reserveSeats(Movie selectedMovie) {
        Scanner scanner = new Scanner(System.in);

        int numSeatsToReserve = -1;
        // Calculate the number of occupied seats
        int occupiedSeatsCount = countOccupiedSeats(selectedMovie);
        // Calculate the maximum available seats
        int maxAvailableSeats = 40 - occupiedSeatsCount;

        // Keep asking for input until a valid integer is provided
        while (numSeatsToReserve <= 0 || numSeatsToReserve > maxAvailableSeats) {
            System.out.println("\nCinema has " + occupiedSeatsCount + " occupied seats.");
            System.out.print("Enter the number of seats to reserve (available: " + maxAvailableSeats + "): ");
        
            // Check if the input is an integer
            if (scanner.hasNextInt()) {
                numSeatsToReserve = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (numSeatsToReserve <= 0) {
                    System.out.println("Number of seats must be greater than 0. Please try again.");
                } else if (numSeatsToReserve > maxAvailableSeats) {
                    System.out.println("Number of seats cannot exceed the maximum available seats ("
                            + maxAvailableSeats + "). Please try again.");
                }
            } else {
                System.out.println("Invalid input: Please enter a valid integer for the number of seats.");
                scanner.nextLine(); // Consume invalid input
            }
        }
        if (numSeatsToReserve > 0 && !selectedMovie.isPremiere()) {
            int numOfDiscount = -1; // Initialize to an invalid value
        
            // Keep asking for input until a valid integer is provided
            while (numOfDiscount < 0 || numOfDiscount > numSeatsToReserve) {
                System.out.print("Enter number of senior citizens: ");
        
                // Check if the input is an integer
                if (scanner.hasNextInt()) {
                    numOfDiscount = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    if (numOfDiscount < 0) {
                        System.out
                                .println("Invalid input: Number of senior citizens cannot be negative. Please try again.");
                    } else if (numOfDiscount > numSeatsToReserve) {
                        System.out.println(
                                "Invalid input: Number of senior citizens cannot be greater than the number of seats to reserve. Please try again.");
                    }
                } else {
                    System.out.println("Invalid input: Please enter a valid integer for the number of senior citizens.");
                    scanner.nextLine(); // Consume invalid input
                }
            }
        } // Initialize to an invalid value

        // Keep asking for input until a valid integer is provided
        if(!selectedMovie.isPremiere()){
            while (numOfDiscount < 0 || numOfDiscount > numSeatsToReserve) {
                System.out.print("Enter number of senior citizens: ");
    
                // Check if the input is an integer
                if (scanner.hasNextInt()) {
                    numOfDiscount = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    if (numOfDiscount < 0) {
                        System.out
                                .println("Invalid input: Number of senior citizens cannot be negative. Please try again.");
                    } else if (numOfDiscount > numSeatsToReserve) {
                        System.out.println(
                                "Invalid input: Number of senior citizens cannot be greater than the number of seats to reserve. Please try again.");
                    }
                } else {
                    System.out.println("Invalid input: Please enter a valid integer for the number of senior citizens.");
                    scanner.nextLine(); // Consume invalid input
                }
            }
        }
        
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

        // Ask if the user wants to proceed with the reservation
        int proceedChoice = -1;
        while (proceedChoice != 0 && proceedChoice != 1) {
            System.out.print("\nDo you want to proceed with the reservation? [1] Yes | [0] No: ");
            if (scanner.hasNextInt()) {
                proceedChoice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (proceedChoice != 0 && proceedChoice != 1) {
                    System.out.println("Invalid input. [1] Yes | [0] No");
                }
            } else {
                System.out.println("Invalid input. [1] Yes | [0] No");
                scanner.nextLine(); // Consume invalid input
            }
        }

        if (proceedChoice == 1) {
            // Calculate total price based on movie type and senior citizen discount
            double totalPrice = calculateTotalPrice(selectedMovie, reservedSeats, numOfDiscount);

            // Generate a ticket number
            String ticketNumber = generateTicketNumber();
            // Display reservation details
            System.out.println("Reservation successful for " + selectedMovie.getTitle());
            System.out.println("Reservation ticket number: " + ticketNumber);
            System.out.println("Reserved Seats: " + String.join(", ", reservedSeats));
            System.out.println("Total Price: " + totalPrice + " PHP");
            selectedMovie.setTotalPrice(totalPrice);

            // Save reservation to CSV
            selectedMovie.setTicketNumber(ticketNumber);
            selectedMovie.addReservation(ticketNumber, reservedSeats);
            saveReservationToCSV(ticketNumber, selectedMovie.getDate(), selectedMovie.getCinemaNumber(),
                    selectedMovie.getTime(), reservedSeats, totalPrice);
        } else {
            System.out.println("Reservation canceled.");
            for (String seatCode : reservedSeats) {
                int row = seatCode.charAt(0) - 'A';
                int column = Integer.parseInt(seatCode.substring(1)) - 1;
                selectedMovie.getSeats()[row][column] = false; // Mark the seat as available again
            }
        }
    }

    private static boolean isSeatAvailable(Movie movie, int row, int column) {
        if (row >= 0 && row < NUM_ROWS && column >= 0 && column < NUM_COLUMNS) {
            return !movie.getSeats()[row][column];
        }
        return false;
    }

    private static double calculateTotalPrice(Movie movie, ArrayList<String> reservedSeats, double discount) {
        // Calculate total price based on movie type and selected seats
        double totalPrice = 0;
        if (!movie.isPremiere()) {
            int regularTicketPrice = 350;
            totalPrice = regularTicketPrice * reservedSeats.size();
            discount = (discount * regularTicketPrice) * 0.20;
            totalPrice = (regularTicketPrice * reservedSeats.size()) - discount;
            // Apply senior citizen discount if necessary
        } else {
            int regularTicketPrice = 500;
            totalPrice = (regularTicketPrice * reservedSeats.size());
        }

        return totalPrice; // Placeholder, implement the logic
    }

    private static void displayAvailableSeats(Movie movie) {
        System.out.println("\nAvailable Seats for " + movie.getTitle() + ":");
        System.out.println("\n\t\t  ******* SCREEN *******");
        System.out.print("Entrance/Exit |");
        boolean[][] seats = movie.getSeats();

        for (int row = 0; row < NUM_ROWS; row++) {
            System.out.print("\n\t      |");
            for (int column = 0; column < NUM_COLUMNS; column++) {
                String seatCode = String.format("   %c%d", 'A' + row, column + 1);

                if (seats[row][column]) {
                    System.out.print("   XX"); // Seat is reserved
                } else {
                    System.out.print(seatCode);
                }
            }
        }
        System.out.println(); // Move to the next row
    }

    private static boolean isValidSeatCode(String seatCode) {
        // Check if the seat code matches the pattern of a valid seat
        if (seatCode.length() != 2) {
            return false;
        }
        char rowChar = seatCode.charAt(0);
        int column = Character.getNumericValue(seatCode.charAt(1));

        // Check if the row character is within 'A' to 'H' (corresponding to rows A to
        // H)
        if (rowChar < 'A' || rowChar > 'H') {
            return false;
        }

        // Check if the column is within 1 to 5 (corresponding to columns 1 to 5)
        if (column < 1 || column > 5) {
            return false;
        }

        return true;
    }

    private static void saveReservationToCSV(String ticketNumber, String date, int cinemaNumber, String time,
            ArrayList<String> reservedSeats, double totalPrice) {
        String csvFilePath = "reservations.csv"; // Change to your desired file path

        try (FileWriter writer = new FileWriter(csvFilePath, true)) {
            // Append reservation details to the CSV file
            writer.append("\"" + ticketNumber + "\",");
            writer.append("\"" + date + "\",");
            writer.append("\"" + cinemaNumber + "\",");
            writer.append("\"" + time + "\",");
            writer.append("\"" + String.join(",", reservedSeats) + "\",");
            writer.append("\"" + String.format("%.2f", totalPrice) + "\"\n");
        } catch (IOException e) {
            System.err.println("Error saving reservation details to CSV: " + e.getMessage());
        }
    }

    // Generate a unique ticket number (you can implement your own logic)
    private static String generateTicketNumber() {
        // Generate a ticket number based on date and a random number
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String formattedDate = dateFormat.format(new Date());

        Random random = new Random();
        int randomNumber = random.nextInt(1000000); // Generate a random 6-digit number

        return formattedDate + String.format("%06d", randomNumber);
    }

    public static void cancelReservation(MovieSchedule movieSchedule, String ticketNumberToCancel) {
        // Access the 'movies' ArrayList through the MovieSchedule instance
        ArrayList<Movie> movies = movieSchedule.getMovies();

        for (Movie movie : movies) {
            Map<String, List<String>> reservations = movie.getReservations();

            if (reservations.containsKey(ticketNumberToCancel)) {
                List<String> reservedSeats = reservations.get(ticketNumberToCancel);

                if (reservedSeats.isEmpty()) {
                    System.out.println("No seats are reserved for this ticket number.");
                    return; // Exit the method
                }

                System.out.println("Reserved Seats for Ticket Number " + ticketNumberToCancel + ": "
                        + String.join(", ", reservedSeats));

                // Mark the canceled seats as available again
                for (String seatCode : reservedSeats) {
                    int row = seatCode.charAt(0) - 'A';
                    int column = Integer.parseInt(seatCode.substring(1)) - 1;
                    movie.getSeats()[row][column] = false; // Mark the seat as available
                }

                // Remove the ticket number from the movie's reservations
                reservations.remove(ticketNumberToCancel);

                // Save updated reservation to CSV (implement this part)
                updateCsvFile("reservations.csv", movies);

                // You should also update the CSV file with the latest reservation information.

                System.out.println(
                        "Reservations for Ticket Number " + ticketNumberToCancel + " have been canceled successfully.");

                // Display available seats for the movie
                displayAvailableSeats(movie);

                return; // Exit the method since the reservations have been canceled
            }
        }

        // If the ticket number was not found in any movie, display an error message
        System.out.println("Ticket Number " + ticketNumberToCancel + " was not found in the reservation records.");
    }

    public static void updateCsvFile(String csvFilePath, ArrayList<Movie> movies) {
        try (FileWriter writer = new FileWriter(csvFilePath, false)) { // Use 'false' to overwrite the entire file
            // Write CSV header
            writer.append("Ticket Number,Date,Cinema Number,Time,Reserved Seats,Total Price\n");

            for (Movie movie : movies) {
                Map<String, List<String>> reservations = movie.getReservations();
                for (Map.Entry<String, List<String>> entry : reservations.entrySet()) {
                    String ticketNumber = entry.getKey();
                    List<String> reservedSeats = entry.getValue();
                    String reservedSeatsStr = String.join(", ", reservedSeats);

                    // Write movie and reservation data to CSV
                    writer.append("\"" + ticketNumber + "\","); // Enclose ticket number in double quotes
                    writer.append(movie.getDate() + ",");
                    writer.append(movie.getCinemaNumber() + ",");
                    writer.append(movie.getTime() + ",");
                    writer.append("\"" + reservedSeatsStr + "\","); // Enclose reserved seats in double quotes
                    writer.append("\"" + String.format("%.2f", movie.getTotalPrice()) + "\"\n"); // Enclose total price
                                                                                                 // in double quotes
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error updating CSV file: " + e.getMessage());
        }
    }

    private static int countOccupiedSeats(Movie movie) {
        boolean[][] seats = movie.getSeats();
        int occupiedSeatsCount = 0;

        for (int row = 0; row < NUM_ROWS; row++) {
            for (int column = 0; column < NUM_COLUMNS; column++) {
                if (seats[row][column]) {
                    occupiedSeatsCount++;
                }
            }
        }

        return occupiedSeatsCount;
    }
}