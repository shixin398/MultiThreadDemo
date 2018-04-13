package com.example.spreadtrumshitaoli.threaddemo;

import android.app.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import static com.example.spreadtrumshitaoli.threaddemo.MainActivity.TAG;


/**
 * Created by SPREADTRUM\shitao.li on 18-3-29.
 */

public class StartThread extends Activity {

    private Button mButtonThread;
    private Button mButtonRunnable;
    private Button mButtonHandler;
    private Handler mHandler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_thread);

        mButtonThread = (Button) findViewById(R.id.bt_thread);
        mButtonThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startThread();
            }
        });

        mButtonRunnable = (Button) findViewById(R.id.bt_runnable);
        mButtonRunnable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRunnable();
            }
        });

        mButtonHandler = (Button) findViewById(R.id.bt_handler);
        mButtonHandler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startHandler();
            }
        });

    }

    private void startThread() {
        new  Thread() {
            int i =0;
           @Override
           public void run() {
//               super.run();
               while (i++<10) {
                   Log.d(TAG,"Use Thread  run now! "+i + "Tid: "+ Thread.currentThread().getId());
               }
           }
       }.start();
    }

    private void startRunnable() {
        new Thread(
                new Runnable() {
                    int i = 0;
                    @Override
                    public void run() {
                        while (i++<10) {
                            Log.d(TAG,"Use Runnable  run now! "+i+ "Tid: "+ Thread.currentThread().getId());
                        }
                    }
                }
        ).start();

    }

    private void startHandler() {
        //this is in main thread. better for handler--looper--message.
        mHandler = new Handler(getMainLooper());
        runnable = new Runnable() {
            int i = 0;
            @Override
            public void run() {
                while (i++<10)
                    Log.d(TAG,"Use Handler  run now! "+i+ "Tid: "+ Thread.currentThread().getId());
            }
        };

        mHandler.post(runnable);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(runnable);
    }
}
