import java.util.Scanner;
public class MovieReservationSystem {
    public static void main(String[] args) throws Exception {
     Scanner scanner = new Scanner(System.in);
     System.out.println("Good Day our fellow customers! Welcome to AWS Cinemas.");
     System.out.println("Please choose whithin the 3 options: \n 1. Display Movie Lists \n 2. Display Available Seats \n 3. Reserve Seats");
     String input = scanner.nextLine();

     switch(input){
        case "1":
            MovieSchedule MovieScheduleInstance = new MovieSchedule();
            MovieScheduleInstance.CSVReader();
            break;
            // Add cases for 2 and 3 here
        case "2":
        SeatReservation system = new SeatReservation(10);
        system.startReservation();
        break;
     }

    }
}
