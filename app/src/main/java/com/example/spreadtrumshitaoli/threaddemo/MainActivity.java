package com.example.spreadtrumshitaoli.threaddemo;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends Activity {

    public final static String TAG = "ThreadDemo";

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.list_thread);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), aClass[position]);
                startActivity(intent);
            }
        });


        mListView.setAdapter(new ArrayAdapter(this, R.layout.simple_list_item, data));

    }



    private String data[] = {
            "The way to start Thread",
            "Use Semaphore",
            "Stop Thread",
            "Synchronization",
            "Try Executor"
    };

    private Class aClass[] = {
            StartThread.class,
            UseSemaphore.class,
            StopThread.class,
            Synchronization.class,
            TryExecutor.class
    };

}
