package com.mrx.www.common.threadpool;

/**
 * thread pool.
 * Implement a simple thread pool manually.
 *
 * @author Mei Ruoxiao
 * @since 2020/06/03
 */
public interface Pool {
    /**
     * add task
     *
     * @param runnable runnable
     */
    void execute(Runnable runnable);

    /**
     * Stop after thread pool finishes executing tasks
     */
    void shutDown();

    /**
     * add worker
     *
     * @param num num
     */
    void addWorker(int num);

    /**
     * remove Worker
     *
     * @param num num
     */
    void removeWorker(int num);

    /**
     * thread pool size
     *
     * @return
     */
    int poolSize();

    /**
     * Stop thread pool,stop whether there is a task or not.
     */
    void shutDownNow();

}
