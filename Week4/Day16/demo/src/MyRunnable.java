public class MyRunnable implements Runnable {
    @Override
    public void run() {
        for (int count = 0; count < 20; count++) {
            System.out.println("Hello from thread #" + Thread.currentThread().threadId());
        }
    }
    //gets from runnable class, interface
    //wrapper class
}
//don't know how long a thread will run and what order they will run