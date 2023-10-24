import java.lang.reflect.Array;
import java.util.ArrayList;


//Q1. What happens? Do all the threads run in order?
//No the threads do not run in order
//Q2. Run your program a couple of times - does the same thread always print the 1st hello? The last hello?
//1st run, first hello is from thread #21, last hello is from thread #39
//2nd run, first hello is from thread #21, last hello is from thread #39
//3rd run, first hello is from thread #21, last hello is from thread #39

//Q3.  This is because the threads are running concurrently and accessing the shared variable answer at
// the same time. Since there is no synchronization mechanism in place, the order in which the threads access
// answer is non-deterministic, leading to different results each time.



// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws InterruptedException {
   // sayHello();
    badSum();
    }
    static int answer = 0;
    static final int i = 0;
    public static void badSum() throws InterruptedException {
    int maxValue = 100;

    int correctAnswer=(maxValue * (maxValue - 1) / 2);

    MathRunnable runnable ;

//    ArrayList<Thread> myThread = new ArrayList<>(6);
        Thread[] myThread = new Thread[6] ;
    for (int i = 0; i < myThread.length; i++) {
        runnable = new MathRunnable( i,maxValue, myThread.length );

        myThread[i] = new Thread(runnable);

        myThread[i].start();

        myThread[i].join();
        answer = (int) runnable.answer;

    }
        System.out.println("My answer:" + answer + " Correct answer:" + correctAnswer);

    }


    public static void sayHello() throws InterruptedException {
        MyRunnable runnable = new MyRunnable();


        ArrayList<Thread> myThread = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(runnable);
            myThread.add(new Thread(runnable));
            t.start();
            t.join();
        }
    }
}

