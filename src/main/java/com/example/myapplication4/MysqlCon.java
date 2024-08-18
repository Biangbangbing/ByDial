package com.example.myapplication4;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static android.content.ContentValues.TAG;

import android.util.Log;

public class MysqlCon {
    JdbcUtil jbdcUtil;
    Connection connection;
    public MysqlCon() {
        jbdcUtil = JdbcUtil.getInstance();
        connection = jbdcUtil.getConnection("by_exp4","root","biangbiang");

    }


    public boolean register(String name,String password) throws SQLException {
        if(connection == null){
            Log.i(TAG,"register:connect is null");
            return false;
        }else{
            System.out.println("n+p:"+name+password);
            Statement sm = connection.createStatement();
            String sql = "insert into users(username,password) values(?,?)";
            try{
                sm.executeQuery("use by_exp4;");
                System.out.println("执行命令之前，使用数据库之后。");
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1,name);
                preparedStatement.setString(2,password);
                System.out.println("login:"+sql);
                return preparedStatement.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                return false;
            }finally {
                try {
                    connection.close();
                    System.out.println("reg关con");
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean login(String name ,String password) throws SQLException {
        if (connection==null){
            Log.i(TAG,"login:connection is null");
            return false;
        }else{
            String sql = "select * from users where name=? and password=?";
            Statement sm = connection.createStatement();
            try{
                sm.executeQuery("use by_exp4;");
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1,name);
                preparedStatement.setString(2,password);
                ResultSet resultSet = preparedStatement.executeQuery();
                boolean t = resultSet.next();
                return t;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                return false;
            }finally {
                try {
                    connection.close();
                    System.out.println("login关con");
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }

    }

//    private Connection connection;
//    String url="jbdc:mysql:localhost:3306/by_exp4";
//
//    public Connection getConnection() {
//        return connection;
//    }
//
//    public void loadDriver(){
//        try{
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            System.out.println("加载JBDC驱动成功！");
//
//        } catch (ClassNotFoundException e) {
//            System.out.println("加载JBDC驱动失败！");
//            e.printStackTrace();
//            return;
//        }
//    }
//    public void connectSQL() throws SQLException {
//                try {
//                    this.connection =DriverManager.getConnection(url,"root","biangbiang");
//                } catch (SQLException throwables) {
//                    throwables.printStackTrace();
//                }
//                System.out.println("链接数据库成功！");
//    }
////    public static void main(String[] args) throws SQLException {
////        MysqlCon con = new MysqlCon();
////        con.loadDriver();
////        con.connectSQL();
////    }
}
