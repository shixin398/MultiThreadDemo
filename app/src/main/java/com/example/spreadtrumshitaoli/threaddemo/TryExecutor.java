package com.example.spreadtrumshitaoli.threaddemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.example.spreadtrumshitaoli.threaddemo.MainActivity.TAG;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Created by SPREADTRUM\shitao.li on 18-4-2.
 */

public class TryExecutor extends Activity {

    private int QueueSize = 10;

    private int mCoreSize;
    private int mMaxSize;
    private int mKeepAliveTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

    }

    private void init() {

        mCoreSize = Runtime.getRuntime().availableProcessors();// cpu core count;
        Log.d(TAG, "Current avaliable processor is: "+mCoreSize);
        mMaxSize = 2 * mCoreSize;// max poor size;
        mKeepAliveTime = 10;//alive time;

        //new ThreadPoolExecutor---ArrayBlockingQueue(FIFO)--which you must set queue size.
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(mCoreSize, mMaxSize,
                mKeepAliveTime, TimeUnit.SECONDS, new ArrayBlockingQueue <Runnable>(QueueSize));

        //new ThreadPoolExecutor--LinkedBlockingDeque(link FIFO)--which you can set queue size or not, if not the size is Integer.MAX_VALUE.
//        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(mCoreSize, mMaxSize, mKeepAliveTime, TimeUnit.SECONDS,
//                new LinkedBlockingDeque <Runnable>());//new LinkedBlockingDeque <Runnable>(QueueSize));

        //new ThreadPoolExecutor--SynchronousQueue(NO queue).
//        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(mCoreSize, mMaxSize, mKeepAliveTime, TimeUnit.SECONDS,
//                new SynchronousQueue <Runnable>());


        //Solve the reject error:
        //1. do noting == AbortPolicy:throw exception.
        //2.DiscardPolicy:drop
        //3.DiscardOldestPolicy: drop the oldeset one
        //4.CallerRunsPolicy: the thread who make executor handle. In this activity, it's the main thread.
        //5. new RejectedExecutionHandler to handle exception.
//        threadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());

//        threadPoolExecutor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
//            @Override
//            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
//                //TODO:
//            }
//        });

        //four executor for simple use:
        //1.newFixedThreadPool
        /*
        * public static ExecutorService newFixedThreadPool(int nThreads) {
        *        return new ThreadPoolExecutor(nThreads, nThreads,
        *               0L, TimeUnit.MILLISECONDS,
        *               new LinkedBlockingQueue<Runnable>());
        *       }
        */
        //2.newSingleThreadExecutor
        /*
        * public static ExecutorService newSingleThreadExecutor() {
        *        return new ThreadPoolExecutor(1, 1,
        *               0L, TimeUnit.MILLISECONDS,
        *               new LinkedBlockingQueue<Runnable>());
        *       }
        */
        //3.newCachedThreadPool
        /*
        * public static ExecutorService newCachedThreadPool() {
        *        return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
        *               60L, TimeUnit.SECONDS,
        *               new SynchronousQueue<Runnable>());
        *       }
        * */
        //4.newScheduledThreadPool
         /*
        * public static ExecutorService ScheduledThreadPoolExecutor(int corePoolSize) {
        *        return new ThreadPoolExecutor(corePoolSize, Integer.MAX_VALUE,
        *               10L, TimeUnit.MILLISECONDS,
        *               new DelayedWorkQueue());
        *       }
        * */
//        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(mCoreSize);
//        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
//        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
//        ExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(mCoreSize);


        int i = 0;
        while (i++<15) {
            MyTask task = new MyTask(i);
            threadPoolExecutor.execute(task);

//            fixedThreadPool.execute(task);
//            singleThreadExecutor.execute(task);
//            cachedThreadPool.execute(task);
//            scheduledThreadPool.execute(task);

            Log.d(TAG, "current core pool size: "+threadPoolExecutor.getCorePoolSize() +
                    ", completed task count: "+threadPoolExecutor.getCompletedTaskCount() +
                    ", active count: "+threadPoolExecutor.getActiveCount() +
                    ", pool size: "+threadPoolExecutor.getPoolSize() +
                    ", task count: "+threadPoolExecutor.getTaskCount());
        }

        threadPoolExecutor.allowsCoreThreadTimeOut();
        while (threadPoolExecutor.getActiveCount() != 0) {
            Thread.yield();
        }
        threadPoolExecutor.shutdownNow();
        Log.d(TAG, "Shut down!");
    }


    class MyTask implements Runnable {

        private int index;

        public MyTask(int index) {
            this.index = index;
        }

        @Override
        public void run() {
            try {
                Thread.currentThread().sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d(TAG,"current thread is: "+Thread.currentThread().getName() + ", index: "+index);
        }
    }

}
