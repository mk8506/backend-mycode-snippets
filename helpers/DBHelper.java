package kr.minj.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHelper {
  private static final String db_hostname = "127.0.0.1";
  private static final int db_portnumber = 3306;
  private static final String db_database = "myschool";
  private static final String db_charset = "utf8";
  private static final String db_username = "root";
  private static final String db_password = "Ydkyaahg2516!#%";
  
  private Connection connection = null;

  //singleton
  private static DBHelper current;
  
  public static DBHelper getInstance() {
    if (current == null) {
      current = new DBHelper();
    }
    return current;
  }
  public static void freeInstance() {
    current = null;
  }
  private DBHelper() {
  }
  
  //methods

  //connect, return
  public Connection open() {
    String urlFormat = "";
    String url = String.format(urlFormat, db_hostname, db_portnumber, db_database, db_charset);

    try {
      Class.forName("com.mysql.cj.jdbc.Driver"); //ClassNotFoundException
      connection = DriverManager.getConnection(url, db_username, db_password); //SQLException
    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return connection;
  }

  //close
  public void close() {
    if (connection != null) {
      try {
        connection.close(); //SQLException
      } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }
}
