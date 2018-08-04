package jimo.mediator;

import java.util.Date;

public class ChatRoom {
    public static void showMessage(User user, String msg) {
        System.out.println(new Date() + "[" + user.getName() + "]: " + msg);
    }
}
