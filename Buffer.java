import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Shutdownz 22C
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

    public synchronized void put(String num) {

        while (BufferSize == MaxBuffSize) {
            try {
                wait();
            } catch (InterruptedException e) {
                Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        BufferEnd = (BufferEnd + 1) % MaxBuffSize;
        store[BufferEnd] = num;
        BufferSize++;
        notifyAll();
    }

    public synchronized String got() {

        while (BufferSize == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        String word = store[BufferStart];
        BufferStart = (BufferStart + 1) % MaxBuffSize;
        BufferSize--;
        notifyAll();
        return word;
    }
}

class Consumer extends Thread {

    private final Buffer buffer;

    public Consumer(Buffer b) {
        buffer = b;
    }

    public void run() {

        try {
            sleep(3000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
        }

        String word;

        while (true) {

            String work = buffer.got();
            switch (work) {
                case "0":
                    word = "Zero";

                    break;
                case "1":
                    word = "One";

                    break;
                case "2":
                    word = "Two";

                    break;
                case "3":
                    word = "Three";

                    break;
                case "4":
                    word = "Four";

                    break;
                case "5":
                    word = "Five";

                    break;
                case "6":
                    word = "Six";

                    break;
                case "7":
                    word = "Seven";

                    break;
                case "8":
                    word = "Eigth";

                    break;
                case "9":
                    word = "Nine";

                    break;

                default:
                    word = "";
                    break;
            }
            System.out.println("Consumer\trunning\t\t--\t\t" + word);
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Producer extends Thread {
    private final Buffer buffer;

    public Producer(Buffer b) {
        buffer = b;
    }

    public void run() {
        String num = "123456789";
        String[] word = num.split("");

        while (true) {

            for (int i = 0; i < word.length; i++) {
                buffer.put(word[i]);
                System.out.println("Producer\trunning\t\t" + word[i] + "\t\t--");
            }

            break;

        }

        System.out.println("Producer\tEND\t\t--\t\t--");
    }
}

class BoundedBuffer {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("program starting\n");
        System.out.println("Thread\t\tStatus\t\tPut\t\tGot\n");
        Buffer buffer = new Buffer(4);
        Producer prod = new Producer(buffer);
        Consumer cons = new Consumer(buffer);

        prod.start();
        cons.start();
    }
}