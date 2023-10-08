import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Movie {
    private String date;
    private int cinemaNumber;
    private String time;
    private boolean isPremiere;
    private String title;
    private int length;
    private boolean[][] seats;
    private static final int NUM_ROWS = 8;
    private static final int NUM_COLUMNS = 5;
    private double totalPrice;
    private String ticketNumber;
    private Map<String, List<String>> reservations; // Use a map to store reservations
    
    @Override
    public String toString() {
        return "Date: " + date +
                ", Cinema Number: " + cinemaNumber +
                ", Time: " + time +
                ", Premiere: " + (isPremiere ? "Yes" : "No") +
                ", Title: " + title +
                ", Length: " + length + " minutes";
    }

    public void addReservation(String ticketNumber, ArrayList<String> reservedSeats) {
        reservations.put(ticketNumber, reservedSeats);
    }
    public Map<String, List<String>> getReservations() {
        return reservations;
    }
    
    public Movie(String date, int cinemaNumber, String time, boolean isPremiere, String title, int length) {
        this.date = date;
        this.cinemaNumber = cinemaNumber;
        this.time = time;
        this.isPremiere = isPremiere;
        this.title = title;
        this.length = length;
        this.seats = new boolean[NUM_ROWS][NUM_COLUMNS]; // Initialize the seat availability
        this.totalPrice = 0.0;
        this.reservations = new HashMap<>(); // Initialize the map of reservations

    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public int getCinemaNumber() {
        return cinemaNumber;
    }
    public void setCinemaNumber(int cinemaNumber) {
        this.cinemaNumber = cinemaNumber;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public boolean isPremiere() {
        return isPremiere;
    }
    public void setPremiere(boolean isPremiere) {
        this.isPremiere = isPremiere;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getLength() {
        return length;
    }
    public void setLength(int length) {
        this.length = length;
    }
    public boolean[][] getSeats() {
        return seats;
    }
    public boolean[][] setSeats() {
        return this.seats;
    }
    public double getTotalPrice() {
        return totalPrice;
    }
    public double setTotalPrice(Double totalPrice) {
        return this.totalPrice;
    }
    public String getTicketNumber() {
        return ticketNumber;
    }
    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }
}
    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }
}
