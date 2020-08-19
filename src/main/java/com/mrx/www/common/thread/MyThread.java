package com.mrx.www.common.thread;

/**
 * MyThread.
 *
 * @author Mei Ruoxiao
 * @since 2020/06/03
 */
public class MyThread extends Thread {
    public MyThread(String name) {
        super(name);
    }

    private int ticket = 5;

    @Override
    public void run() {

        for (int i = 0; i < 10; i++) {
            //判断是否还有剩余票
            if (ticket > 0) {
                System.out.println(Thread.currentThread().getName() + "卖票,ticket = " + ticket--);
            }
        }
    }

    public static void main(String[] args) {
        //局限性:不能资共享,每个地方都买了5张票
        MyThread myThread = new MyThread("售票口1");
        MyThread myThread1 = new MyThread("售票口2");
        //两个地方同时买票
        myThread.start();
        myThread1.start();
    }
}
