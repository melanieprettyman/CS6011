// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.


import java.util.Arrays;
import java.util.Scanner;

public class Main {


    //Function to find the sum of an Array
    //Loops through each value in the array, and finds/returns the sums.
    public static int findSumOfArray(int[] array) {
        int sum = 0;
        for (int value : array) {
            sum += value;
        }
        return sum;
    }

    //Function to determine if user is able to vote
    public static boolean canVote(int age){
        return (age>=18);
    }


    //Function to print out the users generation
    public static void findGeneration(int age){
        if (age >= 76) {
            System.out.println(" You are the Greatest Generation");

        } else if (age >= 57) {
            System.out.println("You are a Baby Boomer");
        } else if (age >= 41) {
            System.out.println("You are Generation X");
        } else if (age >= 26) {
            System.out.println("You are a Millennial");
        } else {
            System.out.println("You are Generation Z");
        }

        }




    public static void main(String[] args) {
        // Press Opt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        System.out.print("Hello world!\n");

        // Press Ctrl+R or click the green arrow button in the gutter to run the code.
        // Press Ctrl+D to start debugging your code. We have set one breakpoint
        // for you, but you can always add more by pressing Cmd+F8.


        //----------PART 1------------//

        //Initialize myArray
        int[] myArray;
        //Add numbers to myArray
        myArray = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        //Print contents of myArray
        System.out.println(Arrays.toString(myArray));

        //Call function findSumOfArray and pass myArray as argument
        System.out.println(findSumOfArray(myArray));



        //----------PART 2------------//


 /*Finally, ask a user to input their name and age.
        Then, print whether they are old enough to vote,
         and which generation they belong to (iGen, millennial, gen X, boomers, greatest generation).
         */

        // Create a Scanner object
        Scanner person = new Scanner(System.in);
        System.out.println("Enter your name and age:");

        String name = person.nextLine();  // Read username input
        int age = Integer.parseInt(person.nextLine());  // Read user age input

//Calling can vote function
        if (canVote(age)){
            System.out.println(name + "you are old enough to vote!");  // Output user input
        }
        else{
            System.out.println(name + "you are not old enough to vote!");  // Output user input
        }

//Calling findGeneration function
        findGeneration(age);

    }





}
