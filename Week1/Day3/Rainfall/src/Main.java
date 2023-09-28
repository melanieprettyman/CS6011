import java.io.*;
import java.util.ArrayList;
import java.util.Scanner; 

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    /**
     * Calculates the average rainfall for a specific month from a given list of rainfall data.
     *dataArray -> An ArrayList of RainfallData objects containing the rainfall data.
     *month->The month for which the average rainfall is to be calculated.
     *The average rainfall amount (in inches) for the specified month.
     */
    public static double getAverageRainfallForAMonth(ArrayList<RainfallData> dataArray, String month) {
        double totalRainfall = 0.0;
        int monthCounter = 0;

        // Loop through the list of rainfall data
        for (RainfallData data : dataArray) {
            // Check if the month of the current data matches the specified month
            if (data.getMonth().equals(month)) {
                totalRainfall += data.getRainfall(); // Add the rainfall amount for this data point
                monthCounter++; // Increment the counter for this month
            }
        }
        // Calculate and return the average rainfall for the specified month
            return totalRainfall / monthCounter;
    }

    /**
     * Writes the average rainfall for each month to a specified output file.
     * outputPath -> The path to the output file where the results will be written.
     * dataArray->An ArrayList of RainfallData objects containing the rainfall data.
     */
    public static void writeAverageRainfallToFile(String outputPath, ArrayList<RainfallData> dataArray) {
        try {
            FileWriter writer = new FileWriter(outputPath);

            // Array of month names
            String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

            // Iterate through each month and calculate the average rainfall, then write it to the file
            for (String month : months) {
                double average = getAverageRainfallForAMonth(dataArray, month);
                writer.write("Average rainfall for " + month + ": " + average + " inches.\n");
            }

            writer.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }



    public static void main(String[] args) throws IOException {

        //File path for the file to open
        String filepath = "/Users/melanieprettyman/Desktop/MSD/CS6011/CS6011/Week1/Day3/Rainfall/src/Atlanta.txt"; // Open file

        //Create a new object, reads in data
        RainfallData atlantaData = new RainfallData(filepath);

        //Create an array of data structs
        ArrayList<RainfallData> dataList = atlantaData.ReadFile();

        //Create file path for outputted file
        String outputPath = "/Users/melanieprettyman/Desktop/MSD/CS6011/CS6011/Week1/Day3/Rainfall/src/rainfall_results.txt"; // Open file

        //wrtie to outputted file
        writeAverageRainfallToFile(outputPath, dataList);

    }

}























