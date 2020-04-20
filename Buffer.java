import java.util.logging.Level;
import java.util.logging.Logger;

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

    public synchronized void put(String ch) {

        while (BufferSize == MaxBuffSize) {
            try {
                wait();
            } catch (InterruptedException e) {
                Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        BufferEnd = (BufferEnd + 1) % MaxBuffSize;
        store[BufferEnd] = ch;
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
        String ch = store[BufferStart];
        BufferStart = (BufferStart + 1) % MaxBuffSize;
        BufferSize--;
        notifyAll();
        return ch;
    }
}

class Consumer extends Thread {

    private final Buffer buffer;

    public Consumer(Buffer b) {
        buffer = b;
    }

    public void run() {

        try {
            sleep(5000);
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
                    word = "End";
                    break;
            }
            if (word != "End") {
                System.out.println("consumer : got " + word + "\n");
            } else {
                break;
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
        int i = 0;

        while (true) {

            if (i != word.length) {

                buffer.put(word[i]);
                System.out.println("producer : put " + word[i]);
                i++;
            } else {
                try {
                    sleep(1000);
                    break;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }
    }
}

class BoundedBuffer {

    public static void main(String[] args) {

        System.out.println("program starting");
        Buffer buffer = new Buffer(4); // buffer has size 5
        Producer prod = new Producer(buffer);
        Consumer cons = new Consumer(buffer);
        prod.start();
        cons.start();
    }
}