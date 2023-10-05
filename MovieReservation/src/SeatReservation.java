import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
public class SeatReservation {
 
    private static final int NUM_ROWS = 8;
    private static final int NUM_COLUMNS = 5;
    public static int numOfDiscount = 0;

        public static void reserveSeats(Movie selectedMovie) {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter the number of seats to reserve:");
            int numSeatsToReserve = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (!selectedMovie.isPremiere()) {
                do {
                    System.out.println("Enter number of senior citizens: ");
                    numOfDiscount = scanner.nextInt();
                    scanner.nextLine();
                    if (numOfDiscount > numSeatsToReserve) {
                        System.out.println("Invalid input: Number of senior citizens cannot be greater than the number of seats to reserve.");
                    }
                } while (numOfDiscount > numSeatsToReserve);
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
            
            System.out.println("Selected Seats: " + String.join(", ", reservedSeats));
            int confirmation = -1; // Initialize to an invalid value

            while (confirmation != 0 && confirmation != 1) {
                System.out.print("Do you want to proceed with the reservation? [1] Yes | [0] No: ");
                
                // Check if the input is an integer
                if (scanner.hasNextInt()) {
                    confirmation = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    
                    if (confirmation == 1) {
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
                        saveReservationToCSV(ticketNumber, selectedMovie.getDate(), selectedMovie.getCinemaNumber(), selectedMovie.getTime(), reservedSeats, totalPrice);
                    } else if (confirmation == 0) {
                        // User opted not to proceed with the reservation
                        System.out.println("Reservation canceled.");
                        // Restore the seats to available status
                        for (String seatCode : reservedSeats) {
                            int row = seatCode.charAt(0) - 'A';
                            int column = Integer.parseInt(seatCode.substring(1)) - 1;
                            selectedMovie.getSeats()[row][column] = false; // Mark the seat as available again
                        }
                    } else {
                        System.out.println("Invalid input. [1] Yes | [0] No: ");
                    }
                } else {
                    System.out.println("Invalid input. [1] Yes | [0] No: ");
                    scanner.nextLine(); // Consume invalid input
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
            discount = (discount*regularTicketPrice) * 0.20;
            totalPrice = (regularTicketPrice * reservedSeats.size()) - discount;
            // Apply senior citizen discount if necessary
        }
        else{
            int regularTicketPrice = 500;
            totalPrice = (regularTicketPrice * reservedSeats.size());
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
    private static void saveReservationToCSV(String ticketNumber, String date, int cinemaNumber, String time, ArrayList<String> reservedSeats, double totalPrice) {
        String csvFilePath = "reservations.csv"; // Change to your desired file path

        try (FileWriter writer = new FileWriter(csvFilePath, true)) {
            // Append reservation details to the CSV file
            writer.append("\"" + ticketNumber + "\",");
            writer.append("\"" + date + "\",");
            writer.append("\"" + cinemaNumber + "\",");
            writer.append("\"" + time + "\",");
            writer.append("\"" + String.join(",", reservedSeats) + "\",");
            writer.append("\"" + String.format("%.2f", totalPrice) + "\"\n");
            System.out.println("Reservation details saved to " + csvFilePath);
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

    public static ArrayList<Movie> loadReservationsFromCSV(String csvFilePath) {
        ArrayList<Movie> reservations = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    String date = parts[1].trim();
                    int cinemaNumber = Integer.parseInt(parts[2].trim());
                    String time = parts[3].trim();
                    String[] seatCodes = parts[4].trim().split("\\s*,\\s*");
                    double price = Double.parseDouble(parts[5].trim());

                    // Find the corresponding movie in the reservations ArrayList
                    for (Movie movie : reservations) {
                        if (movie.getDate().equals(date) && movie.getCinemaNumber() == cinemaNumber
                                && movie.getTime().equals(time)) {
                            // Add seat codes and price to the movie
                        
                            break;
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading reservations from CSV file: " + e.getMessage());
        }

        return reservations;
    }
}