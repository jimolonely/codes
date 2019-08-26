package com.jimo.gc;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.ArrayList;
import java.util.List;

/**
 * 写一段程序，让其运行时的表现为触发5次ygc，然后3次fgc，
 * 然后3次ygc，然后1次fgc，请给出代码以及启动参数
 *
 * @author jimo
 * @date 19-8-26 上午9:37
 */
public class GcTest {

    private static final int _1MB = 1024 * 1024;

    /**
     * <p>
     * -Xms41M -Xmx41M -Xmn10M -XX:+PrintGCDetails
     * </p >
     *
     * @author jimo
     * @date 19-8-26 上午9:41
     */
    public static void main(String[] args) {

        MemoryMXBean mxBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = mxBean.getHeapMemoryUsage();
        System.out.println("初始化堆：" + (heapMemoryUsage.getInit() >> 10) + "K");
        System.out.println("已用堆：" + (heapMemoryUsage.getUsed() >> 10) + "K");
        System.out.println("最大堆：" + (heapMemoryUsage.getMax() >> 10) + "K");

        List<byte[]> bytes = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            byte[] a = new byte[3 * _1MB];
            bytes.add(a);
///            System.out.println("剩余：" + (Runtime.getRuntime().freeMemory() >> 10) + "K");
        }

        bytes.remove(0);
        bytes.add(new byte[3 * _1MB]);

        bytes.subList(0, 8).clear();

        bytes.add(new byte[3 * _1MB]);
        for (int i = 0; i < 6; i++) {
            bytes.add(new byte[3 * _1MB]);
        }
    }
}
