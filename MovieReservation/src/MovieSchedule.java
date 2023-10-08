import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MovieSchedule {
    private ArrayList<Movie> movies;
    private String csvFilePath;

    public MovieSchedule(String csvFilePath) {
        this.csvFilePath = csvFilePath;
        this.movies = new ArrayList<>();
        loadMoviesFromCSV();
    }

    private void loadMoviesFromCSV() {
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    String date = parts[0].trim();
                    int cinemaNumber = Integer.parseInt(parts[1].trim());
                    String time = parts[2].trim();
                    boolean isPremiere = Boolean.parseBoolean(parts[3].trim());
                    String title = parts[4].trim();
                    int length = Integer.parseInt(parts[5].trim());

                    Movie movie = new Movie(date, cinemaNumber, time, isPremiere, title, length);
                    movies.add(movie);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading movies from CSV file: " + e.getMessage());
        }
    }

    public void displayMovieSchedule(String dateToDisplay) {
        System.out.println("Movie Schedule for Date: " + dateToDisplay);
        boolean found = false;
        int i = 1;
        for (Movie movie : movies) {
            if (movie.getDate().equals(dateToDisplay)) {
                System.out.println(i + "||"+movie.getDate()+ "||"+movie.getCinemaNumber() +"||"+movie.getTime()+"||"+  movie.getTitle() + "(" + (movie.isPremiere() ? "Premiere" : "Regular") + ")");
                found = true;
                i++;
            }
        }
    
        if (!found) {
            System.out.println("No movies found for the specified date.");
        }
    }
    public Movie getMovieByIndex(String dateToDisplay, int index) {
        int i = 1;
        for (Movie movie : movies) {
            if (movie.getDate().equals(dateToDisplay)) {
                if (i == index) {
                    return movie;
                }
                i++;
            }
        }
        return null; // Index out of bounds or no movie found for the specified date
    }
    public List<String> getAvailableDates() {
        Set<String> uniqueDates = new HashSet<>();
        for (Movie movie : movies) {
            uniqueDates.add(movie.getDate());
        }
        return new ArrayList<>(uniqueDates);
    }
    public ArrayList<Movie> getMovies() {
        return movies;
    }
        public int getNumberOfElements(String dateToReserve) {
        int count = 0;
        for (Movie movie : movies) {
            if (movie.getDate().equals(dateToReserve)) {
                count++;
            }
        }
        return count;
    }

    
}
