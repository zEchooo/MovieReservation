public class Movie {
    private String date;
    private int cinemaNumber;
    private String time;
    private boolean isPremiere;
    private String title;
    private int length;
    private boolean[][] reservedSeats;

    @Override
    public String toString() {
        return "Date: " + date +
                ", Cinema Number: " + cinemaNumber +
                ", Time: " + time +
                ", Premiere: " + (isPremiere ? "Yes" : "No") +
                ", Title: " + title +
                ", Length: " + length + " minutes";
    }

    public Movie(String date, int cinemaNumber, String time, boolean isPremiere, String title, int length) {
        this.date = date;
        this.cinemaNumber = cinemaNumber;
        this.time = time;
        this.isPremiere = isPremiere;
        this.title = title;
        this.length = length;
        reservedSeats = new boolean[8][5]; // 8 rows, 5 seats per row
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

    
    public boolean isSeatAvailable(int row, int seat) {
        // Check if the seat is within valid row and seat number ranges
        if (row >= 0 && row < reservedSeats.length && seat >= 0 && seat < reservedSeats[row].length) {
            // Check if the seat is available (not reserved)
            return !reservedSeats[row][seat];
        }

        // Invalid row or seat number
        return false;
    }

    public void reserveSeat(int row, int seat) {
        // Reserve the seat
        if (row >= 0 && row < reservedSeats.length && seat >= 0 && seat < reservedSeats[row].length) {
            reservedSeats[row][seat] = true;
        }
    }

    public boolean[][] getReservedSeats() {
        return reservedSeats;
    }
}