package com.jimo.mem;

/**
 * Java虚拟机栈 栈溢出
 *
 * @author jimo
 * @date 2019/8/6 下午10:05
 */
public class JvmStackSOF {
    private int depth = 0;

    public void stackLeak() {
        depth++;
        stackLeak();
    }

    /**
     * <p>
     * -Xss228k, 至少228k才能创建虚拟机
     * </p >
     *
     * @author jimo
     * @date 2019/8/6 下午10:10
     */
    public static void main(String[] args) {
        JvmStackSOF sof = new JvmStackSOF();
        try {
            sof.stackLeak();
        } catch (Throwable t) {
            System.out.println("depth=" + sof.depth);
            throw t;
        }
    }
}
