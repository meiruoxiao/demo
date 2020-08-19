package com.mrx.www.test;

import com.mrx.www.entity.PersonPoJo;
import com.mrx.www.util.MyFunction;

import java.util.*;
import java.util.function.BiFunction;

/**
 * test.
 *
 * @author Mei Ruoxiao
 * @since 2020/05/28
 */
public class Test {


    public static void main(String[] args) {
//        System.out.println(sum(1));
//        System.out.println(solve(1, "张三", 15));
//        System.out.println(myFunction("sdubedjn", s -> s.substring(0, 2)));
//        compare();
//        testMapGet();
        //key相等，value覆盖的情况
//        Map<Integer, String> map = new HashMap<>();
//        map.put(1, "张三");
//        String string = map.put(1, "李四");
//        System.out.println(string);
//        System.out.println(map.size());
//        System.out.println(map.get(1));

//        Scanner scanner = new Scanner(System.in);
//        System.out.println(scanner.nextInt(20));

        //数组变集合-asList()方法,是数组的方法,不是集合的方法,毕竟这是数组的事情,集合哪里知道谁要转成集合?
        //集合变数组-toArray()方法,是集合的方法,这是集合的事情。

        //将一个数组用asList()方法变成集合之后,这个集合是不可以使用集合的增删方法,因为数组的长度是固定的!
//        Integer[] ints = {1, 2, 3, 4, 5};
//        List<Integer> list = Arrays.asList(ints);
//        //会抛出UnsupportedOperationException异常
////        list.add(6);
//        Iterator<Integer> iterator = list.iterator();
//        while (iterator.hasNext()) {
//
//            System.out.println(iterator.next());
//        }

        //如果数组中的元素都是基本数据类型,则该数组变成集合时,会将该数组作为集合的一个元素放入集合,size为1。
        //如果数组中的元素都是引用类型,如Integer,String,那么数组变成集合后,数组中的元素就直接转成集合中的元素，size为实际元素大小。
        int[] ints = {1, 2, 3, 4, 5};
        System.out.println(ints);
        List list = Arrays.asList(ints);
        System.out.println(list.size());
        //元素是基本数据类型的数组，转成集合，直接输出的话，是输出的地址
        //元素是引用类型的数组，转成集合，直接输出的话，是显示的元素列表
        //因为println的底层是调用了对象的toString()方法的，而这个方法，String、Integer等是重写了的，所以输出的是元素，而不是地址
        System.out.println(list);


        //集合里面只能装引用类型，数组可以装基本数据类型和引用类型
        List list1 = new ArrayList();
        list1.add("a");
        list1.add("b");
        list1.add("c");
        list1.add(1);
        //点击println方法发现最终调用的是obj的toString()方法，现在传进去的是ArrayList集合，由于ArrayList没有重写toString()方法，往上查看
        //发现他的父类的父类AbstractCollection重写toString()方法，所以是按照这个来的。
        System.out.println(list1);
        Object[] strings = list1.toArray();
        System.out.println(strings);
    }


    public static int sum(int a) {
        if (a < 5) {
            //递归调用
            return sum(a + 1);
        }
        return a;
    }

    public static PersonPoJo solveIf(int a, String name, Integer age) {
        if (a == 1) {
            return PersonPoJo.of(null, age);
        } else if (a == 2) {
            return PersonPoJo.of(name, null);
        } else if (a == 3) {
            return PersonPoJo.of(name, age);
        }
        return PersonPoJo.of();
    }


    /**
     * 解决if...else过多的表驱动用法
     *
     * @param a    identify
     * @param name name
     * @param age  age
     * @return Object
     */
    public static PersonPoJo solve(int a, String name, Integer age) {
        Map<Integer, BiFunction<String, Integer, PersonPoJo>> map = new Hashtable<>();
        map.put(1, (string, num) -> PersonPoJo.of(null, age));
        map.put(2, (string, num) -> PersonPoJo.of(string, null));
        map.put(3, (string, num) -> PersonPoJo.of(string, age));

        return map.get(a).apply(name, age);

    }

    public static String myFunction(String str, MyFunction<String, String> fun) {
        //入参str不一定会作为apply的参数，有可能会通过它得到一个结果，再作为apply的参数，也就是说，str是我们要处理的对象，而apply是怎么去处理，然后返回结果
        return fun.apply(str);
    }

    //***懒汉单例模式,在第一次调用的时候实例化自己***//

//    private Test(){}
//    private static Test test;
//    public static Test getInstance(){
//        if (test == null){
//            test = new Test();
//        }
//        return test;
//    }


    //***饿汉单例模式 在类初始化时，已经自行实例化***//

    private Test() {
    }

    private static final Test TEST = new Test();

    public static Test getInstance() {
        return TEST;
    }

    //***关于HashMap和Hashtable的效率***//

    public static void mapTest() {
        long start = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        map.put("a", "张三");
        map.put("b", "李四");
        map.put("c", "王五");

        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println("我是键和值: " + entry);
        }

        for (String key : map.keySet()) {
            System.out.println("我是键: " + key);
        }

        for (String value : map.values()) {
            System.out.println("我是值: " + value);
        }
        long end = System.currentTimeMillis();
        System.out.println("花费时间: " + (end - start) + "ms");
    }

    //***测试Map集合取值的null***//

    public static void testMapGet() {
        //不被重写（原生）的hashCode值是根据内存地址换算出来的一个值。因为两个对象都是new出来的，内存地址是不一样的。
        //不被重写（原生）的equals方法是严格判断一个对象是否相等的方法（object1 == object2）。因为==的判断也是根据内存地址进行判断。

        Map<PersonPoJo, String> map = new HashMap<>(4);
        PersonPoJo poJo = new PersonPoJo("hello");
        map.put(poJo, "world");
        PersonPoJo poJo2 = new PersonPoJo("hello");
        System.out.println(poJo.hashCode());
        System.out.println(poJo2.hashCode());
        System.out.println(Objects.equals(poJo, poJo2));
        System.out.println(map.get(poJo2));

        //虽然没有在map中put第二个对象，但是如果重写了hashCode和equals，poJo2设置了相同的属性，所以他们的hashcode是相同。
        //poJo2的key和poJo是一样，那么通过key得到的hashcode也是一样的，实际上就是拿的map.get(poJo)。
        //map集合取的时候是根据hashcode去取的，所以，用pojo的hashcode是可以取到的，跟pojo2存没存进去没关系。
        //如果没有重写，poJo和poJo2的地址不一样，poJo2也没有放在map中，集合中没有该对象，肯定返回null。
        //如果重写了hashCode，hashCode相同，key不同，也是取不到，poJo2根本就没有存放到map中


        //在hashcode相同的情况下，put的时候，如果key相同（用equals判断，equals重写了相同，不重写，new出来的对象是不同的），value就会覆盖，如果key不同，就会以链表形式增加在后面。
        Map<PersonPoJo, String> map1 = new HashMap<>(4);
        PersonPoJo poJo3 = new PersonPoJo(10);
        PersonPoJo poJo4 = new PersonPoJo(10);
        map1.put(poJo3, "beautiful world");
        map1.put(poJo4, "ugly world");
        System.out.println(poJo3.hashCode());
        System.out.println(poJo4.hashCode());
        System.out.println(Objects.equals(poJo3, poJo4));
        System.out.println(map1.get(poJo3));
        System.out.println(map1.get(poJo4));

    }


    //***关于==和equals的方式作比较***//

    public static void compare() {
        PersonPoJo poJo = new PersonPoJo();
        poJo.setName("a");
        //赋值的话，poJo==poJo2就是true
        //PersonPoJo poJo2 = poJo;
        PersonPoJo poJo2 = new PersonPoJo();
        poJo2.setName("a");

        String str = "a";
        String str2 = "a";
        String string = new String("a").intern();
        String string2 = new String("a");
        //两个对象都是常量池的对象"a",不会重复创建,poJo.setName("a");中的"a"也是常量池的对象"a",都是同一个,只有new出来的才不是.所以相等.
        System.out.println(str == str2);
        System.out.println(Objects.equals(str, str2));
        //==比较的是内存地址,new String的方式,虽然是把常量池的值赋给堆中的对象,但是比较的是堆中对象的地址,所以不等
        System.out.println(string == string2);
        //一个在常量池中,一个在堆中,不相等
        System.out.println(str == string);
        //String重写了equals方法,比较的是内容,所以相等
        System.out.println(Objects.equals(str, string));
        System.out.println(Objects.equals(string2, string));

        System.out.println("======================");

        //poJo.getName()和poJo2.getName()得到的都是常量池中的"a"
        System.out.println(Objects.equals(poJo.getName(), poJo2.getName()));
        System.out.println(poJo.getName() == poJo2.getName());

        System.out.println(str == poJo.getName());
        System.out.println(Objects.equals(poJo.getName(), str));

        //PersonPoJo重写了hashCode方法,toString里面调用的是对象的hashCode方法，所以输出结果一样，,如果不重写,不会一样
        System.out.println(poJo.toString());
        System.out.println(poJo2.toString());

        //System.identityHashCode(Object)方法可以返回对象的内存地址,不管该对象的类是否重写了hashCode()方法.
        System.out.println(System.identityHashCode(poJo));
        System.out.println(System.identityHashCode(poJo2));

        //等同于poJo.toString()，返回的不一定时是真实内存地址
        System.out.println(poJo);
        System.out.println(poJo2);


        System.out.println(str.hashCode());
        System.out.println(poJo.hashCode());
        System.out.println(poJo2.hashCode());

        //两个对象的HashCode相同，并不一定表示两个对象就相同，即Objects.equals()不一定为true，只能够说明这两个对象在一个散列存储结构中。
        //因为PersonPoJo没有重写equals方法，那么就会调用Object默认的equals方法，比较的是地址，
        //也就说两个对象的HashCode相同，地址并不一定相同，对象就不相同
        //重写了equals方法之后，就是按重写的方式来的，Objects.equals()就是true了。
        System.out.println(poJo == poJo2);
        System.out.println(Objects.equals(poJo, poJo2));
        System.out.println(Objects.hash(poJo.hashCode()));

    }
}
