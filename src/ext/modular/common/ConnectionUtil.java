package ext.modular.common;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * des:
 *  connectionde 的工具类，目前暂时不写单例了，因为最后是要获取windchill的connection的
 * @author fxiao
 * @date 2019/6/4 14:31
 */
public class ConnectionUtil {
    /*private static String driver="oracle.jdbc.driver.OracleDriver";
    private static String url="jdbc:oracle:thin:@192.168.199.105:1521:wind";
    private static String userName="yaao";
    private static String pass="yaao";*/

    public static Connection getJdbcConnection() throws ClassNotFoundException, SQLException {
        InputStream inputStream = ConnectionUtil.class.getResourceAsStream("/jdbc.properties");
        Properties p = new Properties();
        try {
            p.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String driver=p.getProperty("db.driver");
        String url=p.getProperty("db.url");
        String userName=p.getProperty("db.userName");
        String pass=p.getProperty("db.password");
        Class.forName(driver);
        Connection conn= DriverManager.getConnection(url,userName,pass);
        return conn;
    }
    public static void close(Connection connection, Statement statement){
        if(connection!=null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(statement!=null){
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
