package com.tianzhuan.net_handler_sample;

import org.junit.Test;

public class ThreadLocalTest {
    @Test
    public void test(){
        //创建一个本地的线程（主线程）
        final ThreadLocal<String> threadLocal=new ThreadLocal<String>(){

            //重写初始化方法，默认返回null,如何ThreadLocalMap拿不到值再调用初始化方法
            @Override
            protected String initialValue() {
                return "冯老师";
            }
        };
        //从threadlocalMap中取string值，key是主线程
        System.out.println("主线程threadLocal "+threadLocal.get());
        //----------------thread-0
        Thread thread1=new Thread(){
            @Override
            public void run() {
               //获取threadLocalmap中key为thread-0的value值。
                System.out.println("thread-0的threadLocal "+threadLocal.get());
                //存入threadLocalmap 可以为thread-0,value为熊老师
                threadLocal.set("熊老师");
                System.out.println("thread-0的threadLocal "+threadLocal.get());

                //使用完成建议remove(),避免大量无意义的内存占用

                threadLocal.remove();
            }
        };
        thread1.start();

        //----------------thread-2
        Thread thread2=new Thread(){
            @Override
            public void run() {
                //获取threadLocalmap中key为thread-0的value值。
                System.out.println("thread-2的threadLocal "+threadLocal.get());
                //存入threadLocalmap 可以为thread-0,value为熊老师
                threadLocal.set("王老师");
                System.out.println("thread-2的threadLocal "+threadLocal.get());

                //使用完成建议remove(),避免大量无意义的内存占用

                threadLocal.remove();
            }
        };
        thread2.start();
    }
}
