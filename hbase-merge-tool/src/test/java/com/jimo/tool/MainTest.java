package com.jimo.tool;

import org.apache.hadoop.hbase.HRegionInfo;
import org.junit.jupiter.api.Test;

public class MainTest {

    @Test
    public void print() {
        int i = 1;
        final HRegionInfo r1 = new HRegionInfo();
        final HRegionInfo r2 = new HRegionInfo();
        Main.print("进度：{}%，开始合并第{}、{}个region：{} 和 {}", i * 200.0 / 1000, i, i + 1, r1, r2);
    }


}
