package com.mrx.www.common.syncasync;

/**
 * 同步和异步关注的是消息通信机制 (synchronous communication/ asynchronous communication)。
 * 同步，就是调用某个方法时，要等待这个调用返回结果才能继续往后执行.
 * 异步，和同步相反,不会立即得到结果，在调用发出后,可继续执行后续操作.
 *
 * @author Mei Ruoxiao
 * @since 2020/06/03
 */
public class SyncAsync {
    /**
     * 同步调用方式,按上下文顺序执行.
     */
//    public static void eat() {
//        System.out.println("去吃火锅");
//        try {
//            //等待上锅底和上菜的时间
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("吃完了");
//    }

    /**
     * 异步调用方式,耗时的操作,可以放在另外一个线程之中,提高程序性能,
     * 不一定是按顺序执行的.
     */
    public static void eat() {
        System.out.println("去吃火锅");
        new Thread(() -> {
            try {
                //等待上锅底和上菜的时间
                Thread.sleep(2000);
                System.out.println("吃完了");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void shopping() {
        System.out.println("去逛街了");
    }


    public static void main(String[] args) {
        //这个例子不是很好
        eat();
        shopping();
    }
}
