import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class RainfallData {

    //VARIABLES city month year rainfall
    private String path;
    private String _month;
    private int _year;
    private double _rainfall;

    public String getMonth() {
        return _month;
    }
    public double getRainfall() {
        return _rainfall;
    }


    //CONSTRUCTOR
    // takes the filename as a parameter.
   public RainfallData(String path) {
        this.path=path;
    }

    //STRUCT of each data point
    public RainfallData(String month, int year, double rainfall) {
        _month = month;
        _year = year;
        _rainfall = rainfall;
    }





    //Method to grab the file path
    //Read in the file line by line
    //Split the data points of each line into a struct
    //Store structs into an array
    public ArrayList<RainfallData> ReadFile() throws FileNotFoundException {

        ArrayList<RainfallData> fileArray = new ArrayList<>();
        Scanner fileReader = new Scanner(new FileInputStream(path));
        //Reads in lines
        while (fileReader.hasNext()) {
            //Split a line
            //s+ takes into account all esc sequences (tab, space, /) and splits based on this
                String[] line = (fileReader.nextLine()).split("\\s+");

                //Take care of the first line which is the city, so it doesn't go into the struct
                if(line.length > 2) {
                    //Assign data points in struct
                    String month = line[0];
                    int year = Integer.parseInt(line[1]);
                    double rainfall = Double.parseDouble(line[2]);

                    //Create struct
                    RainfallData data = new RainfallData(month, year, rainfall);

                    //Add struct to array
                    fileArray.add(data);
                }
            }
        return fileArray;

        }

}












