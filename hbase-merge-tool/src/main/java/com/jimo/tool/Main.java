package com.jimo.tool;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HRegionInfo;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            throw new IllegalArgumentException("java -jar hbase-merge-tool.jar zk.quorum tablePattern [isRegex=false]");
        }
        String zk = args[0];
        String tablePattern = args[1];
        boolean regex = args.length > 2 && Boolean.parseBoolean(args[2]);

        final Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", zk);
        final Connection connection = ConnectionFactory.createConnection(conf);
        final HBaseAdmin admin = (HBaseAdmin) connection.getAdmin();

        final TableName[] tableNames;

        if (regex) {
            tableNames = admin.listTableNames(tablePattern);
        } else {
            tableNames = new TableName[]{TableName.valueOf(tablePattern)};
        }

        for (TableName tableName : tableNames) {
            mergeRegion(tableName, admin);
        }

        admin.close();
    }

    private static void mergeRegion(TableName tableName, HBaseAdmin admin) throws IOException {
        print("开始合并表【{}】的region", tableName.getNameAsString());

        final List<HRegionInfo> regions = admin.getTableRegions(tableName);

        print("总共有{}个region", regions.size());

        if (regions.size() < 2) {
            admin.close();
            print("由于region数{}太少，并没有合并", regions.size());
            return;
        }
        regions.sort((o1, o2) -> Bytes.compareTo(o1.getStartKey(), o2.getStartKey()));

        for (int i = 0; i < regions.size(); i += 2) {
            HRegionInfo r1 = regions.get(i);
            if (i + 1 >= regions.size()) {
                break;
            }
            final HRegionInfo r2 = regions.get(i + 1);
            print("进度：{}%，开始合并第{}、{}个region：{} 和 {}", (i + 1) * 100.0 / regions.size(), i, i + 1, r1, r2);
            admin.mergeRegions(r1.getEncodedNameAsBytes(), r2.getEncodedNameAsBytes(), false);
        }
    }

//    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void print(String s, Object... args) {
        logger.info(s, args);
//        for (Object arg : args) {
//            s = s.replaceFirst("\\{}", String.valueOf(arg));
//        }
//        System.out.println(LocalDateTime.now().format(formatter) + ": " + s);
    }
}
