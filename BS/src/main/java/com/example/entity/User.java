package com.example.entity;

import lombok.Data;

import java.io.Serializable;


@Data
public class User implements Serializable {
    private static final long serialVersionUID = -94636525817631348L;
    /**
     * 用户id
     */
    private Integer id;
    /**
     * 用户昵称
     */
    private String name;
    /**
     * 账号
     */
    private String number;
    /**
     * 密码
     */
    private String password;
}

