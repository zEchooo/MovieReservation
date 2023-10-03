public class SeatReservation {
    
    public static void displayAvailableSeats(Movie selectedMovie) {
        String[] rowLabels = {"A", "B", "C", "D", "E", "F", "G", "H"};
        int numSeatsPerRow = 5; // Number of seats per row

        System.out.println("Seat Layout for " + selectedMovie.getTitle() + " (Cinema " + selectedMovie.getCinemaNumber() + "):");
        for (int row = 0; row < rowLabels.length; row++) {
            for (int seat = 0; seat < numSeatsPerRow; seat++) {
                boolean isAvailable = selectedMovie.isSeatAvailable(row, seat);
                String seatLabel = rowLabels[row] + (seat + 1); // Row label + Seat number
                System.out.print(isAvailable ? "[" + seatLabel + "]" : "[X]"); // X represents a reserved seat
            }
            System.out.println(); // Move to the next row
        }
    }

    public static boolean areSeatsAvailable(Movie selectedMovie, String startSeat, String endSeat) {
        // Assuming you have a data structure to track reserved seats in the Movie class
        boolean[][] reservedSeats = selectedMovie.getReservedSeats();
    
        // Parse the seat labels to get row and seat numbers
        int startRow = seatLabelToRow(startSeat);
        int startSeatNumber = seatLabelToSeatNumber(startSeat);
        int endRow = seatLabelToRow(endSeat);
        int endSeatNumber = seatLabelToSeatNumber(endSeat);
    
        // Check if the range of seats is valid
        if (startRow < 0 || startRow >= reservedSeats.length || endRow < 0 || endRow >= reservedSeats.length) {
            return false; // Invalid row
        }
    
        if (startSeatNumber < 0 || startSeatNumber >= reservedSeats[startRow].length || endSeatNumber < 0 || endSeatNumber >= reservedSeats[endRow].length) {
            return false; // Invalid seat number
        }
    
        // Check if any of the seats in the range are already reserved
        for (int row = startRow; row <= endRow; row++) {
            for (int seatNumber = startSeatNumber; seatNumber <= endSeatNumber; seatNumber++) {
                if (reservedSeats[row][seatNumber]) {
                    return false; // Seat is already reserved
                }
            }
        }
    
        // All seats in the range are available
        return true;
    }
    
    // Helper methods to convert seat labels to row and seat numbers
    private static int seatLabelToRow(String seatLabel) {
        char rowChar = seatLabel.charAt(0);
        return rowChar - 'A'; // Convert 'A' to 0, 'B' to 1, etc.
    }
    
    private static int seatLabelToSeatNumber(String seatLabel) {
        return Integer.parseInt(seatLabel.substring(1)) - 1; // Convert seat number to 0-based index
    }
    

    public static boolean isValidSeatLabel(String seatLabel) {
        // Implement seat label validation logic here
        // You can check if the seatLabel matches a valid format, such as "A1", "B3", etc.
        // Return true if it's valid, false otherwise.
        // Example validation logic:
        return seatLabel.matches("[A-H][1-5]");
    }
    



}