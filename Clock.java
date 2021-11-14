import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.BooleanSupplier;

class Clock {
    protected int count;
    protected Timer timer;
    protected TimerTask task;
    protected SCModel sc;
    protected JLabel c0 = new JLabel("30",JLabel.CENTER); //メインシーン用
    protected JLabel c1 = new JLabel("スタートまで 5 秒",JLabel.CENTER); //カウントダウンシーン用
    protected boolean ifLast = false;

    public Clock(SCModel sc) {
        this.sc = sc;
    }

    /*タイマー表示用のsetter */
    public JLabel getClock0(){
        return c0;
    }
    public JLabel getClock1(){
        return c1;
    }

    /* タイマー表示を更新する */
    public void setClockLabel(int t) {
        c0.setText(""+t);
        c1.setText("スタートまで "+t+" 秒");
    }

    /*タイマーに対するタスク */
    protected void makeTimer(){
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                if(count>0){
                    count--;
                    System.out.println(count);
                    //c.setText(""+count);
                    setClockLabel(count);
                    sc.sendTime(count);
                } else {
                    System.out.println("timeout");
                    ifLast = true;
                    timer.cancel();
                }
            }
        };
    }

    /*現在のカウントを取得 */
    public int getCount(){
        return count;
    }

    /*カウントが0かどうかを取得 */
    public boolean ifLast(){
        return ifLast;
    }

    /*カウント開始 */
    public void start(int count){
        if(timer!=null){
            timer.cancel();
        }
        this.count = count;
        ifLast = false;
        makeTimer();
        timer.scheduleAtFixedRate(task, 1000, 1000);
    }
}

// class Test{//使い方の例です
//     static Clock cl;
//     public static void main(String[] args) {
//         cl = new Clock();
//         cl.start(5);
//         try {
//             Thread.sleep(10000);
//         } catch (InterruptedException e) {
//         }
//         cl.start(10);
//     }
// }
