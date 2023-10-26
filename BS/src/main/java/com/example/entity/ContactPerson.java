package com.example.entity;

import lombok.Data;

import java.io.Serializable;


@Data
public class ContactPerson implements Serializable {
    private static final long serialVersionUID = 235429554015588623L;
    /**
     * 主键
     */
    private Integer id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 电话号码
     */
    private String phone;
    /**
     * 住址
     */
    private String address;
}

