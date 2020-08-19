package com.mrx.www.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * try-with-resources.
 *
 * @author Mei Ruoxiao
 * @since 2020/05/29
 */
public class TestThrow implements AutoCloseable {
    @Override
    public void close() throws Exception {
        System.out.println("Close custom resources");
        throw new Exception("close");
    }

    public void readLine() throws Exception {
        throw new Exception("readLine");
    }

    public static void main(String[] args) throws Exception {
        //try catch方式，readLine()方法的异常信息会被close()方法的堆栈信息限制了，
        //这会让人误以为要调查的目标是close()方法而不是readLine()方法，尽管它也是应该怀疑的对象。
        TestThrow testThrow = new TestThrow();
        try {
            testThrow.readLine();
        } finally {
            testThrow.close();
        }

        // try-with-resources 方式，所有的异常信息不会被吃掉
        // 只要 需要释放的资源（比如 BufferedReader）实现了 AutoCloseable 接口。
        // 编译后，在 try 中调用了 close()，且在catch中多了一个addSuppressed()方法，var5.addSuppressed(var4)。
        // 它有什么用处呢？当一个异常被抛出的时候，可能有其他异常因为该异常而被抑制住，从而无法正常抛出。
        // 这时可以通过 addSuppressed() 方法把这些被抑制的方法记录下来，被抑制的异常会出现在抛出的异常的堆栈信息中。
        // 也可以通过 getSuppressed() 方法来获取这些异常。这样做的好处是不会丢失任何异常，方便我们开发人员进行调试。
//        try (TestThrow testThrow = new TestThrow()) {
//            testThrow.readLine();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public static void close(Closeable... closeableList) {
        for (Closeable closeable : closeableList) {
            try {
                if (closeable != null) {
                    closeable.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
