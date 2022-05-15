package Java2_09;

import java.sql.*;
import java.util.Scanner;

public class JdbcCommitCatchTest {
    public static void main(String[] args)  throws SQLException{
        Scanner in = new Scanner(System.in);
        int bookID;


        try(
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/ebookshop", "root", "");
                Statement stml = conn.createStatement();

                ) {
            try {
                // Tắt tính năng tự động cam kết
                conn.setAutoCommit(false);

                // Đưa ra hai câu lệnh INSERT
//                stml.executeUpdate("insert into books value (4001, 'Paul Chan', 'Mahjong 101', 4.4,4)");
//                // Khóa chính trùng lặp, kích hoạt SQLException
//                stml.executeUpdate("insert into books value (4002, 'Paul Chan', 'Mahjong 101', 4.4,4)");
                conn.commit();// Cam kết chỉ thay đổi nếu tất cả các câu lệnh thành công.


//                // Bước 3 & 4: Thực thi truy vấn và xử lý kết quả truy vấn
//                conn.setAutoCommit(false);// Tắt tính năng tự động cam kết cho kết nối, kết nối cam kết mọi câu lệnh SQL.
//
//                // Trước khi thay đổi
//                ResultSet rset = stml.executeQuery("select id , qty from books where id in (1001,1002)");
//                System.out.println("--Before UPDATE --");
//                //Before UPDATE : trước khi cập nhật
//                while (rset.next()) {
//                    System.out.println(rset.getInt("id") + "," + rset.getInt("qty"));
//                }
//                conn.commit();   // Cam kết CHỌN
//
//
//
//                // Đưa ra hai câu lệnh UPDATE thông qua executeUpdate ()
//                stml.executeUpdate("update books set qty = qty + 1 where id =1001");
//                stml.executeUpdate("update books set qty = qty + 1 where  id = 1002");
//                conn.commit(); // Cam kết CHỌN
//
//                rset = stml.executeQuery("select id , qty from books where id in (1001,1002)");
//                System.out.println("--After UPDATE and commit--"); //Sau khi cập nhật và cam kết
//                while (rset.next()){
//                    System.out.println(rset.getInt("id") + "," + rset.getInt("qty"));
//                }
//                conn.commit(); // Cam kết CHỌN
//
//
//
//
//                //Phát hành hai câu lệnh UPDATE thông qua executeUpdate ()
//                stml.executeUpdate("update books set qty = qty - 99 where id = 1001");
//                stml.executeUpdate("update books set qty = qty - 99 where id = 1002");
//
//                conn.rollback(); // trả về bản cập nhật ban đầu
//
//                rset = stml.executeQuery("select id , qty from books where id in (1001,1002)");
//                System.out.println("--After UPDATE and Rollback --"); // Sau khi CẬP NHẬT và khôi phục
//                while (rset.next()){
//                    System.out.println(rset.getInt("id") + "," + rset.getInt("qty"));
//                }
//                conn.commit(); //Conmit SELECT


                // Bước 3 & 4: Thực thi truy vấn và xử lý kết quả truy vấn
                ResultSet rset = stml.executeQuery("select * from books ");


                // Lấy siêu dữ liệu của ResultSet
                ResultSetMetaData rsetMD = rset.getMetaData();
                // Lấy số cột từ siêu dữ liệu
                int numColumns  = rsetMD.getColumnCount();

                // In tên cột - Chỉ mục cột bắt đầu bằng 1 (thay vì 0)
                for (int i = 1; i <= numColumns ; ++i ) {
                    System.out.printf("%-30s" ,  rsetMD.getColumnName(i));
                }
                System.out.println();


                // In tên lớp cột
                for (int i = 1; i <= numColumns ; ++i) {
                    System.out.printf("%-30s" ,"(" + rsetMD.getColumnClassName(i) + ")");
                }
                System.out.println();

                // In nội dung cột cho tất cả các hàng
                while (rset.next()){
                        for (int i = 1; i <= numColumns; i++) {
                            // getString () có thể được sử dụng cho tất cả các loại cột
                            System.out.printf("%-30s" , rset.getString(i));
                        }
                    System.out.println();
                }

            } catch (SQLException e) {
                System.out.println("--Rolling back changes--");
                conn.rollback();// Khôi phục về cam kết cuối cùng.
                e.printStackTrace();
            }
        }
    }
}
