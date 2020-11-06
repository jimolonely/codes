package com.jimo.tool.del;

import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * your comment
 *
 * @author jimo
 * @date 2020/11/6 15:36
 * @since 1.0.0
 */
public class DeleteTable {
    private static Logger logger = LoggerFactory.getLogger(DeleteTable.class);

    public void delete(List<String> tableNames, HBaseAdmin admin) {
        for (String tableName : tableNames) {
            try {
                admin.disableTable(tableName);
                admin.deleteTable(tableName);
                logger.info("删除表{}成功", tableName);
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("删除表{}失败", tableName);
            }
        }
    }

    public void delete(String path, HBaseAdmin admin) {
        try {
            final List<String> tableNames = Files.readAllLines(Paths.get(path));
            delete(tableNames, admin);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
