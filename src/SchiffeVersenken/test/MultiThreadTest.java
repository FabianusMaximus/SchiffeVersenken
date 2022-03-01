package SchiffeVersenken.test;

public class MultiThreadTest implements Runnable {
    private int threadNumber;

    public MultiThreadTest(int index){
        threadNumber = index;
    }
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("From Thread " + threadNumber + ": " + i);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            MultiThreadTest myThing = new MultiThreadTest(i);
            Thread myThread = new Thread(myThing);
            myThread.start();
        }

    }
}
