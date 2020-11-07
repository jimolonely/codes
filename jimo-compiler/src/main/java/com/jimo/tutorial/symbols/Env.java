package com.jimo.tutorial.symbols;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 把词法单元映射为类ID
 *
 * @author jimo
 * @date 2020/11/7 17:28
 * @since 1.0.0
 */
public class Env {

    private Map<String, String> table = new ConcurrentHashMap<>();

}
