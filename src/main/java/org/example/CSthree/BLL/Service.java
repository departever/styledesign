package org.example.CSthree.BLL;

import org.example.CSthree.DAL.Contact;
import org.example.CSthree.DAL.DAO;

//定义一个业务逻辑类，实现对数据访问类的封装和调用，以及一些业务逻辑的处理
public class Service {
    //定义一个数据访问对象，用于调用数据访问类的方法
    private DAO dao;

    //构造方法，初始化数据访问对象
    public Service() {
        dao = new DAO();
    }

    //添加联系人方法，接收一个联系人对象作为参数，调用数据访问类的添加联系人方法，并进行一些业务逻辑的处理，如判断姓名是否重复等
    public void addContact(Contact contact) {
        if (contact == null) { //如果联系人对象为空，表示参数无效
            System.out.println("参数无效");
            return; //结束方法
        }
        if (dao.queryContact(contact.getName()) != null) { //如果根据姓名查询到了联系人信息，表示姓名已存在
            System.out.println("姓名已存在");
            return; //结束方法
        }
        dao.addContact(contact); //调用数据访问类的添加联系人方法，传入联系人对象作为参数
    }

    //删除联系人方法，接收一个姓名作为参数，调用数据访问类的删除联系人方法，并进行一些业务逻辑的处理，如判断姓名是否存在等
    public void deleteContact(String name) {
        if (name == null || name.isEmpty()) { //如果姓名为空或空字符串，表示参数无效
            System.out.println("参数无效");
            return; //结束方法
        }
        if (dao.queryContact(name) == null) { //如果根据姓名查询不到联系人信息，表示姓名不存在
            System.out.println("姓名不存在");
            return; //结束方法
        }
        dao.deleteContact(name); //调用数据访问类的删除联系人方法，传入姓名作为参数
    }

    //修改联系人方法，接收一个联系人对象作为参数，调用数据访问类的修改联系人方法，并进行一些业务逻辑的处理，如判断姓名是否存在等
    public void updateContact(Contact contact) {
        if (contact == null) { //如果联系人对象为空，表示参数无效
            System.out.println("参数无效");
            return; //结束方法
        }
        if (dao.queryContact(contact.getName()) == null) { //如果根据姓名查询不到联系人信息，表示姓名不存在
            System.out.println("姓名不存在");
            return; //结束方法
        }
        dao.updateContact(contact); //调用数据访问类的修改联系人方法，传入联系人对象作为参数
    }

    //查询联系人方法，接收一个姓名作为参数，调用数据访问类的查询联系人方法，并进行一些业务逻辑的处理，如判断姓名是否有效等，并返回一个联系人对象或null
    public Contact queryContact(String name) {
        if (name == null || name.isEmpty()) { //如果姓名为空或空字符串，表示参数无效
            System.out.println("参数无效");
            return null; //返回null
        }
        return dao.queryContact(name); //调用数据访问类的查询联系人方法，传入姓名作为参数，并返回一个联系人对象或null
    }

    //显示所有联系人方法，调用数据访问类的显示所有联系人方法，并进行一些业务逻辑的处理，如判断是否有联系人信息等，并返回一个包含所有联系人信息的字符串或null
    public String showAllContacts() {
        String result = dao.showAllContacts(); //调用数据访问类的显示所有联系人方法，并返回一个包含所有联系人信息的字符串或null
        if (result == null || result.isEmpty()) { //如果结果为空或空字符串，表示没有联系人信息可供显示
            System.out.println("没有联系人信息");
            return null; //返回null
        }
        return result; //返回结果
    }
}