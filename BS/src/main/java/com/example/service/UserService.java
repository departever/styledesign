package com.example.service;

import com.example.entity.User;

public interface UserService {
    Integer checkLogin(String number, String password);

    User getUserByNumber(String number);
}
