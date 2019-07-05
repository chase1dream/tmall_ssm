package com.tmall.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author tanquan
 * @description <p>
 * 使用jdbc技术往数据库准备分类测试数据
 * </p>
 * @date 2019/7/1 15:02
 */
public class TmallTest {
    private static String url = "jdbc:mysql://localhost:3306/tmall_ssm?useUnicode=true&characterEncoding=utf8";
    private static String username = "root";
    private static String password = "1234";

    public static void main(String[] args) {
        // 1.加载驱动类
        try {
            Class.forName("com.mysql.jdbc.Driver");
            // 2.获取连接
            Connection conn = DriverManager.getConnection(url, username, password);
            // 3.准备sql语句
            for (int i = 1; i <= 10; i++) {
                String sqlFormat = "insert into category values (null,'测试分类%d')";
                String sql = String.format(sqlFormat, i);
                // 4.创建Statement对象
                Statement st = conn.createStatement();
                // 5.执行语句
                System.out.println(st.execute(sql));

            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
