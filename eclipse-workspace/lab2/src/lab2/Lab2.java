package lab2;
import java.sql.*;
public class Lab2 {
 public static void main(String[] args){
  //����������
  String driver = "com.mysql.jdbc.Driver";
  // ���ݱ�url
  String url = "jdbc:mysql://localhost:3306/student";
  // MySQL�û���
  String user = "root";
  // MySQL����
  String password = "root";

  // ��ʼ�������ݿ�
  try {
  // ������������
  Class.forName(driver);
  // �������ݿ�
  Connection conn = DriverManager.getConnection(url, user, password);
  if(!conn.isClosed())
   System.out.println("connecting to the database successfully!");
  // statement����ִ��SQL���
  Statement statement = conn.createStatement();
  // select���
  String sql = "select id,name,address from info";
  ResultSet rs = statement.executeQuery(sql);  

  // ���student��������Ϣ
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