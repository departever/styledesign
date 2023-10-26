package org.example.CS;

import java.io.Serializable;

public class Contact implements Serializable {
    private String name;
    private String address;
    private String phone;

    //构造方法，根据参数初始化属性
    public Contact(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    //get和set方法，用于访问和修改属性
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    //重写toString方法，用于显示联系人信息
    public String toString() {
        return "姓名：" + name + "，住址：" + address + "，电话：" + phone;
    }
}