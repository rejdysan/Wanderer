import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Test {
    public static void main(String[] args) {
// creating timer task, timer
        Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                System.out.println("hello");
            }
        };
        t.scheduleAtFixedRate(tt, new Date(), 1000);
    }
}
