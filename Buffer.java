import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Random;

/**
 *
 * @author User PC
 */
public class Buffer {
    private final int MaxBuffSize;
    private String[] store;
    private int BufferStart, BufferEnd, BufferSize;

    public Buffer(int size) {
        MaxBuffSize = size;
        BufferEnd = -1;
        BufferStart = 0;
        BufferSize = 0;
        store = new String[MaxBuffSize];
    }

    public synchronized void insert(String ch) {
        try {
            while (BufferSize == MaxBuffSize) {
                wait();
            }
            BufferEnd = (BufferEnd + 1) % MaxBuffSize;
            store[BufferEnd] = ch;
            BufferSize++;
            notifyAll();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public synchronized String delete() {
        try {
            while (BufferSize == 0) {
                wait();
            }
            String ch = store[BufferStart];
            BufferStart = (BufferStart + 1) % MaxBuffSize;
            BufferSize--;
            notifyAll();
            return ch;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "END";
        }
    }
}

class Consumer extends DoWork {

    private final Buffer buffer;

    public Consumer(Buffer b) {
        buffer = b;
    }

    public void run() {

        try {
            sleep(10000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
        }

        while (!Thread.currentThread().isInterrupted()) {
            String work = buffer.delete();
            System.out.println("\n\nFrom HOUSEMAID : " + work);
            work();
        }
    }
}

class Producer extends Thread {
    private final Buffer buffer;

    Scanner sc = new Scanner(System.in);

    public Producer(Buffer b) {
        buffer = b;
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            System.out.print("From MASTER : ");
            String work = sc.nextLine();
            if ("-1".equals(work))
                break; // -1 is eof
            buffer.insert(work);
        }
    }
}

class DoWork extends Thread {

    public static void work() {

        Random rand = new Random();
        int time = rand.nextInt(6) + 5;

        System.out.print("\n   This work use " + time + " seconds to finish\n");

        for (int i = 1; i <= time; i++) {
            try {
                sleep(1000);
                System.out.print("\n    Still working... " + i + " second");
            } catch (InterruptedException ex) {
                Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        try {
            sleep(1000);
            System.out.print("\n    Finish !!\n");
            sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}

class BoundedBuffer {

    public static void main(String[] args) {

        System.out.println("program starting");
        Buffer buffer = new Buffer(5); // buffer has size 5
        Producer prod = new Producer(buffer);
        Consumer cons = new Consumer(buffer);
        prod.start();
        cons.start();

        try {
            prod.join();
            cons.interrupt();
        } catch (InterruptedException e) {
        }

        System.out.println("End of Program");
    }
}