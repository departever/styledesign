package org.example.CSthree.DAL;

import java.sql.*;

//定义一个数据访问类，实现对数据库的增删改查等操作
public class DAO {
    //定义一个数据库连接对象，用于和数据库进行通信
    private Connection conn;

    //构造方法，初始化数据库连接对象
    public DAO() {
        try {
            //加载数据库驱动类
            Class.forName("com.mysql.cj.jdbc.Driver");
            //获取数据库连接对象，传入数据库的URL、用户名和密码，请根据实际情况修改这里的参数值
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/address", "root", "123456");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    //添加联系人方法，接收一个联系人对象作为参数，将其插入到数据库中
    public void addContact(Contact contact) {
        try {
            //创建一个预编译语句对象，传入SQL语句
            PreparedStatement ps = conn.prepareStatement("insert into contacts values(?,?,?)");
            //设置SQL语句中的参数值，根据联系人对象的属性
            ps.setString(1, contact.getName());
            ps.setString(2, contact.getAddress());
            ps.setString(3, contact.getPhone());
            //执行SQL语句，并返回影响的行数
            int rows = ps.executeUpdate();
            if (rows > 0) { //如果影响的行数大于0，表示插入成功
                System.out.println("添加成功");
            } else { //如果影响的行数小于等于0，表示插入失败
                System.out.println("添加失败");
            }
            //关闭预编译语句对象
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //删除联系人方法，接收一个姓名作为参数，根据姓名删除对应的联系人记录
    public void deleteContact(String name) {
        try {
            //创建一个预编译语句对象，传入SQL语句
            PreparedStatement ps = conn.prepareStatement("delete from contacts where name=?");
            //设置SQL语句中的参数值，根据姓名
            ps.setString(1, name);
            //执行SQL语句，并返回影响的行数
            int rows = ps.executeUpdate();
            if (rows > 0) { //如果影响的行数大于0，表示删除成功
                System.out.println("删除成功");
            } else { //如果影响的行数小于等于0，表示删除失败
                System.out.println("删除失败");
            }
            //关闭预编译语句对象
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //修改联系人方法，接收一个联系人对象作为参数，根据姓名修改对应的联系人记录
    public void updateContact(Contact contact) {
        try {
            //创建一个预编译语句对象，传入SQL语句
            PreparedStatement ps = conn.prepareStatement("update contacts set address=?, phone=? where name=?");
            //设置SQL语句中的参数值，根据联系人对象的属性
            ps.setString(1, contact.getAddress());
            ps.setString(2, contact.getPhone());
            ps.setString(3, contact.getName());
            //执行SQL语句，并返回影响的行数
            int rows = ps.executeUpdate();
            if (rows > 0) { //如果影响的行数大于0，表示修改成功
                System.out.println("修改成功");
            } else { //如果影响的行数小于等于0，表示修改失败
                System.out.println("修改失败");
            }
            //关闭预编译语句对象
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //查询联系人方法，接收一个姓名作为参数，根据姓名查询对应的联系人记录，并返回一个联系人对象或null
    public Contact queryContact(String name) {
        try {
            //创建一个预编译语句对象，

            //传入SQL语句
            PreparedStatement ps = conn.prepareStatement("select * from contacts where name=?");
            //设置SQL语句中的参数值，根据姓名
            ps.setString(1, name);
            //执行SQL语句，并返回一个结果集对象
            ResultSet rs = ps.executeQuery();
            if (rs.next()) { //如果结果集中有数据，表示查询成功
                //从结果集中获取联系人信息，并创建一个联系人对象
                Contact contact = new Contact(rs.getString("name"), rs.getString("address"), rs.getString("phone"));
                //关闭预编译语句对象和结果集对象
                ps.close();
                rs.close();
                //返回联系人对象
                return contact;
            } else { //如果结果集中没有数据，表示查询失败
                //关闭预编译语句对象和结果集对象
                ps.close();
                rs.close();
                //返回null
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //显示所有联系人方法，从数据库中查询所有的联系人记录，并返回一个包含所有联系人信息的字符串
    public String showAllContacts() {
        try {
            //创建一个字符串缓冲区，用于拼接字符串
            StringBuffer sb = new StringBuffer();
            //创建一个预编译语句对象，传入SQL语句
            PreparedStatement ps = conn.prepareStatement("select * from contacts");
            //执行SQL语句，并返回一个结果集对象
            ResultSet rs = ps.executeQuery();
            while (rs.next()) { //循环遍历结果集中的数据
                //从结果集中获取联系人信息，并创建一个联系人对象
                Contact contact = new Contact(rs.getString("name"), rs.getString("address"), rs.getString("phone"));
                //将联系人对象的信息添加到字符串缓冲区中
                sb.append(contact.toString() + "\n");
            }
            //关闭预编译语句对象和结果集对象
            ps.close();
            rs.close();
            //返回字符串缓冲区的内容
            return sb.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}