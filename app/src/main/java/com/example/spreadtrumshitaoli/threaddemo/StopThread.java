package com.example.spreadtrumshitaoli.threaddemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import static com.example.spreadtrumshitaoli.threaddemo.MainActivity.TAG;

/**
 * Created by SPREADTRUM\shitao.li on 18-3-29.
 *
 *   The way to stop a thread:
 *   1. while(flag)
 *   2.interrupt
 *   3.stop, but do not use it.
 *
 * Notify:way 2 cost more than 1, 1 is better performance.
 *
 *
 */

public class StopThread extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Task task = new Task();
        Thread thread = new Thread(task);
        thread.start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.d(TAG,"thread task i: "+task.i);
        //way 2
        //thread.interrupt();
        //way 1
        task.running = false;
        Log.d(TAG,"exit first main thread i: "+task.i);
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG,"Really exit main thread i: "+task.i+". Do your thread exit?");
    }



    public class Task implements Runnable {

        volatile boolean running = true;//you can try boolean running = true; if you exit current activty, and enter again, the thread will no exit.
        int i = 0;
        @Override
        public void run() {
            //way 2
//            while (!Thread.interrupted()) {
            // way 1
            while (running) {
                i++;
            }
            Log.d(TAG, "exit task "+i);
        }
    }

}
