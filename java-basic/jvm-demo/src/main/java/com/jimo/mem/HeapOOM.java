package com.jimo.mem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jimo
 * @date 2019/8/6 下午9:47
 */
public class HeapOOM {

    static class OOMObject {
    }

    /**
     * <p>
     * -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
     * </p >
     * https://juejin.im/entry/59ddcff86fb9a0452340da0e
     * @author jimo
     * @date 2019/8/6 下午9:53
     */
    public static void main(String[] args) {
        List<OOMObject> objs = new ArrayList<>();
        while (true) {
            objs.add(new OOMObject());
        }
    }
}
