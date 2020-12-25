package com.jimo;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * your comment
 *
 * @author jimo
 * @date 2020/12/21 14:23
 * @since 1.0.0
 */
public class Main {

    /**
     * 集群：ip1:p1,ip2:p2
     */
    public static void main(String[] args) {
        if (args.length < 3) {
            throw new IllegalArgumentException("java -jar client.jar redisHostAnfPorts command(get/lpop) key " +
                    "[password]");
        }
        String password = args.length >= 4 ? args[3] : null;
        final String[] hosts = args[0].split(",");
        Set<HostAndPort> hostAndPorts = new HashSet<>(4);
        for (String host : hosts) {
            final String[] hp = host.split(":");
            hostAndPorts.add(new HostAndPort(hp[0], Integer.parseInt(hp[1])));
        }
        final JedisCluster jedisCluster = new JedisCluster(hostAndPorts, 10000, 60000, 3, password, getPoolConfig());

        final String command = args[1].trim();
        String key = args[2].trim();
        switch (command.toLowerCase()) {
            case "get":
                System.out.println("GET: key=" + key + " , value=" + jedisCluster.get(key));
                break;
            case "lpop":
                final String value = jedisCluster.lpop(key);
                System.out.println("LPOP: key=" + key + " , value=" + value);
                break;
            default:
                throw new IllegalArgumentException("Not Support yet");
        }
    }

    /**
     * 获取redis连接池的配置信息
     *
     * @return Jedis连接池对象
     */
    private static JedisPoolConfig getPoolConfig() {
        final JedisPoolConfig config = new JedisPoolConfig();
        int maxTotal = 8;
        int maxIdle = 1;
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        return config;
    }
}
