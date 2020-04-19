
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @from TutorialPoint URL:
 *       https://www.tutorialspoint.com/java/java_thread_synchronization.htm
 *       โปรแกรมนี้แสดงการใช้งาน synchronized สำหรับ Monitor ของ Java
 *       โปรแกรมนี้สร้าง thread ย่อยขึ้นสองเธร็ด คือ T1 กับ T2 แต่ละตัวเป็น
 *       class ThreadDemo ซึ่งใช้งาน Object PrintDemo ที่มี printCount()
 *       ร่วมกันเป็น critical method PrintCount() ทำงานเพียงแค่แสดงเลขถอยหลังจาก
 *       10 ถึง 1
 * 
 *       งานคือ... ให้ทำความเข้าใจโปรแกรมนี้ ให้ดูที่บรรทัดที่ประมาณ 71
 *       ลองกำหนดค่า true หรือ false ในเงื่อนไข if(...) เพื่อกำหนดว่าจะให้ใช้
 *       Monitor's synchronized หรือไม่ เมื่อทดลองทั้ง true และ false แล้ว
 *       ท่านอธิบายผลลัพธ์ว่าแตกต่างกันอย่างไร และเพราะเหตุใด สรุปได้ว่า
 *       synchronized ของ Java ทำหน้าที่อะไร
 * 
 */
public class Monitor {

    public static void main(String args[]) {

        PrintDemo PD = new PrintDemo();

        ThreadDemo T1 = new ThreadDemo("One", PD);
        ThreadDemo T2 = new ThreadDemo("Two", PD);

        T1.start();
        T2.start();

        // wait for threads to end
        try {
            T1.join();
            T2.join();
        } catch (InterruptedException e) {
            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}

class PrintDemo {

    final int MAX = 10;

    public void printCount(String threadName) {
        try {
            for (int i = MAX; i > 0; i--) {
                System.out.println(threadName + " Counter   ---   " + i);
                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            Logger.getLogger(PrintDemo.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}

class ThreadDemo extends Thread {

    private Thread t;
    final private String threadName;
    PrintDemo pd;

    ThreadDemo(String name, PrintDemo pd) {
        threadName = name;
        this.pd = pd;
    }

    @Override
    public void run() {

        /*******************************************************************
         * Choose one of the following two ways by changing the boolean T/F
         *******************************************************************/
        if (false) {
            synchronized (pd) { // by using monitor
                pd.printCount(threadName);
            }
        } else {
            pd.printCount(threadName); // without using monitor
        }
        System.out.println("Thread " + threadName + " exiting.");
    }

    @Override
    public void start() {
        System.out.println("Starting " + threadName);
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }
}
