package lab2;
import java.sql.*;
public class Lab2 {
 public static void main(String[] args){
  //驱动程序名
  String driver = "com.mysql.jdbc.Driver";
  // 数据表url
  String url = "jdbc:mysql://localhost:3306/student";
  // MySQL用户名
  String user = "root";
  // MySQL密码
  String password = "root";

  // 开始连接数据库
  try {
  // 加载驱动程序
  Class.forName(driver);
  // 连续数据库
  Connection conn = DriverManager.getConnection(url, user, password);
  if(!conn.isClosed())
   System.out.println("connecting to the database successfully!");
  // statement用来执行SQL语句
  Statement statement = conn.createStatement();
  // select语句
  String sql = "select id,name,address from info";
  ResultSet rs = statement.executeQuery(sql);  

  // 输出student的所有信息
  while(rs.next()) {
   System.out.println(rs.getString("id") + "\t" + rs.getString("name") + "\t" + rs.getString("address")); 
   } 

  rs.close(); 
  conn.close();  
  } catch(ClassNotFoundException e) {  
   System.out.println("sorry, can't find the driver!");  
   e.printStackTrace();  
  } catch(SQLException e) {
   e.printStackTrace();
  } catch(Exception e){
   e.printStackTrace();
  }
 }  
}