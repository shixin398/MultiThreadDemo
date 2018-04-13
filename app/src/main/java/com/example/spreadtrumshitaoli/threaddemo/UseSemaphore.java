package com.example.spreadtrumshitaoli.threaddemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.util.concurrent.Semaphore;

import static com.example.spreadtrumshitaoli.threaddemo.MainActivity.TAG;
/**
 * Created by SPREADTRUM\shitao.li on 18-3-29.
 */

public class UseSemaphore extends Activity {

    private Semaphore semaphore = new Semaphore(5);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        for (int i = 0; i<10; i++) {
            Thread thread = new Thread(){
                @Override
                public void run() {
//                    super.run();
                    int j =  0;
                    try {
                        semaphore.acquire();
//                        semaphore.tryAcquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    while (j++<100) {
                        if (j==99 || j == 1) {
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Log.d(TAG, "current tid: "+Thread.currentThread().getId()+", j: "+j);
                        }
                    }
                    semaphore.release();
                }
            };
            thread.start();
        }
    }
}
/*
* Out put:
* 03-29 15:14:02.713 31581-31616/com.example.spreadtrumshitaoli.threaddemo D/ThreadDemo: current tid: 446, j: 1
03-29 15:14:02.712 31581-31617/com.example.spreadtrumshitaoli.threaddemo D/ThreadDemo: current tid: 447, j: 1
03-29 15:14:02.713 31581-31619/com.example.spreadtrumshitaoli.threaddemo D/ThreadDemo: current tid: 449, j: 1
03-29 15:14:02.722 31581-31620/com.example.spreadtrumshitaoli.threaddemo D/ThreadDemo: current tid: 450, j: 1
03-29 15:14:02.725 31581-31621/com.example.spreadtrumshitaoli.threaddemo D/ThreadDemo: current tid: 451, j: 1
03-29 15:14:03.213 31581-31616/com.example.spreadtrumshitaoli.threaddemo D/ThreadDemo: current tid: 446, j: 99
03-29 15:14:03.215 31581-31617/com.example.spreadtrumshitaoli.threaddemo D/ThreadDemo: current tid: 447, j: 99
03-29 15:14:03.215 31581-31619/com.example.spreadtrumshitaoli.threaddemo D/ThreadDemo: current tid: 449, j: 99
03-29 15:14:03.222 31581-31620/com.example.spreadtrumshitaoli.threaddemo D/ThreadDemo: current tid: 450, j: 99
03-29 15:14:03.226 31581-31621/com.example.spreadtrumshitaoli.threaddemo D/ThreadDemo: current tid: 451, j: 99
03-29 15:14:03.717 31581-31622/com.example.spreadtrumshitaoli.threaddemo D/ThreadDemo: current tid: 452, j: 1
03-29 15:14:03.718 31581-31623/com.example.spreadtrumshitaoli.threaddemo D/ThreadDemo: current tid: 453, j: 1
03-29 15:14:03.718 31581-31618/com.example.spreadtrumshitaoli.threaddemo D/ThreadDemo: current tid: 448, j: 1
03-29 15:14:03.724 31581-31624/com.example.spreadtrumshitaoli.threaddemo D/ThreadDemo: current tid: 454, j: 1
03-29 15:14:03.728 31581-31625/com.example.spreadtrumshitaoli.threaddemo D/ThreadDemo: current tid: 455, j: 1
03-29 15:14:04.218 31581-31622/com.example.spreadtrumshitaoli.threaddemo D/ThreadDemo: current tid: 452, j: 99
03-29 15:14:04.219 31581-31623/com.example.spreadtrumshitaoli.threaddemo D/ThreadDemo: current tid: 453, j: 99
03-29 15:14:04.220 31581-31618/com.example.spreadtrumshitaoli.threaddemo D/ThreadDemo: current tid: 448, j: 99
03-29 15:14:04.225 31581-31624/com.example.spreadtrumshitaoli.threaddemo D/ThreadDemo: current tid: 454, j: 99
03-29 15:14:04.229 31581-31625/com.example.spreadtrumshitaoli.threaddemo D/ThreadDemo: current tid: 455, j: 99
* */