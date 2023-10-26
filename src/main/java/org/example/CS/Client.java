package org.example.CS;

import java.io.*;
import java.net.*;
import java.util.*;

//定义一个客户端类，用于连接服务端，并发送请求和接收结果
public class Client {
    //定义一个套接字对象，用于和服务端进行通信
    private Socket socket;

    //定义一个对象输入流，用于从服务端获取输入流
    private ObjectInputStream ois;

    //定义一个对象输出流，用于向服务端发送输出流
    private ObjectOutputStream oos;

    //定义一个扫描器对象，用于从控制台获取用户输入
    private Scanner scanner;

    //构造方法，根据参数初始化套接字对象，并创建输入输出流和扫描器对象
    public Client(String host, int port) {
        try {
            //创建一个套接字对象，连接到指定的主机和端口号
            socket = new Socket(host, port);
            System.out.println("已连接到服务端");
            //创建一个对象输出流，从套接字中获取输出流
            oos = new ObjectOutputStream(socket.getOutputStream());
            //创建一个对象输入流，从套接字中获取输入流
            ois = new ObjectInputStream(socket.getInputStream());
            //创建一个扫描器对象，从系统标准输入获取输入流
            scanner = new Scanner(System.in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //启动客户端的方法，向服务端发送请求，并接收结果
    public void start() {
        try {
            //循环向服务端发送请求
            while (true) {
                //显示菜单选项，并提示用户选择
                System.out.println("请选择要执行的操作：");
                System.out.println("1. 添加联系人");
                System.out.println("2. 删除联系人");
                System.out.println("3. 修改联系人");
                System.out.println("4. 查询联系人");
                System.out.println("5. 显示所有联系人");
                System.out.println("6. 退出");
                System.out.print("请输入你的选择：");
                //获取用户输入的数字，并转换为整数类型
                int choice = Integer.parseInt(scanner.nextLine());
                //根据用户选择的数字进行不同的处理
                switch (choice) {
                    case 1: //添加联系人请求
                        //调用添加联系人方法，向服务端发送添加联系人请求，并接收结果
                        addContact();
                        break;
                    case 2: //删除联系人请求
                        //调用删除联系人方法，向服务端发送删除联系人请求，并接收结果
                        deleteContact();
                        break;
                    case 3: //修改联系人请求
                        //调用修改联系人方法，向服务端发送修改联系人请求，并接收结果
                        updateContact();
                        break;
                    case 4: //查询联系人请求
                        //调用查询联系人方法，向服务端发送查询联系人请求，并接收结果
                        queryContact();
                        break;
                    case 5: //显示所有联系人请求
                        //调用显示所有联系人方法，向服务端发送显示所有联系人请求，并接收结果
                        showAllContacts();
                        break;
                    case 6: //退出请求
                        //向服务端发送一个字符串对象，表示退出请求
                        oos.writeObject("exit");
                        //从服务端读取一个字符串对象，表示退出结果
                        String result = (String) ois.readObject();
                        System.out.println(result);
                        //关闭套接字和流
                        socket.close();
                        ois.close();
                        oos.close();
                        scanner.close();
                        return; //结束方法
                    default: //无效请求
                        System.out.println("无效选择，请重新输入");
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //定义一个添加联系人方法，向服务端发送添加联系人请求，并接收结果
    public void addContact() throws IOException, ClassNotFoundException {
        //提示用户输入要添加的联系人信息，并获取用户输入的字符串
        System.out.print("请输入要添加的姓名：");
        String name = scanner.nextLine();
        System.out.print("请输入要添加的住址：");
        String address = scanner.nextLine();
        System.out.print("请输入要添加的电话：");
        String phone = scanner.nextLine();
        //创建一个联系人对象，根据用户输入的字符串初始化属性
        Contact contact = new Contact(name, address, phone);
        //向服务端发送一个字符串对象，表示添加联系人请求
        oos.writeObject("add");
        //向服务端发送一个联系人对象，表示要添加的联系人信息
        oos.writeObject(contact);
        //从服务端读取一个字符串对象，表示添加结果
        String result = (String) ois.readObject();
        System.out.println(result);
    }

    //定义一个删除联系人方法，向服务端发送删除联系人请求，并接收结果
    public void deleteContact() throws IOException, ClassNotFoundException {
        //提示用户输入要删除的联系人姓名，并获取用户输入的字符串
        System.out.print("请输入要删除的姓名：");
        String name = scanner.nextLine();
        //向服务端发送一个字符串对象，表示删除联系人请求
        oos.writeObject("delete");
        //向服务端发送一个字符串对象，表示要删除的联系人姓名
        oos.writeObject(name);
        //从服务端读取一个字符串对象，表示删除结果
        String result = (String) ois.readObject();
        System.out.println(result);
    }

    //定义一个修改联系人方法，向服务端发送修改联系人请求，并接收结果
    public void updateContact() throws IOException, ClassNotFoundException {
        //提示用户输入要修改的联系人姓名，并获取用户输入的字符串
        System.out.print("请输入要修改的姓名：");
        String name = scanner.nextLine();
        //向服务端发送一个字符串对象，表示查询联系人请求
        oos.writeObject("query");
        //向服务端发送一个字符串对象，表示要查询的联系人姓名
        oos.writeObject(name);
        //从服务端读取一个字符串对象，表示查询结果
        String result = (String) ois.readObject();
        if (result.equals("查询失败")) { //如果查询失败，表示没有找到匹配的联系人信息
            //向用户显示查询失败的信息
            System.out.println(result);
            return; //结束方法
        } else { //如果查询成功，表示找到了匹配的联系人
            //从服务端读取一个联系人对象，表示查询到的联系人信息
            Contact contact = (Contact) ois.readObject();
            //向用户显示查询成功的信息和联系人信息
            System.out.println(result);
            System.out.println(contact);
            //提示用户输入要修改的联系人信息，并获取用户输入的字符串
            System.out.print("请输入要修改的住址：");
            String address = scanner.nextLine();
            System.out.print("请输入要修改的电话：");
            String phone = scanner.nextLine();
            //更新联系人对象的属性
            contact.setAddress(address);
            contact.setPhone(phone);
            //向服务端发送一个字符串对象，表示修改联系人请求
            oos.writeObject("update");
            //向服务端发送一个联系人对象，表示要修改的联系人信息
            oos.writeObject(contact);
            //从服务端读取一个字符串对象，表示修改结果
            result = (String) ois.readObject();
            System.out.println(result);
        }
    }

    //定义一个查询联系人方法，向服务端发送查询联系人请求，并接收结果
    public void queryContact() throws IOException, ClassNotFoundException {
        //提示用户输入要查询的联系人姓名，并获取用户输入的字符串
        System.out.print("请输入要查询的姓名：");
        String name = scanner.nextLine();
        //向服务端发送一个字符串对象，表示查询联系人请求
        oos.writeObject("query");
        //向服务端发送一个字符串对象，表示要查询的联系人姓名
        oos.writeObject(name);
        //从服务端读取一个字符串对象，表示查询结果
        String result = (String) ois.readObject();
        if (result.equals("查询失败")) { //如果查询失败，表示没有找到匹配的联系人信息
            //向用户显示查询失败的信息
            System.out.println(result);
        } else { //如果查询成功，表示找到了匹配的联系人
            //从服务端读取一个联系人对象，表示查询到的联系人信息
            Contact contact = (Contact) ois.readObject();
            //向用户显示查询成功的信息和联系人信息
            System.out.println(result);
            System.out.println(contact);
        }
    }

    //定义一个显示所有联系人方法，向服务端发送显示所有联系人请求，并接收结果
    public void showAllContacts() throws IOException, ClassNotFoundException {
        //向服务端发送一个字符串对象，表示显示所有联系人请求
        oos.writeObject("show");
        //从服务端读取一个字符串对象，表示显示结果
        String result = (String) ois.readObject();
        if (result.equals("显示成功")) { //如果显示成功，表示有联系人信息可供显示
            //从服务端读取一个字符串对象，表示所有联系人信息
            String data = (String) ois.readObject();
            //向用户显示显示成功的信息和所有联系人信息
            System.out.println(result);
            System.out.println(data);
        } else { //如果显示失败，表示没有联系人信息可供显示
            //向用户显示显示失败的信息
            System.out.println(result);
        }
    }

    //定义一个主方法，用于创建并启动客户端对象
    //定义一个主方法，用于创建并启动客户端对象
    public static void main(String[] args) {
        Client client = new Client("127.0.0.1", 8888); //创建一个客户端对象，传入服务器的IP地址和端口号作为参数，请根据实际情况修改这里的参数值
        client.start(); //启动客户端对象
    }


}