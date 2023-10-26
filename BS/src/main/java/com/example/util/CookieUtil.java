package com.example.util;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.List;

public class CookieUtil {
    public static List<Cookie> loginUser(String number, String password) {
        List<Cookie> cookies = new ArrayList<>();
        Cookie cookie1 = new Cookie("number", number);
        Cookie cookie2 = new Cookie("password", password);
        cookies.add(cookie1);
        cookies.add(cookie2);
        return cookies;
    }
}
