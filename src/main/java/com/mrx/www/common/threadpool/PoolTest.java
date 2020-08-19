package com.mrx.www.common.threadpool;

/**
 * .
 *
 * @author Mei Ruoxiao
 * @since 2020/06/03
 */
public class PoolTest {
    public static void main(String[] args) {
        //构建一个只有10个线程的线程池
        Pool pool = new DefaultThreadPool(10);

        try {
            System.out.println("休眠1000");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //放15个任务进去,让线程池进行消费
        for (int i = 0; i < 15; i++) {
            int finalI = i;
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("当前任务执行的线程" + Thread.currentThread().getName() + " 打印数字:" + finalI);
                }
            });
        }

        /**
         * 验证线程池的消费完任务停止以及不等任务队列清空就停止任务
         */
        System.out.println("停止线程池");
        //会让15个任务执行完才停止线程池,销毁
        //pool.shutDown();
        //一个线程执行一个任务后,不等任务队列被清空就停止了
        //pool.shutDownNow();

        /**
         * 移除2个工作者
         */
        //pool.removeWorker(2);
        //System.out.println("线程池大小:"+pool.poolSize());

        /**
         * 添加5个工作者
         */
        //pool.addWorker(5);
        //System.out.println("线程池大小:"+pool.poolSize());

    }
}
