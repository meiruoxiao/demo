package com.mrx.www.util;

import java.io.*;

/**
 * IO流-read()和write().
 *
 * @author Mei Ruoxiao
 * @since 2020/04/16
 */
public class FileDemo {
    public static void main(String[] args) throws IOException {
        //    readDemo();
        bufferedWriter();
        //    readTest();
        //    writeDemo();
        //    writeTest();
        //    copy("E:\\image\\wo.txt", "E:\\image\\wo1.txt");
    }

    /**
     * InputStream：字节输入流 -从文件中读，read()的问题：读取乱码，因为一个汉子占两个字节，不能拿一个字节来读。
     *
     * @throws IOException
     */
    private static void readDemo() throws IOException {
        // 创建一个文件对象，这个目录下没有文件的话，会报错，跟输入流不一样
        File file = new File("E:\\image\\wo.txt");
        // 创建一个文件输入流对象
        FileInputStream inputStream = new FileInputStream(file);
        int b;
        while ((b = inputStream.read()) != -1) {
            // 读取返回的是int类型
            System.out.println(b);
            // 转换成字符
            System.out.println((char) b);

            // 把读取的字节存放到bytes中（输出流去写入，写到文件中）
        }
        // 关闭流
        inputStream.close();
    }

    /**
     * InputStream：字节输入流 -从文件中读，不乱码。
     *
     * @throws IOException
     */
    private static void readTest() throws IOException {
        // 创建一个文件对象，这个目录下没有文件的话，会报错，跟输入流不一样
        File file = new File("E:\\image\\wo.txt");
        // 创建一个文件输入流对象
        FileInputStream inputStream = new FileInputStream(file);
        // 创建一个字节数组，存储文件数据
        byte[] bytes = new byte[(int) file.length()];
        // 每次从inputStream读取一定数量的字节，并将其存储在缓冲区数组bytes中，以整数形式返回实际读取的字节数，现在存放到bytes中了
        inputStream.read(bytes);
        // String对中文会识别，对中文编码进行转换
        // 电脑上的文档默认的GBK格式，而.java文件默认utf-8文件
        // 通过使用指定的 charset，解码指定的 byte数组，构造一个新的 String
        String string = new String(bytes, "GBK");
        System.out.println(string);

        // 关闭流
        inputStream.close();
    }

    /**
     * OutputStream：字节输出流 -写入文件中，一次写一个字节，效率低
     *
     * @throws IOException
     */
    private static void writeDemo() throws IOException {
        // 创建一个文件对象，现在就相当于有一个文件了，实际上文件还没有内容，也没有存到硬盘中
        File file = new File("E:\\image\\wo.txt");
        // 获取文件名，针对文件对象操作，而不是文件里的内容
        System.out.println(file.getName());

        // 如果E盘下没有wo.txt文件，则会创建一个，但是创建不了多级目录
        String path = "E:\\image\\wo.txt";

        // 创建一个文件输出流对象，从输出流写入数据（从内存中存储到硬盘上的文件里或显示到屏幕上）
        // 若文件不存在会自动创建
        FileOutputStream outputStream = new FileOutputStream(file);
        //    FileOutputStream outputStream = new FileOutputStream(path);//用path也可以
        // 通过输出流写入数据,OutputStream 的write(int b)方法，一次只能写一个字节,效率低
        // 注意：使用write(int b)方法，虽然接收的是int类型参数，但是write的常规协定是：向输出流写入一个字节。要写入的字节是参数b的八个低位。b的24个高位将被忽略。
        // UTF-8编码中，一个英文字符等于一个字节，8位，所以这里不会遗漏。
        outputStream.write('h');
        outputStream.write('e');
        // 关闭流
        outputStream.close();
    }

    /**
     * OutputStream：字节输出流 -写入文件中，使用缓冲，提高写入数据效率
     *
     * @throws IOException
     */
    private static void writeTest() throws IOException {

        String string = "自己按门铃自己听，自己的眼睛，自己的病，自己茂盛，自己凋零";
        byte[] bytes = string.getBytes();
        File file = new File("E:\\image\\wo11.txt");
        // 1.创建文件输出流，指向指定的文件
        FileOutputStream outputStream = new FileOutputStream(file);
        // 2.通过流文件写数据
        outputStream.write(bytes);
        // 3.关闭流
        outputStream.close();
    }

    /**
     * 字节流文件拷贝 （1）从源文件读取，放入到读取流中，从读取流中读取到数组中。 （2）创建输出流到目的文件，将数组中的字节数据写入到文件中。
     *
     * @param path1 源文件路径，硬盘中要有文件
     * @param path2 目的文件路径，硬盘中可无，会自动创建
     * @throws IOException
     */
    private static void copy(String path1, String path2) throws IOException {
        File file = new File(path1);
        InputStream inputStream = new FileInputStream(file);
        byte[] bytes = new byte[(int) file.length()];
        inputStream.read(bytes);
        inputStream.close();

        File file1 = new File(path2);
        OutputStream outputStream = new FileOutputStream(file1);
        outputStream.write(bytes);
        outputStream.close();
    }

    /**
     * Reader：字符输入流 -从文件中读
     * 如果文件不是utf-8编码，中文会乱码，要么把文件的编码方式改成utf-8,要么在代码里面转换
     *
     * @throws IOException
     */
    private static void read() throws IOException {
        File file = new File("E:\\重要.txt");

        // 创建文件字符输入流
        Reader reader = new FileReader(file);
        System.out.println(file.length());
        char[] chars = new char[200];
        // 读取数据
        reader.read(chars);
        for (char c : chars) {
            System.out.print(c);
        }
        String str = new String(chars);
        System.out.println(str);
        reader.close();
    }

    /**
     * BufferedReader：字符c缓冲输入流 -从文件中读
     * 如果文件不是utf-8编码，中文会乱码，要么把文件的编码方式改成utf-8，要么在代码里面转换，
     * 使用try-catch-finally时，多个io流需要关闭而重复嵌套try-catch-finally的问题，
     * 可以考虑try-with-resource（编译后会调用close()方法），但是不建议使用。
     *
     * @throws IOException
     */
    private static void bufferedReader() {
        File file = new File("E:\\重要.txt");
        // 创建文件字符输入流
        Reader reader = null;
        BufferedReader br = null;
        String str;
        try {
            reader = new FileReader(file);
            br = new BufferedReader(reader);
            while ((str = br.readLine()) != null) {
                System.out.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        // try-with-resource方式，但是不建议使用
//    String str;
//    try( BufferedReader br = new BufferedReader(new FileReader(file))) {
//      while ((str = br.readLine()) !=null){
//        System.out.println(str);
//      }
//    } catch (IOException e) {
//      e.printStackTrace();
//    }

    }

    /**
     * Writer：字符输出流 -写入到文件中
     *
     * @throws IOException
     */
    private static void write() throws IOException {
        String str = "大鱼的翅膀，已经太辽阔";
        String path = "E:\\image\\wo.txt";
        // 创建文件字符输出流，指向目的文件
        Writer writer = new FileWriter(path);
        // 写入数据
        writer.write(str);
        writer.close();
    }

    /**
     * BufferedWriter：字符缓冲输出流 -写入到文件中
     *
     * @throws IOException
     */
    private static void bufferedWriter() throws IOException {
        String str = "大鱼的翅膀，已经太辽阔";
        String path = "E:\\wo.txt";
        // 创建文件字符输出流，指向目的文件
        Writer writer = new FileWriter(path);
        BufferedWriter bw = new BufferedWriter(writer);
        // 写入数据
        bw.write(str);
        bw.close();
        writer.close();
    }

    /**
     * 字符流拷贝文件
     *
     * @param path1
     * @param path2
     * @throws IOException
     */
    private static void copyFile(String path1, String path2) throws IOException {
        char[] chars = new char[1024];
        int ch = -1;
        // 创建文件字符输入流对象
        Reader reader = new FileReader(path1);
        // 创建文件字符输出流对象
        Writer writer = new FileWriter(path2);
        // 读取path1的文件内容到chars
        while ((ch = reader.read(chars)) != -1) {
            // 将chars的内容写入到path2的文件中
            writer.write(chars, 0, ch);
        }
        // 关闭流
        reader.close();
        writer.close();
    }


    /**
     * 字节缓冲流实现复制，上述程序中我们为了提高流的使用效率，自定义了字节数组，作为缓冲区Java其实提供了专门的字节流缓冲来提高效率。
     * BufferedOutputStream和BufferedOutputStream类可以通过减少读写次数来提高输入和输出的速度。它们内部有一个缓冲区，用来提高处理效率。
     *
     * @param src  源文件路径，硬盘中要有文件
     * @param desc 目的文件路径，硬盘中可无，会自动创建
     * @throws IOException
     */
    private static void copyDemo(String src, String desc) throws IOException {
        File file = new File(src);
        File descFile = new File(desc);
        if (!descFile.exists()) {
            descFile.createNewFile();
        }
        // 字节写出流
        InputStream in = new FileInputStream(file);
        // 字节写入流
        OutputStream out = new FileOutputStream(desc);
        // 写入缓冲流
        BufferedOutputStream bufferOut = new BufferedOutputStream(out);
        // 写出缓冲流
        BufferedInputStream bufferIn = new BufferedInputStream(in);
        int b;
        while ((b = bufferIn.read()) != -1) {
            bufferOut.write(b);
        }
        bufferIn.close();
        in.close();
        bufferOut.close();
        out.close();
    }

    /**
     * 字符缓冲流实现复制
     *
     * @param src  源文件路径，硬盘中要有文件
     * @param desc 目的文件路径，硬盘中可无，会自动创建
     * @throws IOException
     */
    public static void copyTest(String src, String desc) throws IOException {
        File file = new File(src);
        File descFile = new File(desc);

        // 字符流
        Reader in = new FileReader(file);
        Writer out = new FileWriter(descFile);

        // 字符缓冲流
        BufferedReader bufferRead = new BufferedReader(in);
        BufferedWriter bufferWrite = new BufferedWriter(out);
        String line;
        // readLine方法用于读取一行字符串(以\r或\n分隔)
        while ((line = bufferRead.readLine()) != null) {
            bufferWrite.write(line);
            // 换行，newLine方法用于写入一个行分隔符
            bufferWrite.newLine();
        }
        bufferRead.close();
        in.close();
        bufferWrite.close();
        out.close();
    }

    /**
     * 字节字符转换流
     *
     * @throws IOException
     */
    public static void test() throws IOException {
        String name = "E:\\image\\深深.txt";
        File file = new File(name);
        FileInputStream in = new FileInputStream(file);
        // 字节流转化为字符流
        InputStreamReader reader = new InputStreamReader(in);
        // BufferedReader 对象来缓冲字符流
        BufferedReader buffer = new BufferedReader(reader);
        String line;
        while ((line = buffer.readLine()) != null) {
            System.out.println(line);
        }
        buffer.close();
        in.close();
        reader.close();
    }

}
