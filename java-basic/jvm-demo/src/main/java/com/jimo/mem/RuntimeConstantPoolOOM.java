package com.jimo.mem;

/**
 * @author jimo
 * @date 2019/8/6 下午10:12
 */
public class RuntimeConstantPoolOOM {
    public static void main(String[] args) {
        String s = "计算机寂寞";
        String s1 = new StringBuilder("计算机").append("寂寞").toString();
        System.out.println(s1.intern() == s1);

        String s2 = new StringBuilder("ja").append("va").toString();
        System.out.println(s2.intern() == s2);
    }
}
