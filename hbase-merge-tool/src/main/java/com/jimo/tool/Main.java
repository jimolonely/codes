package com.jimo.tool;

import com.jimo.tool.del.DeleteTable;
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
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        if (args.length < 3) {
            throw new IllegalArgumentException("java -jar hbase-merge-tool.jar zk.quorum tablePattern command" +
                    "(merge|delete) [isRegex=false] [namespace=default]");
        }
        String zk = args[0];
        String tablePattern = args[1];
        String command = args[2];
        boolean regex = args.length > 3 && Boolean.parseBoolean(args[3]);
        String namespace = args.length > 4 ? args[4] : "default";

        final Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", zk);
        final Connection connection = ConnectionFactory.createConnection(conf);
        final HBaseAdmin admin = (HBaseAdmin) connection.getAdmin();

        if (command.equals("delete")) {
            doDelete(admin, tablePattern);
        } else if (command.equals("merge")) {
            doMerge(tablePattern, regex, admin, namespace);
        } else {
            throw new IllegalArgumentException("不合法的command");
        }

        admin.close();
    }

    private static void doDelete(HBaseAdmin admin, String path) {
        final DeleteTable deleteTable = new DeleteTable();
        deleteTable.delete(path, admin);
    }

    private static void doMerge(String tablePattern, boolean regex, HBaseAdmin admin, String namespace) throws IOException {
        final List<TableName> tableNames = new ArrayList<>();

        final TableName[] allTable = admin.listTableNamesByNamespace(namespace);
        if (regex) {
//            tableNames = admin.listTableNames(tablePattern);
            Pattern pattern = Pattern.compile(tablePattern);
            for (TableName t : allTable) {
                if (pattern.matcher(t.getNameAsString()).matches()) {
                    tableNames.add(t);
                }
            }
        } else {
            tableNames.add(TableName.valueOf(tablePattern));
        }

        for (TableName tableName : tableNames) {
            mergeRegion(tableName, admin);
        }
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
