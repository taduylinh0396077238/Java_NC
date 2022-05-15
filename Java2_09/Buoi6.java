package Java2_09;

import java.sql.*;
import java.util.Scanner;

public class Buoi6 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebookshop",
                        "root", "");
                Statement stmt = conn.createStatement();

                // khi các câu lệnh SQL có chuyền tham số vào thì nên sử dung PreparedStatement
                PreparedStatement pstmt = conn.prepareStatement("insert  into books values (?, ?, ?, ?, ?)");
                PreparedStatement pstmt2 = conn.prepareStatement("insert  into books (id, title, qty) values (?, ?, ?)");
                PreparedStatement pstmtSelect = conn.prepareStatement("select * from books");

        ) {
            pstmt.setInt(1, 4001);// gán giá trị sẽ tùy thuộc vào kiểu dữ liệu của cột
            pstmt.setString(2, "King 11");// số thứ tự (1,2,3,...) sẽ tùy thuộc vào số cột mà ta muốn Insert
            pstmt.setString(3, "Kai");
            pstmt.setDouble(4, 55.55);
            pstmt.setInt(5, 55);
            int rowsInserted = pstmt.executeUpdate();
            System.out.println(rowsInserted + "rows affected.\n");

            pstmt.setInt(1, 4002); //thay đổi giá trị tham số 1 và 2
            pstmt.setString(2, "King 22");
            // không thay đổi giá trị cho các tham số 3 đến 5
            rowsInserted = pstmt.executeUpdate();
            System.out.println(rowsInserted + "rows affected.\n");

            // gán giá trị vào 3 cột
            pstmt2.setInt(1, 4003);
            pstmt2.setString(2, "King 33");
            pstmt2.setInt(3, 22);
            rowsInserted = pstmt2.executeUpdate();
            System.out.println(rowsInserted + "rows affected.\n");

            ResultSet rset = pstmtSelect.executeQuery();
            while (rset.next()) {
                System.out.println(rset.getInt("id") + ", "
                        + rset.getString("title") + ", "
                        + rset.getString("author") + ", "
                        + rset.getDouble("price") + ", "
                        + rset.getInt("qty")
                );
            }



            // Batch - nhóm các lệnh thành 1 lô rồi gửi đi
            conn.setAutoCommit(false);

            stmt.addBatch("insert  into books values (4004, 'Java ABC', 'Kevin ABC', 22.2, 22)");
            stmt.addBatch("insert  into books values (4005, 'Java XYZ', 'Kevin XYZ', 22.2, 22)");
            stmt.addBatch("update books set price = 22.22 where id = 4004 or id = 4005");
            int[] returnCodes = stmt.executeBatch();

            System.out.println("\nReturn codes are: ");
            for (int code : returnCodes) {
                System.out.printf(code + ", ");
            }
            System.out.println();

            conn.commit();


            //
            rset = pstmtSelect.executeQuery();
            while (rset.next()) {
                System.out.println(rset.getInt("id") + ", "
                        + rset.getString("title") + ", "
                        + rset.getString("author") + ", "
                        + rset.getDouble("price") + ", "
                        + rset.getInt("qty")
                );
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
