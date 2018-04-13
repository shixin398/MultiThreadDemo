package com.example.spreadtrumshitaoli.threaddemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.util.concurrent.locks.ReentrantLock;

import static com.example.spreadtrumshitaoli.threaddemo.MainActivity.TAG;

/**
 * Created by SPREADTRUM\shitao.li on 18-3-30.
 * this is a exsample
 *You save money in the bank, but your wife take your bank money for shopping.
 * you need to synchronize the bank count.
 *
 * Notify volatile is useless: non-atomic.
 * 1.synchronized funtion
 * 2.synchronized code block
 * 3.ReentrantLock: diff from synchronized, cost more, but can stop at where you want.
 */

public class Synchronization extends Activity {

    private Bank mBank;
    private Thread saveThread;
    private Thread takeThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

        //start save and take
        saveThread.start();
        takeThread.start();


        //if bank is ok, the money should be:1000 + 1000*1 - 500*1 = 1500;
        //but whitout synchronazition, the result will be unexception. One of the result is :ThreadDemo:  money is : 1506
        try {
            saveThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            takeThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.d(TAG," money is : "+mBank.mMonkey);

    }

    private void init() {
        mBank = new Bank(1000);
        saveThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int i =0;
                while (i++<1000) {
                    try {
                        Thread.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mBank.saveMoney(1);
                }
                Log.d(TAG," saveMoney end I: "+i);
            }
        });

        takeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (i++<500) {
                    try {
                        Thread.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mBank.takeMoney(1);
                }
                Log.d(TAG," takemonkey end I: "+i);
            }
        });
    }


    private class Bank {

        private int mMonkey;
        private ReentrantLock lock = new ReentrantLock();

        public Bank(int initMoney) {
            mMonkey = initMoney;
        }

        public void saveMoney(int money) {
//        public synchronized void saveMoney(int money) { //1.synchronized the function
//            synchronized (this) {//2.synchronized code block
//            lock.lock();//3.ReentrantLock
                mMonkey = mMonkey + money;
//            lock.unlock();
//            }
        }

        public void takeMoney(int money) {
//        public synchronized void takeMoney(int money) {//1.synchronized the function
//            synchronized (this) {//2.synchronized code block
//            lock.lock();
                mMonkey = mMonkey - money;
//            lock.unlock();
//            }
        }

        public int checkMonkey() {
            return mMonkey;
        }
    }
}
