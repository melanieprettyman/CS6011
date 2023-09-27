import java.io.*;
import java.util.ArrayList;
import java.util.Scanner; 

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    //Function to find the average rainfall for a given month
    //input array and month
    static double getAverageRainfallForAMonth(ArrayList<RainfallData> arrayOfData, String month) {
        double totalRainfall = 0.0;
        int monthCounter = 0;

        //Loop through array
        for (int i = 0; i < arrayOfData.size(); i++) {
            //If the month equals the month inputted, get the rainfall for that struct in the array
            //and add it to the totalRainfall
            //and add 1 to month counter
            if (arrayOfData.get(i).getMonth().equals(month)) {
                totalRainfall += arrayOfData.get(i).getRainfall();
                monthCounter++;
            }
        }
        //return average
        return totalRainfall / monthCounter;
    }

    public static void main(String[] args) throws IOException {

        //File path for the file to open
        String filepath = "/Users/melanieprettyman/Desktop/MSD/CS6011/CS6011/Week1/Day3/Rainfall/src/Atlanta.txt"; // Open file

        //Create a new object, reads in data
        RainfallData alantaData = new RainfallData(filepath);

        //Create an array of data structs 
        ArrayList<RainfallData> dataList = alantaData.ReadFile();

        //Call function that gets the average rainfall for a particular month.
        double avgerage_January = getAverageRainfallForAMonth(dataList, "January");
        double avgerage_February = getAverageRainfallForAMonth(dataList, "February");
        double avgerage_March = getAverageRainfallForAMonth(dataList, "March");
        double avgerage_April = getAverageRainfallForAMonth(dataList, "April");
        double avgerage_May = getAverageRainfallForAMonth(dataList, "May");
        double avgerage_June = getAverageRainfallForAMonth(dataList, "June");
        double avgerage_July = getAverageRainfallForAMonth(dataList, "July");
        double avgerage_August = getAverageRainfallForAMonth(dataList, "August");
        double avgerage_September = getAverageRainfallForAMonth(dataList, "September");
        double avgerage_October = getAverageRainfallForAMonth(dataList, "October");
        double avgerage_November = getAverageRainfallForAMonth(dataList, "November");
        double avgerage_December = getAverageRainfallForAMonth(dataList, "December");



        //PROGRAM OUTPUT

        //file name with path
        String filePath = "/Users/melanieprettyman/Desktop/MSD/CS6011/CS6011/Week1/Day3/Rainfall/src/rainfall_results.txt"; // Open file


        //write in file made above month averages
        try {
            FileWriter myWriter = new FileWriter(filePath);
            myWriter.write("Average rainfall for January: " + avgerage_January + " inches.\n");
            myWriter.write("Average rainfall for February: " + avgerage_February + " inches.\n");
            myWriter.write("Average rainfall for March: " + avgerage_March + " inches.\n");
            myWriter.write("Average rainfall for April: " + avgerage_April + " inches.\n");
            myWriter.write("Average rainfall for May: " + avgerage_May + " inches.\n");
            myWriter.write("Average rainfall for June: " + avgerage_June + " inches.\n");
            myWriter.write("Average rainfall for July: " + avgerage_July + " inches.\n");
            myWriter.write("Average rainfall for August: " + avgerage_August + " inches.\n");
            myWriter.write("Average rainfall for September: " + avgerage_September + " inches.\n");
            myWriter.write("Average rainfall for October: " + avgerage_October + " inches.\n");
            myWriter.write("Average rainfall for November: " + avgerage_November + " inches.\n");
            myWriter.write("Average rainfall for December: " + avgerage_December + " inches.\n");

            //close the file
            myWriter.close();

            //print a success message if this all happened
            System.out.println("Successfully wrote to the file.");

            //if the program wasn't able to write out to the file then print an error message
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}























