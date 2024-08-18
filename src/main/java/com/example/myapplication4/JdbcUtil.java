package com.example.myapplication4;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtil {
    private static JdbcUtil instance;

    public static JdbcUtil getInstance() {
        if (instance == null){
            instance = new JdbcUtil();
        }
        return instance;
    }

    public Connection getConnection(String dbName,String name,String password)  {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://10.69.163.28:3306/by_exp4";
            System.out.println("连接sql成功!");
            return DriverManager.getConnection(url, name, password);
        } catch(Exception e){
            e.printStackTrace();
            System.out.println("尝试连接sql失败！");
            return null;
        }
    }




}
