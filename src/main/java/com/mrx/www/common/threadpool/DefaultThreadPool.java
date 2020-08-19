package com.mrx.www.common.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * DefaultThreadPool.
 *
 * @author Mei Ruoxiao
 * @since 2020/06/03
 */
@Slf4j
public class DefaultThreadPool implements Pool {
    /**
     * Use the blocking queue under the Java Concurrent library to do this,
     * so we don't need to do additional synchronization and blocking operations.
     * 使用java并发库下的阻塞队列来做,这样我们就不需要做额外的同步跟阻塞操作
     * jobs 任务队列,Runnable就是一个任务
     * workers 工作者队列
     * <p>
     * BlockingQueue是双缓冲队列。
     * BlockingQueue内部使用两条队列，允许两个线程同时向队列一个存储，一个取出操作。
     * 在保证并发安全的同时，提高了队列的存取效率。
     */
    private final BlockingQueue<Runnable> jobs = new LinkedBlockingQueue<>();
    private final BlockingQueue<Worker> workers = new LinkedBlockingQueue<>();

    /**
     * Initialize thread pool size.
     *
     * @param num num
     */
    public DefaultThreadPool(int num) {
        initPool(num);
    }


    private void initPool(int num) {
        for (int i = 0; i < num; i++) {
            Worker worker = new Worker();
            workers.add(worker);
            System.out.println("工作者:" + worker);
            worker.start();
        }
    }

    @Override
    public void execute(Runnable runnable) {
        /**
         * 将任务放入任务队列
         */
        if (runnable != null) {
            jobs.add(runnable);
        }
    }

    /**
     * It can be judged whether the task queue has been emptied through continuous cycles,
     * If the queue task is cleared, the worker queue's threads are stopped,
     * Break the cycle and clear the worker queue.
     * 通过不断的循环来判断,任务队列是否已经清空,
     * 如果队列任务清空了,将工作者队列的线程停止,
     * 打破循环,清空工作者队列.
     */
    @Override
    public void shutDown() {
        while (true) {
            if (jobs.size() == 0) {
                for (Worker worker : workers) {
                    worker.stopRunning();
                }
                break;
            }
        }
        workers.clear();
    }

    /**
     * Add a new worker to the end of the worker queue.
     *
     * @param num num
     */
    @Override
    public void addWorker(int num) {
        for (int i = 0; i < num; i++) {
            Worker worker = new Worker();
            workers.offer(worker);
            worker.start();
        }
    }

    /**
     * Remove worker blocking thread at queue head.
     * 移除工作者阻塞队列头部的线程.
     *
     * @param num num
     */
    @Override
    public void removeWorker(int num) {
        for (int i = 0; i < num; i++) {
            try {
                workers.take().stopRunning();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int poolSize() {
        return workers.size();
    }

    /**
     * Clears the task queue and then calls the way to stop the thread pool.
     * 清空任务队列,然后调用停止线程池的方法.
     */
    @Override
    public void shutDownNow() {
        jobs.clear();
        shutDown();
    }


    private class Worker extends Thread {

        //通过 volatile修饰的变量,保证变量的可见性,从而能让线程马上得知状态
        private volatile boolean isRunning = true;


        @Override
        public void run() {
            //通过自旋不停的从任务队列中获取任务
            while (isRunning) {
                Runnable runnable = null;

                try {
                    //从任务队列中获取任务
                    runnable = jobs.take();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (runnable != null) {
                    System.out.println("当前线程:" + getName() + " 当前runnable----:" + runnable);
                    runnable.run();
                }
                // 睡眠 100毫秒,验证 shutdown 是否是在任务执行完毕后才会关闭线程池
                try {
                    log.info("休眠2000");
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(getName() + "销毁...");
        }

        public void stopRunning() {
            this.isRunning = false;
        }
    }
}
