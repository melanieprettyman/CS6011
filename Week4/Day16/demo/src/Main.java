import java.util.ArrayList;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    //main is a thread, and java has a bunch of threads running that we don't see so the threads start at 21
    public static void main(String[] args) {

        MyRunnable runnable = new MyRunnable();

//        ArrayList<Thread> myThread = new ArrayList<>();
//        myThread.add(new Thread(runnable));

        for (int i = 0; i<5; i++){
            Thread t = new Thread(runnable);
            t.start();
        }

    }
}