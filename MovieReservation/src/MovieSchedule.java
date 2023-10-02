import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MovieSchedule {
    public void CSVReader(){
        String csvFile = "C:/Users/iramonroe.tabora/JavaProject/MovieReservation/MovieReservation/10-2-2023-MovieSchedule.csv";
        String line = "";
        String csvSplitBy = ",";{

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] values = line.split(csvSplitBy);
                System.out.println("Values [column-1= " + values[0] + " , column-2=" + values[1] + "]");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
}
