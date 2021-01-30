package com.jimo;

import java.security.AccessControlContext;
import java.security.AccessController;

public class Main {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            final AccessControlContext context = AccessController.getContext();
            System.out.println("Main:" + context);

            new Thread(() -> {
                final AccessControlContext context1 = AccessController.getContext();
                System.out.println(Thread.currentThread().getName() + ":" + context1);
            }).start();
        }
    }
}
