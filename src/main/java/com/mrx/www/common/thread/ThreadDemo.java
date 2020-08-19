package com.mrx.www.common.thread;

/**
 * .
 *
 * @author Mei Ruoxiao
 * @since 2020/06/03
 */
public class ThreadDemo {
    public static ThreadDemo threadDemo3 = new ThreadDemo();//静态对象

    public static void main(String[] args) {
//        Thread t = new Thread() {
//            @Override
//            public void run() {
//                pang();
//            }
//        };
//        t.start();//该行代码相当于是启动线程
//        // start和run的区别,
//        //t.run();//该行代码相当于是使用t这个类中的run方法而已
//        System.out.print("ping");

        //********关于类对象锁和某个对象的锁*******//

        ThreadDemo threadDemo = new ThreadDemo();
        ThreadDemo threadDemo2 = new ThreadDemo();

        //====Synchronized修饰非静态方法，实际上是对调用该方法的对象加锁，俗称“对象锁”====//
        //在两个线程中分别访问同一个对象的同一个或不同同步方法，会产生互斥，不会并发执行
        //相当于四合院的房间A和B都是同一把锁
        //在两个线程中调用不同对象的同一个或不同同步方法，不会互斥，会并发执行
        //相当于四合院的房间A和B不是同一把锁

        //线程1调用对象threadDemo的方法A，那么别的线程就不能调用对象threadDemo的方法A和B，
        //要等线程１调用对象threadDemo的方法A完成之后，另外的线程调用对象threadDemo的A和B方法才会执行（但是别的线程可以调用对象threadDemo2的方法A和B）。
        //如果线程１调用的是对象threadDemo的方法C，那么别的线程还是可以调用对象threadDemo的方法C和A的，因为C是字符串锁，不是对象锁，而A是对象锁。
        //多个线程可以同时访问不同对象的同步方法。

        //线程1，匿名对象只能使用一次，想要再次使用start()方法就要再创建一个对象
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                threadDemo.methodA();
//            }
//        }).start();
//        //线程2
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
////                threadDemo.methodA();//同一个对象的同一个方法
////                threadDemo.methodB();//同一个对象的不同方法
//
//                  threadDemo2.methodA();//不同对象的的同一个方法
////                threadDemo2.methodB();//不同对象的不同方法
//            }
//        }).start();
//        //线程3
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                threadDemo.methodC();
//            }
//        }).start();


        //====Synchronized修饰静态方法，实际上是对该类加锁，俗称“类锁”====//
        //用类直接在两个线程中调用两个不同的同步静态方法
        //会产生互斥，不会并发执行，相当于只有四合院大门的钥匙


        new Thread(new Runnable() {
            @Override
            public void run() {
                methodD();
            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                methodE();
            }
        }).start();

    }

//    static void pang() {
//        System.out.print("pang");
//    }


    //一个类里面有两个非静态同步方法，不同线程调同一个对象的这两个方法会影响，会等一个方法执行完才会执行另外一个//

    public synchronized void methodA() {
        System.out.println("这是方法A");
        try {
            Thread.sleep(4000);
            System.out.println("睡眠4秒,方法A执行完毕");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void methodB() {
        synchronized (this) {
            System.out.println("这是方法B");
        }

    }

    public void methodC() {
        String string = "哈哈";
        synchronized (string) {
            System.out.println("这是方法C");
        }
    }


    public static synchronized void methodD() {
        System.out.println("这是方法D");
        try {
            Thread.sleep(4000);
            System.out.println("休眠4秒，静态方法D执行完毕");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void methodE() {
        System.out.println("这是方法E");
    }
}
