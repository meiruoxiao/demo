package com.mrx.www.common.thread;

/**
 * MyRunnable.
 *
 * @author Mei Ruoxiao
 * @since 2020/06/03
 */
public class MyRunnable implements Runnable {
    private int ticket = 5;

//    @Override
//    public void run() {
//        for (int i = 0; i < 10; i++) {
//            //判断是否还有剩余票
//            if (ticket > 0) {
//                System.out.println(Thread.currentThread().getName() + "卖票,ticket = " + ticket--);
//            }
//        }
//    }


//    @Override
//    public void run() {
//        for (int i = 0; i < 10; i++) {
//            //同步代码块,一个线程操作的时候,另外一个不允许操作,不然可能会出现两个线程都在操作同一张票
//            //因为加上了线程锁，很容易出现线程阻塞，死锁等问题。
//            synchronized (this) {
//                //判断是否还有剩余票
//                if (ticket > 0) {
//                    try {
//                        //休息1000毫秒,如果不休眠,一个线程就全跑完了,这个数据小
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    System.out.println(Thread.currentThread().getName() + "卖票,ticket = " + ticket--);
//                }
//            }
//
//        }
//    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            sale();
        }
    }

    /**
     * 同步方法,因为加上了线程锁，很容易出现线程阻塞，死锁等问题。
     */
    public synchronized void sale() {
        if (this.ticket > 0) {
            try {
                //休息1000毫秒
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()
                    + "卖票： " + this.ticket--);
        }
    }


    public static void main(String[] args) {
        MyRunnable runnable = new MyRunnable();
        //MyRunnable runnable1 = new MyRunnable();
        //这是两个线程,分别处理两个任务,资源不是共享的
//        Thread thread = new Thread(runnable);
//        Thread thread1 = new Thread(runnable1);
//        thread.start();
//        thread1.start();
        //这是两个线程,共同处理一个任务,资源共享的
        Thread thread = new Thread(runnable, "售票口1");
        Thread thread1 = new Thread(runnable, "售票口2");
        thread.start();
        thread1.start();

        //Runnable可以理解为就是一个任务

    }
}
