package ext.modular.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * des:
 *  connectionde 的工具类，目前暂时不写单例了，因为最后是要获取windchill的connection的
 * @author fxiao
 * @date 2019/6/4 14:31
 */
public class ConnectionUtil {
    private final static Logger log= LoggerFactory.getLogger(ConnectionUtil.class);
    private static String driver="oracle.jdbc.driver.OracleDriver";
    private static String url="jdbc:oracle:thin:@192.168.199.204:1521:wind";
    private static String userName="yaao";
    private static String pass="yaao";


    public static Connection getJdbcConnection() throws ClassNotFoundException, SQLException {
//        InputStream inputStream = ConnectionUtil.class.getResourceAsStream("/jdbc.properties");
//        Properties p = new Properties();
//        try {
//            p.load(inputStream);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String driver=p.getProperty("db.driver");
//        String url=p.getProperty("db.url");
//        String userName=p.getProperty("db.userName");
//        String pass=p.getProperty("db.password");
        log.info("正在尝试连接数据库，driver={},url={}",driver,url);
        Class.forName(driver);
        Connection conn= DriverManager.getConnection(url,userName,pass);
        log.info("获取到的connection为：{}",conn);
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
    public static void close(Connection connection, PreparedStatement ps){
        if(connection!=null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(ps!=null){
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
