package org.example.CSthree.BLL;

import org.example.CSthree.DAL.Contact;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

//定义一个服务端类，用于接收客户端的请求，并调用业务逻辑类的方法进行处理，然后将结果返回给客户端
class Server {
    //定义一个业务逻辑对象，用于调用业务逻辑类的方法
    private Service service;

    //构造方法，初始化业务逻辑对象
    public Server() {
        service = new Service();
    }

    //启动服务端的方法，创建一个服务器套接字，等待客户端的连接，并创建一个线程来处理每个客户端的请求
    public void start() {
        try {
            //创建一个服务器套接字，绑定到指定的端口号
            ServerSocket serverSocket = new ServerSocket(8888);
            System.out.println("服务端已启动，等待客户端连接...");
            //循环监听客户端的连接请求
            while (true) {
                //接受客户端的连接请求，返回一个套接字对象
                Socket socket = serverSocket.accept();
                System.out.println("客户端" + socket.getInetAddress() + "已连接");
                //创建一个线程对象，传入套接字对象作为参数
                Thread thread = new Thread(new Handler(socket));
                //启动线程
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //定义一个内部类，实现Runnable接口，用于处理每个客户端的请求
    class Handler implements Runnable {
        //定义一个套接字对象，用于和客户端进行通信
        private Socket socket;

        //构造方法，根据参数初始化套接字对象
        public Handler(Socket socket) {
            this.socket = socket;
        }

        //重写run方法，实现业务逻辑
        public void run() {
            try {
                //创建一个对象输入流，从套接字中获取输入流
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                //创建一个对象输出流，从套接字中获取输出流
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                //循环读取客户端发送的请求
                while (true) {
                    //读取一个字符串对象，表示请求的类型
                    String type = (String) ois.readObject();
                    //根据请求的类型进行不同的处理
                    switch (type) {
                        case "add": //添加联系人请求
                            //读取一个联系人对象，表示要添加的联系人信息
                            Contact contact = (Contact) ois.readObject();

                            //调用业务逻辑类的添加联系人方法，传入联系人对象作为参数
                            service.addContact(contact);
                            //向客户端发送一个字符串对象，表示操作成功
                            oos.writeObject("添加成功");
                            break;
                        case "delete": //删除联系人请求
                            //读取一个字符串对象，表示要删除的联系人姓名
                            String name = (String) ois.readObject();
                            //调用业务逻辑类的删除联系人方法，传入姓名作为参数
                            service.deleteContact(name);
                            //向客户端发送一个字符串对象，表示操作成功
                            oos.writeObject("删除成功");
                            break;
                        case "update": //修改联系人请求
                            //读取一个联系人对象，表示要修改的联系人信息
                            contact = (Contact) ois.readObject();
                            //调用业务逻辑类的修改联系人方法，传入联系人对象作为参数
                            service.updateContact(contact);
                            //向客户端发送一个字符串对象，表示操作成功
                            oos.writeObject("修改成功");
                            break;
                        case "query": //查询联系人请求
                            //读取一个字符串对象，表示要查询的联系人姓名
                            name = (String) ois.readObject();
                            //调用业务逻辑类的查询联系人方法，传入姓名作为参数，并返回一个联系人对象或null
                            contact = service.queryContact(name);
                            if (contact == null) { //如果返回null，表示没有找到匹配的联系人信息
                                //向客户端发送一个字符串对象，表示查询失败
                                oos.writeObject("查询失败");
                            } else { //如果返回一个联系人对象，表示找到了匹配的联系人
                                //向客户端发送一个字符串对象，表示查询成功
                                oos.writeObject("查询成功");
                                //向客户端发送一个联系人对象，表示查询到的联系人信息
                                oos.writeObject(contact);
                            }
                            break;
                        case "show": //显示所有联系人请求
                            //调用业务逻辑类的显示所有联系人方法，并返回一个包含所有联系人信息的字符串或null
                            String result = service.showAllContacts();
                            if (result == null) { //如果返回null，表示没有联系人信息可供显示
                                //向客户端发送一个字符串对象，表示显示失败
                                oos.writeObject("显示失败");
                            } else { //如果返回一个包含所有联系人信息的字符串，表示有联系人信息可供显示
                                //向客户端发送一个字符串对象，表示显示成功
                                oos.writeObject("显示成功");
                                //向客户端发送一个字符串对象，表示所有联系人信息
                                oos.writeObject(result);
                            }
                            break;
                        case "exit": //退出请求
                            //向客户端发送一个字符串对象，表示退出成功
                            oos.writeObject("退出成功");
                            //关闭套接字和流
                            socket.close();
                            ois.close();
                            oos.close();
                            System.out.println("客户端" + socket.getInetAddress() + "已断开");
                            return; //结束线程
                        default: //无效请求
                            //向客户端发送一个字符串对象，表示无效请求
                            oos.writeObject("无效请求");
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    //定义一个主方法，用于创建并启动服务端对象
    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}