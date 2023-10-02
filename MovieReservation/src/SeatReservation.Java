import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SeatReservation {
    private boolean[] seats;
    private int ticketNum = 0;

    public SeatReservation(int totalSeats) {
        seats = new boolean[totalSeats];
    }

    public boolean isSeatAvailable(int seatNum) {
        return !seats[seatNum];
    }

    public void reserveSeat(int seatNum) {
        seats[seatNum] = true;
    }

    public void startReservation() throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter seat numbers (comma separated):");
        String[] seatNums = scanner.nextLine().split(",");

        for (String seatNum : seatNums) {
            int num = Integer.parseInt(seatNum.trim());
            if (!isSeatAvailable(num)) {
                System.out.println("This Seat is taken, choose another seat");
                return;
            }
        }

        for (String seatNum : seatNums) {
            int num = Integer.parseInt(seatNum.trim());
            reserveSeat(num);
        }

        System.out.println("Is this a premier show? (yes/no)");
        String premier = scanner.nextLine();
        double ticketPrice;
        if (premier.equalsIgnoreCase("yes")) {
            ticketPrice = 500;
        } else {
            System.out.println("Is the customer a senior? (yes/no)");
            String senior = scanner.nextLine();
            if (senior.equalsIgnoreCase("yes")) {
                ticketPrice = 150 * 0.2;
            } else {
                ticketPrice = 350;
            }
        }

        double totalPrice = ticketPrice * seatNums.length;

        System.out.println("Confirm booking? (yes/no)");
        String confirm = scanner.nextLine();
        if (confirm.equalsIgnoreCase("yes")) {
            ticketNum++;
            String ticketDetails = "Ticket Num: " + ticketNum + ", Showing Date: TBD, Showing Time: TBD, Seat Num: " + String.join(", ", seatNums) + ", Total Price: " + totalPrice;

            FileWriter writer = new FileWriter("tickets.csv", true);
            writer.write(ticketDetails + "\n");
            writer.close();

            System.out.println(ticketDetails);
            System.out.println("Booking confirmed!");
        } else {
            System.out.println("Booking not confirmed.");
        }

        scanner.close();
    }
}