package BasicThreads;

public class SimpleThreads {

    // Display a message, preceded by the name of the current thread
    static void threadMessage(String message) {
        String threadName = Thread.currentThread().getName();
        System.out.format("%s: %s%n", threadName, message);
    }

    // Make this class a thread class
    private static class MessageLoop ... {
        @Override
        public void run() {
            String importantInfo[] = {
                "First man eats an orange",
                "Second woman eats an orange",
                "Third boy eats an orange",
                "Forth girl will eat an orange too."
            };
            
            // print each sentence in the array using the threadMessage() above, 
            // but wait for 4 seconds between each message
            //...
            //...
        }
    }

    public static void main(String args[])
        throws InterruptedException {

        // Delay, in milliseconds before we interrupt MessageLoop
        // thread (default one min).
        long patience = 1000 * 30;
        //patience = 5000; // แล้วลองเปลี่ยนค่า patience เป็น 5000 ดูว่าจะเป้นยังไงนะ

        threadMessage("Starting MessageLoop thread");
        long startTime = System.currentTimeMillis();
        
        // create and start running a thread of MessageLoop object
        // t = ...
        
        
        threadMessage("Waiting for MessageLoop thread to finish");

        // loop until MessageLoop thread exits
        int count = 1;
        while (t.isAlive()) {
            threadMessage("Still waiting... " + count++);

            // sleep for 1 second
            // ...

            if ( (System.currentTimeMillis() - startTime > patience)
                  && t.isAlive() ) {
                threadMessage("Tired of waiting!");
                //...
            }
        }
        threadMessage("Finally!");
    }
}