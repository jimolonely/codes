package com.jimo.cmd;

/**
 * @author jimo
 * @date 19-4-9 上午10:19
 */
public class Main {

    public static void main(String[] args) {
        assert args.length == 1;

        System.out.println(System.getProperty("name"));
    }
}
