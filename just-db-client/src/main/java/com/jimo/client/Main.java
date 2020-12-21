package com.jimo.client;

import com.jd.urbancomputing.just.dbdriver.client.JustClient;
import com.jd.urbancomputing.just.dbdriver.exception.ExecuteFailException;
import com.jd.urbancomputing.just.dbdriver.result.ResultSet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws IOException, ExecuteFailException {
        if (args.length < 6) {
            throw new IllegalArgumentException("java -jar app.jar pk sk url sqlPath db columnCount [pageSize]");
        }
        final JustClient client = new JustClient(args[0], args[1], args[2]);
        client.useDb(args[4]);
        client.setPageSize(args.length > 6 ? Integer.parseInt(args[6]) : 1000);
        final ResultSet rs = client.executeQuery(readSql(args[3]));
        int columns = Integer.parseInt(args[5]);
        while (rs.next()) {
            for (int i = 0; i < columns; i++) {
                System.out.print(rs.getString(i + 1) + "    ");
            }
            System.out.println();
        }
    }

    private static String readSql(String path) throws IOException {
        return String.join(" ", Files.readAllLines(Paths.get(path)));
    }
}
