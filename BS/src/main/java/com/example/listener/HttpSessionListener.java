package com.example.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import java.util.concurrent.atomic.AtomicInteger;

@WebListener
public class HttpSessionListener implements javax.servlet.http.HttpSessionListener {
    private static AtomicInteger online = new AtomicInteger(0);

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        int increment = online.getAndIncrement();
        String id = se.getSession().getId();
        System.out.println("一位用户上线，SessionId为：【" + id + "】,当前在线人数：【" + increment + "】");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        int decrement = online.getAndDecrement();
        String id = se.getSession().getId();
        System.out.println("一位用户下线，SessionId为：【" + id + "】,当前在线人数：【" + decrement + "】");
    }

    public static Integer getOnline() {
        return online.get();
    }
}
