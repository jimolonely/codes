package com.jimo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {

    public static void main(String[] args) {
        String json = "{\n" +
                "  \"key\": [\n" +
                "    {\n" +
                "      \"id\": 1,\n" +
                "      \"name\": \"jimo\",\n" +
                "      \"value\": 1.234567890\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        JSONObject obj = JSON.parseObject(json);
        for (Map.Entry<String, Object> e : obj.entrySet()) {
            List list = (List) e.getValue();
            Map<String, Object> map = new HashMap<String, Object>();
            for (Object o : list) {
                Map m = (Map) o;
                map.put(m.get("name").toString(), m.get("value"));
                System.out.println(m.get("value"));
            }
            System.out.println(map);
        }
    }
}
