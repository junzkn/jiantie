import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.* ;
import com.sun.istack.internal.NotNull;

public class Dao {
    // JDBC 驱动名及数据库 URL
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/test?useSSL=false";
    // 数据库的用户名与密码，需要根据自己的设置
    private static final String USER = "root";
    private static final String PASS = "zkn8023apple";


    public static String checkUser(@NotNull String name, @NotNull String password) {
        UserBean bean = new UserBean();
        Gson gson = new Gson();
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            // 打开链接
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            // 执行查询
            stmt = conn.createStatement();
            String sql = "SELECT * FROM user Where name=\'" + name + "\' AND password=\'" + password + "\'";
            ResultSet rs = stmt.executeQuery(sql);

            // 展开结果集数据库
            if (rs.next()) {
                bean.setStatus("success");
                String _name = rs.getString("name");
                String _password = rs.getString("password");
                String _sex = rs.getString("sex");
                bean.setName(_name);
                bean.setPassword(_password);
                bean.setSex(_sex);
            } else {
                bean.setStatus("error");
            }
            // 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            //JDBC error
            e.printStackTrace();
        } catch (Exception e) {
            //Class.forName error
        } finally {
            // 关闭资源
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
            }// 什么都不做
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return gson.toJson(bean);
    }


    public static String addUser(@NotNull String name, @NotNull String password, @NotNull String sex) {
        String result ="";
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            // 打开链接
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            // 执行查询
            stmt = conn.createStatement();
            String sql = "INSERT INTO USER (NAME,PASSWORD,SEX) VALUES (\""+name+"\", \""+password+"\", \""+sex+"\")" ;
            boolean b = stmt.execute(sql);
            if(b){
                result = "{\"status\":\"error\"}" ;
            }
            else {
                result = "{\"status\":\"success\"}" ;
            }
            // 完成后关闭
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            //JDBC error
            e.printStackTrace();
            result = "{\"status\":\"error\"}" ;
        } catch (Exception e) {
            //Class.forName error
        } finally {
            // 关闭资源
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
            }// 什么都不做
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return result;
    }


    public static String getMusic() {
        List<MusicBean> musicList  = new ArrayList<>() ;

        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            // 打开链接
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            // 执行查询
            stmt = conn.createStatement();
            String sql = "SELECT * FROM music";
            ResultSet rs = stmt.executeQuery(sql);

            // 展开结果集数据库
            while (rs.next()) {
                String _name = rs.getString("name");
                String _url = rs.getString("url");
                MusicBean bean = new MusicBean();
                bean.setName(_name);
                bean.setUrl(_url);
                musicList.add(bean);
            }
            // 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            //JDBC error
            e.printStackTrace();
        } catch (Exception e) {
            //Class.forName error
        } finally {
            // 关闭资源
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
            }// 什么都不做
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        String string = new Gson().toJson(musicList);
        return string;
    }


    public static String saveMusic(String name , String url){
        String result ="";
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            // 打开链接
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            // 执行查询
            stmt = conn.createStatement();
            String sql = "INSERT INTO music (name,url) VALUES (\""+name+"\", \""+url+"\")" ;
            System.out.println(sql);

            boolean b = stmt.execute(sql);
            if(b){
                result = "{\"status\":\"error\"}" ;
            }
            else {
                result = "{\"status\":\"success\"}" ;
            }
            // 完成后关闭
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            //JDBC error
            e.printStackTrace();
            result = "{\"status\":\"error\"}" ;
        } catch (Exception e) {
            //Class.forName error
        } finally {
            // 关闭资源
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
            }// 什么都不做
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return result;
    }













}
