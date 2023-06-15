package multiservers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Brando
 */
public class DB {

    public Connection conn = null;

    public DB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/peers?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false";
            conn = DriverManager.getConnection(url, "root", "mysql");
            System.out.println("conn built");
        } catch (SQLException | ClassNotFoundException e) {
        }
    }

    public ResultSet runSql(String sql) throws SQLException {
        Statement sta = conn.createStatement();
        System.out.println("BD: runsql");
        return sta.executeQuery(sql);
    }

    public void exe(String sql) throws SQLException {
        Statement sta = conn.createStatement();
        sta.executeUpdate(sql);
        System.out.println("Se agrego correctamente");
    }

    protected void finalize() throws Throwable {
        if (conn != null || !conn.isClosed()) {
            conn.close();
        }
        System.out.println("BD: Conexion cerrada");
    }

}
