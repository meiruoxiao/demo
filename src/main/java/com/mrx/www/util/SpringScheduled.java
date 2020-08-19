package com.mrx.www.util;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * Scheduled tasks.
 *
 * @author Mei Ruoxiao
 * @since 2020/06/03
 */
@Component
public class SpringScheduled {

    /**
     * way 1
     */
    @Scheduled(initialDelay = 2000, fixedDelay = 4000)
    public void doSomething1() {
        System.out.println("直接运行项目TestApplication,Spring自带的Scheduled执行了=======================");
    }

    /**
     * 注解方式 异步定时任务
     *
     * @throws InterruptedException
     */
    @Async
    @Scheduled(initialDelay = 2000, fixedDelay = 3000)
    public void something() throws InterruptedException {
        System.out.println("直接运行项目TestApplication,Spring自带的Scheduled执行了=======================");
        System.out.println("第一个定时任务开始 : " + LocalDateTime.now().toLocalTime() + "\r\n线程 : " + Thread.currentThread().getName());
        Thread.sleep(1000);
    }

    /**
     * 注解方式 异步定时任务
     */
    @Async
    @Scheduled(initialDelay = 1000, fixedDelay = 5000)
    public void something2() {
        System.out.println("直接运行项目TestApplication,Spring自带的Scheduled执行了***********************");
        System.out.println("第二个定时任务开始 : " + LocalDateTime.now().toLocalTime() + "\r\n线程 : " + Thread.currentThread().getName());
    }

    /**
     * way 2
     * Timer只创建了一个线程。当你的任务执行的时间超过设置的延时时间将会产生一些问题.
     * Timer创建的线程没有处理异常，因此一旦抛出非受检异常，该线程会立即终止.
     * 间隔时间不是特别稳定
     */
    public static void doSomething2() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Time's up!");

            }
        }, 2000, 100);
        //2000表示第一次执行任务延迟时间，100表示以后每隔多长时间执行一次run里面的任务
    }

    /**
     * way 3
     * 间隔时间比较稳定
     */
    public static void doSomething3() {
        //ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(3, 7, 100, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10));
        ScheduledExecutorService scheduledThreadPool = new ScheduledThreadPoolExecutor(3);
        scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("=========================");
            }
        }, 1000, 2000, TimeUnit.MILLISECONDS);

        scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println(System.currentTimeMillis() + "<><>" + System.nanoTime());
            }
        }, 1000, 2000, TimeUnit.MILLISECONDS);
    }

//    public static void main(String[] args) {
//        doSomething3();
//    }
}
