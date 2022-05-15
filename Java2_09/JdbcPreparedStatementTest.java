package Java2_09;

import java.sql.*;

public class JdbcPreparedStatementTest {
    public static void main(String[] args) {
        try (

                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebookshop", "root", "");

                // Hai câu lệnh chuẩn bị, một cho INSERT và một cho SELECT
                PreparedStatement pstml = conn.prepareStatement(
                        "insert into books values (?,?,?,?,?) "); // Năm tham số từ 1 đến 5
                PreparedStatement pttmtSelect = conn.prepareStatement("select * from books ");
                ) {
//             pstml.setInt(1,7001); // Đặt giá trị cho các tham số từ 1 đến 5
//            pstml.setString(2,"Duy Linh");
//            pstml.setString(3, "QUÝ ĐƠ");
//            pstml.setDouble(4,88.88);
//            pstml.setInt(5,8);
//            int rowInserted = pstml.executeUpdate(); // thực thi câu lệnh
//            System.out.println(rowInserted + "rows affected");

            conn.setAutoCommit(false);
//            Tắt tính năng tự động cam kết

            pstml.setInt(1, 8003);  // Set values for parameters 1 to 5
            pstml.setString(2, "Java 123");
            pstml.setString(3, "Kevin Jones");
            pstml.setDouble(4, 12.34);
            pstml.setInt(5, 88);
            pstml.addBatch(); //thêm câu lệnh để xử lý hàng loạt

            pstml.setInt(1,8004);
            pstml.setString(2,"Java 456");
            // Không thay đổi giá trị cho các tham số 3 đến 5
            pstml.addBatch(); //thêm câu lệnh để xử lý hàng loạt

            int [] returnCodes = pstml.executeBatch();
            // executeBatch () trả về một mảng int, giữ các mã trả về của tất cả các câu lệnh
            System.out.println("Return codes are:");
            for (int code : returnCodes) System.out.println( code + ",");
            System.out.println();
            conn.commit(); // cam kết chọn bản ghi 

            // Step 3 & 4: Execute query and Process the query result
            conn.setAutoCommit(false);  // Tự động tắt tính cam kết


            pstml.addBatch("insert into books values (8001, 'Java ABC', 'Kevin Jones', 1.1, 99)");
            pstml.addBatch("insert into books values (8002, 'Java XYZ', 'Kevin Jones', 1.1, 99)");
            pstml.addBatch("update books set price = 11.11 where id=8001 or id=8002");


            System.out.print("Return codes are: ");
            for (int code : returnCodes) {
                System.out.printf(code + ", ");
            }
            System.out.println();

            conn.commit();  // Commit SQL statements


            // Đưa ra một SELECT để kiểm tra các thay đổi
            ResultSet rset = pttmtSelect.executeQuery();
            while (rset.next()){
                System.out.println(rset.getInt("id")+ ","
                + rset.getString("title") + ","
                + rset.getString("author") + ","
                + rset.getFloat("price") + ","
                        + rset.getInt("qty")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
