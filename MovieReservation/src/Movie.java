public class Movie {
    private String date;
    private int cinemaNumber;
    private String time;
    private boolean isPremiere;
    private String title;
    private int length;

    
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
}