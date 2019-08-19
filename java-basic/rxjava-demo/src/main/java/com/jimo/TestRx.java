package com.jimo;

import io.reactivex.Flowable;

/**
 * @author jimo
 * @date 19-8-19 下午5:28
 */
public class TestRx {

    public static void main(String[] args) {
        Flowable.just("hello reactive").subscribe(System.out::println);
    }
}
